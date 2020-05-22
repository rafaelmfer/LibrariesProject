package rafaelmfer.customviews.extensions

import android.view.View
import androidx.core.widget.NestedScrollView
import kotlin.math.min

private const val ONE_FLOAT = 1f
private const val ALPHA_CONSTANT = 100

fun NestedScrollView.scaleViewOnScroll(
    view: View,
    maxHeight: Int,
    minHeight: Int,
    scrollSize: Int,
    shouldFade: Boolean
) {
    setOnScrollChangeListener(
        NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            getImageScale(
                maxHeight = maxHeight,
                minHeight = minHeight,
                maxScroll = scrollSize,
                yOffset = scrollY
            ).let { scale ->
                view.run {
                    pivotX = (measuredWidth / 2).toFloat()
                    pivotY = (measuredHeight / 2).toFloat()
                    scaleX = scale
                    scaleY = scale
                }
            }
        }
    )
    if (shouldFade) {
        this.viewTreeObserver.addOnScrollChangedListener {
            val maxDistance = view.height
            val movement = this.scrollY
            val alphaFactor = movement * ONE_FLOAT / (maxDistance - ALPHA_CONSTANT)

            if (movement in 0..maxDistance) view.alpha = 1 - alphaFactor
        }
    }
}

private fun getImageScale(maxHeight: Int, minHeight: Int, maxScroll: Int, yOffset: Int): Float {
    val effectiveOffset = min(yOffset, maxScroll)
    val maxShrink = ONE_FLOAT - minHeight.toFloat() / maxHeight.toFloat()
    val shrinkRatio = effectiveOffset.toFloat() / maxScroll.toFloat()
    return ONE_FLOAT - maxShrink * shrinkRatio
}