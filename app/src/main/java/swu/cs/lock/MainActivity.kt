package swu.cs.lock

import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Rect
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.util.DisplayMetrics
import android.util.EventLog
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val dots : Array<ImageView> by lazy {
        arrayOf(imageView11,imageView12,imageView13,imageView14,imageView15,imageView16,imageView17,imageView18,imageView19)
    }
    private val lines : Array<ImageView> by lazy {
        arrayOf(imageView1_2,imageView1_4,imageView1_5,imageView2_3,imageView2_4,imageView2_5,imageView2_6,imageView3_5,imageView3_6,imageView4_5,imageView4_7,imageView4_8,imageView5_6,imageView5_7,imageView5_8,imageView5_9,imageView6_8,imageView6_9,imageView7_8,imageView8_9)
    }
    private val sDots = mutableListOf<ImageView>()
    private var forceDot: ImageView? = null
    private var password:String= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            //手指按下屏幕
            MotionEvent.ACTION_DOWN -> {
                findIfIn(event)
            }
            //移动手指
            MotionEvent.ACTION_MOVE -> {
                findIfIn(event)
            }
            //手指离开屏幕
            MotionEvent.ACTION_UP -> {
                for(dot in sDots){
                    dot.visibility = View.INVISIBLE
                }
                Log.v("cs", password)
                password = ""
            }


            MotionEvent.ACTION_CANCEL ->{
                Log.v("cs","被其他程序打断")
            }
        }
        return true
    }
    private fun findIfIn(event: MotionEvent) {
        for (dot in dots) {
            val rect = Rect()
            dot.requestRectangleOnScreen(rect)
            rect.right = rect.left + dot.width
            rect.bottom = rect.top + dot.height
            if (rect.contains(event.x.toInt(),event.y.toInt()) && dot.visibility == View.INVISIBLE) {
                dot.visibility = View.VISIBLE
                sDots.add(dot)
                password += dot.tag.toString()
                line(dot)
                forceDot = dot
            }
        }
    }
    private fun line(dot:ImageView){
        if(forceDot != null) {
            for (line in lines) {
                if(forceDot?.tag.toString()+dot.tag.toString() == line.tag.toString() || dot.tag.toString()+forceDot?.tag.toString() == line.tag.toString() && line.visibility == View.INVISIBLE){
                    line.visibility = View.VISIBLE
                    sDots.add(line)
                }
            }
        }
    }
}

