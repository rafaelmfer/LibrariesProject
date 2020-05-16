package rafaelmfer.customviews.toolbars

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import rafaelmfer.customviews.R
import rafaelmfer.customviews.extensions.changeVisibility
import rafaelmfer.customviews.extensions.configureBackButtonAction
import rafaelmfer.customviews.extensions.setHeaderAccessibility
import rafaelmfer.customviews.extensions.setToolbarAccessibleBackButton

class BasicToolbar @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val btBackButton by lazy { findViewById<Button>(R.id.navigation_left_button) }
    private val tvToolbarTitle by lazy { findViewById<TextView>(R.id.toolbar_title) }
    private val btCloseButton by lazy { findViewById<Button>(R.id.navigation_right_button) }

    private var showBackButton: Boolean
    private var showTitle: Boolean = true
    private var showCloseButton: Boolean

    private var attributesTypedArray: TypedArray

    private var listenerCloseButton: () -> Unit = {}

    var titleToolbar: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_toolbar_basic, this, true)
        attributesTypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.BasicToolbar, 0, 0)
        tvToolbarTitle.apply {
            text = attributesTypedArray.getString(R.styleable.BasicToolbar_toolbarTitle) ?: ""
            setHeaderAccessibility()
        }
        showBackButton = attributesTypedArray.getBoolean(R.styleable.BasicToolbar_showBackButton, true)
        showTitle = attributesTypedArray.getBoolean(R.styleable.BasicToolbar_showTitle, true)
        showCloseButton = attributesTypedArray.getBoolean(R.styleable.BasicToolbar_showCloseButton, false)

        initView()
        attributesTypedArray.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        btBackButton.setToolbarAccessibleBackButton()
    }

    private fun initView() {
        if (context is Activity) {
            btBackButton.run { configureBackButtonAction(context as Activity) }
        }
        btBackButton.changeVisibility(showBackButton)
        tvToolbarTitle.changeVisibility(showTitle)
        btCloseButton.changeVisibility(showCloseButton)
    }

    fun setCloseButtonAction(action: () -> Unit) {
        this.listenerCloseButton = action
    }

    fun updateTitleToolbar() {
        tvToolbarTitle.run {
            text = titleToolbar
            contentDescription = resources.getString(R.string.acs_toolbar_tittle, titleToolbar)
        }
    }

    fun setFocusOnBackButton() {
        btBackButton.requestFocus()
        btBackButton.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
    }

    fun setBackButtonText(text: String) {
        btBackButton.text = text
    }

    fun setBackButtonColor(color: Int) {
        btBackButton.setTextColor(color)
    }

    fun setCloseButtonText(text: String) {
        btCloseButton.text = text
    }

    fun setCloseButtonColor(color: Int) {
        btCloseButton.setTextColor(color)
    }
}
