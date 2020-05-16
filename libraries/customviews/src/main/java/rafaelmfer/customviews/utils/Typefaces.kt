package rafaelmfer.customviews.utils

import android.content.Context
import android.graphics.Typeface
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

object Typefaces {

    private val tempFont = hashMapOf<Int, Typeface>()

    fun getTypeface(context: Context, @FontRes font: Int): Typeface? {
        if (tempFont[font] == null) {
            try {
                ResourcesCompat.getFont(context, font)?.run {
                    tempFont[font] = this
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return tempFont[font]
    }
}