package rafaelmfer.customviews.extensions

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.R.attr.selectableItemBackground
import android.R.attr.selectableItemBackgroundBorderless
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.O
import android.os.Environment.getExternalStorageDirectory
import android.text.SpannableString
import android.text.Spanned
import android.text.format.DateFormat.format
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_FOCUSED
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast.LENGTH_LONG
import androidx.constraintlayout.widget.Constraints
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import rafaelmfer.customviews.R
import java.io.File
import java.util.*
import kotlin.math.roundToInt
import kotlin.reflect.KClass

const val appName = "fun"
const val DEFAULT_PADDING: Int = 16
const val DOUBLE_PADDING: Int = DEFAULT_PADDING * 2
const val DEFAULT_QUICK_ANIM: Long = 100
const val DEFAULT_ANIM_DURATION: Long = 250
const val DEFAULT_WAIT_FOR_ANIM: Long = DEFAULT_ANIM_DURATION + DEFAULT_QUICK_ANIM
const val QUALITY: Int = 50
const val LENGTH_MONTH_AND_DAY = 2
const val DEFAULT_ICON_WIDTH = 64
const val ACTION_BAR_SIZE = 56
const val fun_BAR_SIZE = 88

fun View.asViewHolder() = object : RecyclerView.ViewHolder(this) {}

val EditText?.textOrEmptyString get() = this?.text?.toString() ?: EMPTY_STRING

fun View.setHeaderAccessibility() {
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.isHeading = true
        }
    })
}

fun View.setViewAsButtonForAccessibility() {
    viewAs(Button::class)
}

infix fun View.viewAs(className: KClass<*>) {
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.className = className.java.name
        }
    })
}

fun View.addBackgroundRipple(round: Boolean = false) =
    setBackgroundResource(resolveAttribute(if (round && SDK_INT >= LOLLIPOP) selectableItemBackgroundBorderless else selectableItemBackground))

fun View.resolveAttribute(id: Int) =
    TypedValue().apply { context.theme.resolveAttribute(id, this, true) }.resourceId

fun View.dpToPx(densityPixels: Int) =
    (densityPixels * (context.resources.displayMetrics.xdpi / DENSITY_DEFAULT)).roundToInt()

fun View.pxToDp(pixels: Int) =
    (pixels / (context.resources.displayMetrics.xdpi / DENSITY_DEFAULT)).roundToInt()

fun View.setPaddingDP(dp: Int = DEFAULT_PADDING) = dpToPx(dp).let { default ->
    setPadding(default, default, default, default)
    this
}

fun <T : View> T.setPaddingDP(
    start: Int = DEFAULT_PADDING,
    top: Int = DEFAULT_PADDING,
    end: Int = DEFAULT_PADDING,
    bottom: Int = DEFAULT_PADDING
) = this.apply { setPadding(dpToPx(start), dpToPx(top), dpToPx(end), dpToPx(bottom)) }

fun View.getFont(id: Int) = ResourcesCompat.getFont(context, id)

fun View.animateVisibility(
    toAlpha: Float = 1f,
    newVisibility: Int = View.VISIBLE,
    duration: Long = DEFAULT_ANIM_DURATION
) {
    animate().alpha(toAlpha).setDuration(duration).setListener(object : AnimatorListenerAdapter() {

        override fun onAnimationStart(animation: Animator?) {
            if (newVisibility == View.VISIBLE) visibility = newVisibility
        }

        override fun onAnimationEnd(animation: Animator?) {
            if (newVisibility != View.VISIBLE) visibility = newVisibility
        }
    })
}

fun View.animateExpand(
    expand: Boolean = true,
    duration: Long = DEFAULT_ANIM_DURATION,
    vertical: Boolean = true
) =
    AnimatorSet().run {
        interpolator = AccelerateDecelerateInterpolator()
        play(
            ValueAnimator.ofInt(
                if (vertical) height else width,
                newSizeValue(expand, vertical)
            ).apply {
                this.duration = duration
                addUpdateListener(this, vertical)
            })
        start()
    }

fun View.waitForDefaultAnim(block: () -> Unit) =
    postDelayed({ block.invoke() }, DEFAULT_WAIT_FOR_ANIM)

fun View.waitForQuickAnim(block: () -> Unit) = postDelayed({ block.invoke() }, DEFAULT_QUICK_ANIM)

fun View.nowFocus() {
    sendAccessibilityEvent(TYPE_VIEW_FOCUSED)
    requestFocus()
}

fun View.delayedFocus() = waitForDefaultAnim {
    sendAccessibilityEvent(TYPE_VIEW_FOCUSED)
    requestFocus()
}

fun View.addUpdateListener(valueAnimator: ValueAnimator, vertical: Boolean) =
    valueAnimator.addUpdateListener { animator ->
        (animator.animatedValue as Int).let {
            if (vertical) layoutParams.height = it else layoutParams.width = it
        }
        requestLayout()
    }

private fun View.newSizeValue(expand: Boolean, vertical: Boolean) = if (!expand) 0 else {
    measure(WRAP_CONTENT, WRAP_CONTENT)
    if (vertical) measuredHeight else measuredWidth
}

fun hasWritePermission(context: Context) =
    ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED

fun View.buildFile(name: String) = if (!hasWritePermission(context)) null else {
    buildDirAndFile(
        "${name.filter { it.isLetterOrDigit() }}${format(
            "yyyy-MM-dd_HH:mm:ss",
            Date()
        )}.jpg"
    )
}

@Suppress("DEPRECATION")
fun buildDirAndFile(name: String) = File(getExternalStorageDirectory(), appName).run {
    if (!exists()) mkdirs()
    val file = File(this, name)
    file.createNewFile()
    return@run file
}

fun View.createBitmap(printBGColor: Int) = if (!hasWritePermission(context)) null else {
    createBitmap(width, height, ARGB_8888).let { bitmap ->
        Canvas(bitmap).let { canvas ->
            if (background != null) background.draw(canvas) else canvas.drawColor(printBGColor)
            draw(canvas)
            bitmap
        }
    }
}

fun View.startImageIntent(action: String = ACTION_VIEW, uri: Uri?, chooserText: String? = "") =
    context?.startActivity(
        createChooser(
            Intent()
                .setAction(action).addFlags(FLAG_GRANT_READ_URI_PERMISSION)
                .setDataAndType(uri, "image/*").putExtra(EXTRA_STREAM, uri), chooserText
        )
    )

fun View.buildSpannable(string: CharSequence, style: Int) = SpannableString(string).apply {
    setSpan(TextAppearanceSpan(context, style), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun <T : View> T.enforceFocus(focus: Boolean = true) = apply {
    isFocusable = focus
    if (SDK_INT >= O) focusable = if (focus) View.FOCUSABLE else View.NOT_FOCUSABLE
    importantForAccessibility =
        if (focus) View.IMPORTANT_FOR_ACCESSIBILITY_YES else View.IMPORTANT_FOR_ACCESSIBILITY_NO
}

fun View.stringAny(text: Any?): CharSequence = context stringAny text

fun View.isHeightDiffMeasuredHeight() = run {
    measure(WRAP_CONTENT, WRAP_CONTENT)
    height != measuredHeight
}


fun View.constraintParams(
    pixelWidth: Int = WRAP_CONTENT,
    pixelHeight: Int = WRAP_CONTENT,
    block: (Constraints.LayoutParams.() -> Unit) = {}
) = Constraints.LayoutParams(pixelWidth, pixelHeight).also {
    layoutParams = it
    block.invoke(it)
}

fun View.linearParams(
    pixelWidth: Int = WRAP_CONTENT,
    pixelHeight: Int = WRAP_CONTENT,
    block: (LinearLayout.LayoutParams.() -> Unit) = {}
) =
    LinearLayout.LayoutParams(pixelWidth, pixelHeight).also {
        layoutParams = it
        block.invoke(it)
    }

//fun View.newCollumn(vararg arrayOfViews: View? = arrayOf()) =
//    LinearLayout(context).apply {
//        orientation = VERTICAL
//        linearParams(MATCH_PARENT, WRAP_CONTENT)
//        setPaddingDP(0, 8, 0, 8)
//        addViews(*arrayOfViews) { index, viewAdded ->
//            if (index > 0) viewAdded.linearMargins(VERTICAL)
//        }
//    }
//
//fun View.newRow(vararg arrayOfViews: View? = arrayOf()) =
//    LinearLayout(context).apply {
//        orientation = HORIZONTAL
//        linearParams(WRAP_CONTENT, MATCH_PARENT)
//        setPaddingDP(0, 8, 0, 8)
//        addViews(*arrayOfViews) { index, viewAdded ->
//            if (index > 0) viewAdded.linearMargins(HORIZONTAL)
//        }
//    }

const val MARGIN_LEFT = 0       // same value and effect as LinearLayout.HORIZONTAL
const val MARGIN_TOP = 1        // same value and effect as LinearLayout.VERTICAL
const val MARGIN_RIGHT = 2
const val MARGIN_BOTTOM = 3
const val MARGIN_LEFT_AND_RIGHT = 4
const val LEFT_AND_RIGHT_ANTI_PADDING = 5
const val MARGIN_TOP_AND_BOTTOM = 6
const val TOP_AND_BOTTOM_ANTI_PADDING = 7
const val MARGIN_ALL = 8

fun View.linearMargins(
    orient: Int = HORIZONTAL,
    margin: Int = DEFAULT_PADDING,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT
) =
    linearParams(width, height) {
        val dp = dpToPx(margin)
        val negativeDP = -dpToPx(margin + 4)
        when (orient) {
            MARGIN_LEFT -> setMargins(dp, 0, 0, 0)
            // same value and effect as LinearLayout.HORIZONTAL
            MARGIN_TOP -> setMargins(0, dp, 0, 0)
            // same value and effect as LinearLayout.VERTICAL
            MARGIN_RIGHT -> setMargins(0, 0, dp, 0)
            MARGIN_BOTTOM -> setMargins(0, 0, 0, dp)
            MARGIN_LEFT_AND_RIGHT -> setMargins(dp, 0, dp, 0)
            MARGIN_TOP_AND_BOTTOM -> setMargins(0, dp, 0, dp)
            LEFT_AND_RIGHT_ANTI_PADDING -> setMargins(negativeDP, 0, negativeDP, 0)
            TOP_AND_BOTTOM_ANTI_PADDING -> setMargins(0, negativeDP, 0, negativeDP)
            MARGIN_ALL -> setMargins(dp, dp, dp, dp)
        }
    }

fun View.linearPadding(
    orient: Int = HORIZONTAL,
    width: Int = WRAP_CONTENT,
    height: Int = WRAP_CONTENT,
    padding: Int = DOUBLE_PADDING
) =
    linearParams(width, height) {
        val dp = dpToPx(padding)
        val negativeDP = -dpToPx(padding + 4)
        when (orient) {
            MARGIN_LEFT -> setPadding(dp, 0, 0, 0)
            MARGIN_TOP -> setPadding(0, dp, 0, 0)
            MARGIN_RIGHT -> setPadding(0, 0, dp, 0)
            MARGIN_BOTTOM -> setPadding(0, 0, 0, dp)
            MARGIN_LEFT_AND_RIGHT -> setPadding(dp, 0, dp, 0)
            MARGIN_TOP_AND_BOTTOM -> setPadding(0, dp, 0, dp)
            LEFT_AND_RIGHT_ANTI_PADDING -> setPadding(negativeDP, 0, negativeDP, 0)
            TOP_AND_BOTTOM_ANTI_PADDING -> setPadding(0, negativeDP, 0, negativeDP)
            MARGIN_ALL -> setPadding(dp, dp, dp, dp)
        }
    }

fun View.addMargin(orient: Int = HORIZONTAL) = linearParams {
    when (orient) {
        HORIZONTAL -> setMargins(dpToPx(DOUBLE_PADDING), 0, 0, 0)
        else -> setMargins(0, dpToPx(DOUBLE_PADDING), 0, 0)
    }
}

fun View.newDividerView(
    dividerSize: Int = 1,
    orient: Int = VERTICAL,
    backgroundColor: Int = R.color.grey_500
) =
    View(context).apply {
        val dp = dpToPx(dividerSize)
        if (orient == VERTICAL) {
            linearMargins(LEFT_AND_RIGHT_ANTI_PADDING, width = MATCH_PARENT, height = dp)
        } else {
            linearMargins(TOP_AND_BOTTOM_ANTI_PADDING, width = dp, height = MATCH_PARENT)
        }
        setBGColor(backgroundColor)
    }


fun View.setBGColor(color: Int = R.color.primary) =
    setBackgroundColor(ContextCompat.getColor(context, color))

fun View.setBGDrawable(drawable: Int) {
    background = (ContextCompat.getDrawable(context, drawable))
}

fun DatePickerDialog.setColorButton(): DatePickerDialog {
    if (this.datePicker.getChildAt(0) != null &&
        (this.datePicker.getChildAt(0) as ViewGroup).getChildAt(
            0
        ) != null
    ) {
        (this.datePicker.getChildAt(0) as ViewGroup).getChildAt(0)
            .setBackgroundColor(ContextCompat.getColor(context, R.color.white))
    }
    return this
}

fun Array<out View>.setVisibility(visibility: Int = View.VISIBLE) =
    forEach { it.visibility = visibility }

fun MutableList<out View>.setVisibility(visibility: Int = View.VISIBLE) =
    forEach { it.visibility = visibility }

fun View.changeVisibility(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.changeToInvisible(invisible: Boolean) {
    this.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

val View.visible: View
    get() {
        visibility = View.VISIBLE
        return this
    }

val View.invisible: View
    get() {
        visibility = View.INVISIBLE
        return this
    }

val View.gone: View
    get() {
        visibility = View.GONE
        return this
    }

fun View.toggleVisibility() {
    visibility = when (visibility) {
        View.INVISIBLE, View.GONE -> View.VISIBLE
        else -> View.INVISIBLE
    }
}

fun <T : View> T.onGlobalLayoutListener(onGlobalLayout: T.() -> Unit) = viewTreeObserver.let {
    it.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            it.removeOnGlobalLayoutListener(this)
            onGlobalLayout.invoke(this@onGlobalLayoutListener)
        }
    })
}

fun View.setOnClickAndCallBack(onClick: (() -> Unit) -> Unit, callBack: (() -> Unit)) =
    setOnClickListener { onClick.invoke(callBack) }

fun View.getColor(color: Int) = ContextCompat.getColor(context, color)

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
inline fun View.obtainStyledAttributes(
    set: AttributeSet?,
    attrs: IntArray? = null,
    defStyle: Int = 0,
    block: TypedArray.() -> Unit = {}
) {
    if (set != null) {
        context.obtainStyledAttributes(set, attrs, 0, defStyle).run {
            block.invoke(this)
            recycle()
        }
    }
}

fun View.onClickStart(kClass: KClass<out Activity>) = setOnClickListener {
    context.startActivity(Intent(context, kClass.java))
}

infix fun View.onClick(block: View.() -> Unit) = setOnClickListener { block.invoke(this) }

infix fun View.setID(resource: Any?) {
    if (resource is Int) id = resource
}

fun View.onBackPressed() = context.scanForAct?.onBackPressed()

val TextView.isNotEmptyOrSuppressed
    get() = if (text.isNotEmpty()) true else {
        visibility = View.INVISIBLE
        setOnClickListener(null)
        enforceFocus(false)
        false
    }

infix fun View.getDrawable(resourceInt: Int): Drawable? = context.getDrawable(resourceInt)

//val View.backButtonContentDesc
//    get() = apply {
//        contentDescription = context.getString(R.string.button_description_back)
//    }

fun View.find(id: Int) = findViewById<View>(id)

val View.isScreenVertical get() = context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

val View.activity get() = context.scanForAct!!

fun Context.toast(message: Any?) = Toast.makeText(this, stringAny(message), LENGTH_LONG).show()

fun View.toast(message: Any?) = Toast.makeText(context, stringAny(message), LENGTH_LONG).show()

private const val STATUS_BAR_HEIGHT = "status_bar_height"
private const val DIMENSION = "dimen"
private const val SYSTEM_NAME = "android"

fun View.getDeviceStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier(STATUS_BAR_HEIGHT, DIMENSION, SYSTEM_NAME)
    if (resourceId > 0) result = resources.getDimensionPixelOffset(resourceId)
    return result
}

fun View.addStatusBarHeight() {
    val params = this.layoutParams as ViewGroup.LayoutParams
    params.height = params.height + getDeviceStatusBarHeight()
}

fun View.addMarginTopStatusBarHeight() {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams
    params.updateMargins(top = params.topMargin + getDeviceStatusBarHeight())
    this.layoutParams = params
}

fun View.setToolbarAccessibleBackButton() {
    setOnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                view.performClick()
                true
            }
            else -> false
        }
    }
}

fun View.configureBackButtonAction(activity: Activity) = setOnClickListener { activity.onBackPressed() }