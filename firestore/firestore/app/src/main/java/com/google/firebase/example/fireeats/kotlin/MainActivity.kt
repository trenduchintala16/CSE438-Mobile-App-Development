package com.google.firebase.example.fireeats.kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.example.fireeats.R
import com.google.firebase.example.fireeats.kotlin.adapter.RestaurantAdapter
import com.google.firebase.example.fireeats.kotlin.model.Restaurant
import com.google.firebase.example.fireeats.kotlin.util.RatingUtil
import com.google.firebase.example.fireeats.kotlin.util.RestaurantUtil
import com.google.firebase.example.fireeats.kotlin.viewmodel.MainActivityViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.buttonClearFilter
import kotlinx.android.synthetic.main.activity_main.filterBar
import kotlinx.android.synthetic.main.activity_main.recyclerRestaurants
import kotlinx.android.synthetic.main.activity_main.textCurrentSearch
import kotlinx.android.synthetic.main.activity_main.textCurrentSortBy
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.viewEmpty

class MainActivity : AppCompatActivity(),
        FilterDialogFragment.FilterListener,
        RestaurantAdapter.OnRestaurantSelectedListener {

    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query

    private lateinit var filterDialog: FilterDialogFragment
    lateinit var adapter: RestaurantAdapter

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // View model
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        firestore = FirebaseFirestore.getInstance()

        query = firestore.collection("restaurants")
                .orderBy("avgRating", Query.Direction.DESCENDING)
                .limit(50)

        // RecyclerView
        adapter = object : RestaurantAdapter(query, this@MainActivity) {
            override fun onDataChanged() {
                // Show/hide content if the query returns empty.
                if (itemCount == 0) {
                    recyclerRestaurants.visibility = View.GONE
                    viewEmpty.visibility = View.VISIBLE
                } else {
                    recyclerRestaurants.visibility = View.VISIBLE
                    viewEmpty.visibility = View.GONE
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error: check logs for info.", Snackbar.LENGTH_LONG).show()
            }
        }

        recyclerRestaurants.layoutManager = LinearLayoutManager(this)
        recyclerRestaurants.adapter = adapter

        // Filter Dialog
        filterDialog = FilterDialogFragment()

        filterBar.setOnClickListener { onFilterClicked() }
        buttonClearFilter.setOnClickListener { onClearFilterClicked() }
    }

    public override fun onStart() {
        super.onStart()

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn()
            return
        }

        // Apply filters
        onFilter(viewModel.filters)

        // Start listening for Firestore updates
        adapter.startListening()
    }

    public override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_items -> onAddItemsClicked()
            R.id.menu_sign_out -> {
                AuthUI.getInstance().signOut(this)
                startSignIn()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            viewModel.isSigningIn = false

            if (resultCode != Activity.RESULT_OK) {
                if (response == null) {
                    // User pressed the back button.
                    finish()
                } else if (response.error != null && response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    showSignInErrorDialog(R.string.message_no_network)
                } else {
                    showSignInErrorDialog(R.string.message_unknown)
                }
            }
        }
    }

    private fun onFilterClicked() {
        // Show the dialog containing filter options
        filterDialog.show(supportFragmentManager, FilterDialogFragment.TAG)
    }

    private fun onClearFilterClicked() {
        filterDialog.resetFilters()

        onFilter(Filters.default)
    }

    override fun onRestaurantSelected(restaurant: DocumentSnapshot) {
        // Go to the details page for the selected restaurant
        val intent = Intent(this, RestaurantDetailActivity::class.java)
        intent.putExtra(RestaurantDetailActivity.KEY_RESTAURANT_ID, restaurant.id)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
    }

    override fun onFilter(filters: Filters) {

    }

    private fun shouldStartSignIn(): Boolean {
        return !viewModel.isSigningIn && FirebaseAuth.getInstance().currentUser == null
    }

    private fun startSignIn() {
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders((listOf(AuthUI.IdpConfig.EmailBuilder().build())))
                .setIsSmartLockEnabled(false)
                .build()

        startActivityForResult(intent, RC_SIGN_IN)
        viewModel.isSigningIn = true
    }

    private fun onAddItemsClicked() {
        val batch = firestore.batch()
        for(i in 0..9) {
            val restRef = firestore.collection("restaurants").document()

            val randomRestaurant = RestaurantUtil.getRandom(this)
            val randomRatings = RatingUtil.getRandomList((randomRestaurant.numRatings))
            randomRestaurant.avgRating = RatingUtil.getAverageRating(randomRatings)

            batch.set(restRef, randomRestaurant)

            for(rating in randomRatings) {
                batch.set(restRef.collection("ratings").document(), rating)
            }
        }

        batch.commit().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Log.d(TAG,"Write succeeded")
            } else {
                Log.d(TAG,"Write failed")
            }
        }
    }

    private fun showSignInErrorDialog(@StringRes message: Int) {
        val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.title_sign_in_error)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.option_retry) { _, _ -> startSignIn() }
                .setNegativeButton(R.string.option_exit) { _, _ -> finish() }.create()

        dialog.show()
    }

    companion object {

        private const val TAG = "MainActivity"

        private const val RC_SIGN_IN = 9001

        private const val LIMIT = 50
    }
}
