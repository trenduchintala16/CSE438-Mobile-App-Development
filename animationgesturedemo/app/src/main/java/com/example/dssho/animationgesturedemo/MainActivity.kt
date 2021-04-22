package com.example.dssho.animationgesturedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.util.DisplayMetrics
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.view.DragEvent
import android.widget.LinearLayout
import android.view.ViewGroup
import android.R.attr.shape
import android.annotation.TargetApi
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.View.OnDragListener
import org.w3c.dom.CharacterData


class MainActivity : AppCompatActivity() {

    private lateinit var mDetector: GestureDetectorCompat
    private var height: Int = 0
    private var width: Int = 0
    private lateinit var faceView: View

    @RequiresApi(Build.VERSION_CODES.N)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        faceView = face
        mDetector = GestureDetectorCompat(this, MyGestureListener())

        val metrics = this.resources.displayMetrics
        this.height = metrics.heightPixels
        this.width = metrics.widthPixels

        faceView.setOnLongClickListener { v: View ->

            val data = ClipData.newPlainText("", "");

            v.startDragAndDrop(
                data,
                View.DragShadowBuilder(v),
                v,
                0
            )
            true
        }
        var mdl = MyDragListener()
        faceView.setOnDragListener(mdl)
        mainLayout.setOnDragListener(mdl)
    }

    override fun onTouchEvent(event: MotionEvent) : Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    fun moveTo(targetX: Float, targetY: Float) {

        val animSetXY = AnimatorSet()

        val x = ObjectAnimator.ofFloat(
            faceView,
            "translationX",
            faceView.translationX,
            targetX
        )

        val y = ObjectAnimator.ofFloat(
            faceView,
            "translationY",
            faceView.translationY,
            targetY
        )

        animSetXY.playTogether(x, y)
        animSetXY.duration = 300
        animSetXY.start()



    }


    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        private var swipedistance = 150

        override fun onLongPress(event: MotionEvent) {
            moveTo(this@MainActivity.width / 2f - face.width / 2, -this@MainActivity.height / 2f + face.height / 2f)
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            moveTo(-this@MainActivity.width / 2f + face.width / 2, this@MainActivity.height / 2f - face.height / 2f)
            return true
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            moveTo(0f, 0f)
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if(e1.x - e2.x > swipedistance) {
                moveTo(-this@MainActivity.width / 2f + face.width / 2, 0f)
                return true
            } else if (e2.x - e1.x > swipedistance) {
                moveTo(this@MainActivity.width / 2f - face.width / 2, 0f)
                return true
            } else if(e1.y - e2.y > swipedistance) {
                moveTo(0f, -this@MainActivity.height / 2f + face.height / 2f)
                return true
            } else if (e2.y - e1.y > swipedistance) {
                moveTo(0f, this@MainActivity.height / 2f - face.height / 2f)
                return true
            }
            return false
        }
    }

    private inner class MyDragListener : OnDragListener {

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
