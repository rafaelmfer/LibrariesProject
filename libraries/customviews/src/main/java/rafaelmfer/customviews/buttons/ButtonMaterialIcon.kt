package rafaelmfer.customviews.buttons

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import rafaelmfer.customviews.R
import rafaelmfer.customviews.extensions.setPaddingDP

class ButtonMaterialIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var attributesTypedArray: TypedArray

    init {
        typeface = ResourcesCompat.getFont(getContext(), R.font.material_design_icon_font_24px)
        textSize = DEFAULT_TEXT_SIZE
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER
        setPaddingDP(12)
        attributesTypedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ButtonMaterialIcon, 0, 0)
        background =
            attributesTypedArray.getDrawable(R.styleable.ButtonMaterialIcon_android_background)
    }

    companion object {
        private const val DEFAULT_TEXT_SIZE = 24f
    }
}