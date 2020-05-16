package rafaelmfer.customviews.textfields

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import rafaelmfer.customviews.R
import rafaelmfer.customviews.extensions.setPaddingDP

class TextViewMaterialIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var attributesTypedArray: TypedArray

    init {
        typeface = ResourcesCompat.getFont(getContext(), R.font.material_design_icon_font_24px)
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        gravity = Gravity.CENTER
        attributesTypedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.TextViewMaterialIcon, 0, 0)
        background =
            attributesTypedArray.getDrawable(R.styleable.TextViewMaterialIcon_android_background)
        setPaddingDP(8)
    }
}