package rafaelmfer.customviews.buttons

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import rafaelmfer.customviews.R
import rafaelmfer.customviews.extensions.setViewAsButtonForAccessibility

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val button by lazy { findViewById<Button>(R.id.button) }
    private val loadingOnButton by lazy { findViewById<ProgressBar>(R.id.loading_progress) }

    private var attributesTypedArray: TypedArray
    private val buttonText: String
    private val buttonTextColor: Int

    private var onClickListener: OnClickListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading_button, this, true)
        attributesTypedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0).apply {
                buttonText = getString(R.styleable.LoadingButton_android_text) ?: ""
                buttonTextColor = getColor(
                    R.styleable.LoadingButton_android_textColor,
                    ContextCompat.getColor(context, R.color.white)
                )
                setupButton(context)
                setupLoading(context)
                setViewAsButtonForAccessibility()
                recycle()
            }

    }

    private fun TypedArray.setupButton(context: Context) {
        button.apply {
            text = buttonText
            setTextColor(buttonTextColor)
            background =
                getDrawable(R.styleable.LoadingButton_android_background)
                    ?: ContextCompat.getDrawable(context, R.color.deep_purple_500)
            isEnabled =
                getBoolean(R.styleable.LoadingButton_enableButton, true)
        }
    }

    private fun TypedArray.setupLoading(context: Context) {
        loadingOnButton.apply {
            indeterminateDrawable.setColorFilter(
                getColor(
                    R.styleable.LoadingButton_colorProgressBar,
                    ContextCompat.getColor(context, R.color.white)
                ),
                PorterDuff.Mode.SRC_IN
            )
            visibility = if (getBoolean(
                    R.styleable.LoadingButton_showLoading,
                    false
                )
            ) View.VISIBLE else View.GONE
        }
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_UP &&
            (event.keyCode === KeyEvent.KEYCODE_DPAD_CENTER || event.keyCode === KeyEvent.KEYCODE_ENTER)
        ) {
            onClickListener?.onClick(this)
        }
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        isPressed = when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                true
            }
            MotionEvent.ACTION_UP -> {
                onClickListener?.onClick(this)
                false
            }
            else -> {
                false
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        onClickListener = l
    }

    fun setLoadingState(loadingState: Boolean) {
        loadingOnButton.visibility = if (loadingState) View.VISIBLE else View.GONE
        button.isClickable = !loadingState
        button.setTextColor(
            if (loadingState) ContextCompat.getColor(
                context,
                android.R.color.transparent
            ) else buttonTextColor
        )
    }
}