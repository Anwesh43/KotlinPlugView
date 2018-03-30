package ui.anwesome.com.plugview

/**
 * Created by anweshmishra on 31/03/18.
 */
import android.app.Activity
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
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2* prevScale
                startcb()
            }
        }
     }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate (updatecb : () -> Unit) {
            if (animated) {
                try {
                    updatecb()
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start () {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
    data class Plug (var i : Int, val state : State = State()) {
        fun draw (canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val size = Math.min(w, h)/ 4
            paint.strokeWidth = size/12
            paint.style = Paint.Style.STROKE
            paint.color = Color.WHITE
            paint.strokeCap = Paint.Cap.ROUND
            canvas.save()
            canvas.translate(-size/10 + (w / 2 + size/10) * state.scales[0] , h / 2)
            canvas.rotate(90f * state.scales[1])
            canvas.drawArc(RectF(-size, -size/2, 0f, size/2), -90f, 180f, true, paint)
            canvas.save()
            canvas.translate(- (h/2+size/10) * state.scales[2], 0f)
            for (i in 0..1) {
                val y : Float = -size/4 * (1 - 2 * i)
                canvas.drawLine(-size/2 - size/3 , y, -size/2, y, paint)
            }
            canvas.restore()
            canvas.restore()
        }
        fun update (stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer (var view : PlugView) {
        val plug : Plug = Plug(0)
        val animator : Animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            plug.draw(canvas, paint)
            animator.animate {
                plug.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            plug.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity) : PlugView {
            val view : PlugView = PlugView(activity)
            activity.setContentView(view)
            return view
        }
    }
}