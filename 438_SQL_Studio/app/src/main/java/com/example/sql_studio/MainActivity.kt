package com.example.sql_studio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.sql_studio.Adapter.EventListAdapter
import com.example.sql_studio.Database.Event
import com.example.sql_studio.Fragments.AddFragment
import com.example.sql_studio.Fragments.ViewFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var  eventList: ArrayList<Event> = ArrayList<Event>()
    private var eventViewModel : EventViewModel? = null
    public lateinit  var clearButton : Button
    public lateinit var addButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ViewFragment(), "View")
        adapter.addFragment(AddFragment(), "Add")
        viewPager?.adapter = adapter
        tabs.setupWithViewPager(viewPager)


    }
}
