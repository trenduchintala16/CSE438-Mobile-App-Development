package com.example.gesturesandanimationsstudio

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ClipData
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var gestureDetector: GestureDetectorCompat
    private var rocketX : ArrayList<Float> = ArrayList()
    private var rocketY : ArrayList<Float> = ArrayList()
    private var rockets : ArrayList<ImageView> = ArrayList()
    private var width : Int = 0
    private var height : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        gestureDetector = GestureDetectorCompat(this, MyGestureListener())
        val metrics = this.resources.displayMetrics
        this.height = metrics.heightPixels
        this.width = metrics.widthPixels
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) {
            rockets.add(rocket1)
            rockets.add(rocket2)
            rockets.add(rocket3)
            rockets.add(rocket4)

            var mdl = MyDragListener()
            for(i in 0 until rockets.size) {
                rocketX.add(rockets[i].x)
                rocketY.add(rockets[i].y)
                rockets[i].setOnClickListener {
                    if(it.rotation == 360f) {
                        it.rotation = 90f
                    } else {
                        it.rotation += 90
                    }
                }
                rockets[i].setOnLongClickListener { v: View ->

                    val data = ClipData.newPlainText("", "");

                    v.startDragAndDrop(
                        data,
                        View.DragShadowBuilder(v),
                        v,
                        0
                    )
                    true
                }
                rockets[i].setOnDragListener(mdl)
            }
            mainLayout.setOnDragListener(mdl)
        }
    }

    //for gestures
    override fun onTouchEvent(event: MotionEvent) : Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun moveTo(target: Float, isX: Boolean, view: ImageView) {

        var direction = "translationY"
        if(isX) {
            direction = "translationX"
        }

        val x = ObjectAnimator.ofFloat(
            view,
            direction,
            view.translationX,
            target
        )
        x.duration = 3000
        x.start()

    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            //reset positions
            for(i in 0 until rockets.size) {
                rockets[i].x = rocketX[i]
                rockets[i].y = rocketY[i]
            }
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val xDif = e2.rawX - e1.rawX
            val yDif = e2.rawY - e1.rawY

            //on right swipe
            if(xDif > 0 && abs(xDif) > 500){
                for(i in 0 until rockets.size) {
                    if(rockets[i].rotation == 90f) {
                        rocketX[i] = rockets[i].x
                        rocketY[i] = rockets[i].y
                        moveTo(rockets[i].x + 5000, true, rockets[i])
                    }
                }
            }

            if(xDif < 0 && abs(xDif) > 500){
                for(i in 0 until rockets.size) {
                    if(rockets[i].rotation == 270f) {
                        rocketX[i] = rockets[i].x
                        rocketY[i] = rockets[i].y
                        moveTo(rockets[i].x - 5000, true, rockets[i])
                    }
                }
            }
            //on up swipe
            if(yDif < 0 && abs(yDif) > 500){
                for(i in 0 until rockets.size) {
                    if(rockets[i].rotation == 360f || rockets[i].rotation == 0f) {
                        rocketX[i] = rockets[i].x
                        rocketY[i] = rockets[i].y
                        moveTo(rockets[i].y - 5000, false, rockets[i])
                    }
                }
            }

            if(yDif > 0 && abs(yDif) > 500){
                for(i in 0 until rockets.size) {
                    if(rockets[i].rotation == 180f) {
                        rocketX[i] = rockets[i].x
                        rocketY[i] = rockets[i].y
                        moveTo(rockets[i].y + 5000, false, rockets[i])
                    }
                }
            }
            return true
        }
    }

    private inner class MyDragListener : View.OnDragListener {

        private lateinit var img: View
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val action = event.action
            when(action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    if(event.localState != null) {
                        img = event.localState as View
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {}
                DragEvent.ACTION_DRAG_EXITED -> {}
                DragEvent.ACTION_DROP -> {
                    if(img != null) {
                        img.x = event.x - img.width / 2
                        img.y = event.y - img.height / 2
                    }
                }
                DragEvent.ACTION_DRAG_ENDED -> {}
                else -> {}

            }
            return true
        }
    }
}
