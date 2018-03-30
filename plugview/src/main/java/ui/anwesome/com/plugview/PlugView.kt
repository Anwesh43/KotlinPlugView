package ui.anwesome.com.plugview

/**
 * Created by anweshmishra on 31/03/18.
 */
import android.content.Context
import android.graphics.*
import android.view.*
class PlugView (ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw (canvas : Canvas) {

    }
    override fun onTouchEvent (event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                
            }
        }
        return true
    }
}