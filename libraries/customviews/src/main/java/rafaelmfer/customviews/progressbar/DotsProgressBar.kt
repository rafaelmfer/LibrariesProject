package rafaelmfer.customviews.progressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import androidx.core.content.ContextCompat
import rafaelmfer.customviews.R

class DotsProgressBar : View {

    private var dotRadius = 5
    private var activeDotRadius = 8
    private var dotsDistance = 20
    private val paint = Paint()

    private var dotPosition = 0
    private var dotCount = DEF_COUNT
    private var timeout = DEF_TIMEOUT
    private var dotColor = Color.parseColor(
        "#" + Integer.toHexString(
            ContextCompat.getColor(
                context,
                R.color.orange_500
            )
        )
    )

    constructor(context: Context?) : super(context) {
        initDotSize()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initDotSize()
        applyAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initDotSize()
        applyAttrs(context, attrs)
    }

    private fun initDotSize() {
        val scale = resources.displayMetrics.density
        dotsDistance = (dotsDistance * scale).toInt()
        dotRadius = (dotRadius * scale).toInt()
        activeDotRadius = (activeDotRadius * scale).toInt()
    }

    private fun applyAttrs(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DotsProgressBar, 0, 0).apply {
            dotColor = getColor(R.styleable.DotsProgressBar_dotColors, dotColor)
            dotCount = getInteger(R.styleable.DotsProgressBar_dotsCount, dotCount)
            dotCount = dotCount.coerceAtLeast(MIN_COUNT).coerceAtMost(MAX_COUNT)
            timeout = getInteger(R.styleable.DotsProgressBar_timeoutOfDots, timeout)
            timeout = timeout.coerceAtLeast(MIN_TIMEOUT).coerceAtMost(MAX_TIMEOUT)
            recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isShown) {
            paint.color = dotColor
            createDots(canvas, paint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    private fun createDots(canvas: Canvas, paint: Paint) {
        for (i in 0 until dotCount) {
            val radius = if (i == dotPosition) activeDotRadius else dotRadius
            canvas.drawCircle(
                dotsDistance / 2 + (i * dotsDistance).toFloat(),
                activeDotRadius.toFloat(),
                radius.toFloat(),
                paint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(dotsDistance * dotCount, activeDotRadius * 2)
    }

    private fun startAnimation() {
        startAnimation(BounceAnimation().apply {
            duration = timeout.toLong()
            repeatCount = Animation.INFINITE
            interpolator = LinearInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
                override fun onAnimationRepeat(animation: Animation) {
                    if (++dotPosition >= dotCount) {
                        dotPosition = 0
                    }
                }
            })
        })
    }

    private inner class BounceAnimation : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            super.applyTransformation(interpolatedTime, t)
            invalidate()
        }
    }

    companion object {
        private const val MIN_COUNT = 2
        private const val DEF_COUNT = 10
        private const val MAX_COUNT = 100
        private const val MIN_TIMEOUT = 100
        private const val DEF_TIMEOUT = 500
        private const val MAX_TIMEOUT = 3000
    }
}