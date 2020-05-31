package rafaelmfer.customviews.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.TypedValue
import rafaelmfer.customviews.R
import rafaelmfer.customviews.extensions.toast

fun Context.lazyDialog(title: String, message: String) =
    LazyDialog(this, title, message)

private const val DEFAULT_TEXT = "Whiskas"

class LazyDialog(
    context: Context,
    title: String = DEFAULT_TEXT,
    message: String = DEFAULT_TEXT,
    theme: Int = R.attr.alertDialogTheme
) : AlertDialog.Builder(context, context.resolveAttribute(theme)) {

    constructor(context: Context) : this(context, "", "")

    var textPositive = "OK!"
    var textNeutral = "Isentão"
    var textNegative = "NO!"

    var onPositive = DialogInterface.OnClickListener { _, _ -> context.toast(textPositive) }
        set(value) {
            field = value
            setPositiveButton(textPositive, onPositive)
        }

    var onNeutral = DialogInterface.OnClickListener { _, _ -> context.toast(textNeutral) }
        set(value) {
            field = value
            setNeutralButton(textNeutral, onNeutral)
        }

    var onNegative = DialogInterface.OnClickListener { _, _ -> context.toast(textNegative) }
        set(value) {
            field = value
            setNegativeButton(textNegative, onNegative)
        }

    init {
        setView(0)
        setTitle(title)
        setMessage(message)
        setPositiveButton(textPositive, onPositive)
        setNeutralButton(textNeutral, onNeutral)
        setNegativeButton(textNegative, onNegative)
    }
}

private fun Context.resolveAttribute(attr: Int) = TypedValue().apply {
    theme.resolveAttribute(attr, this, true)
}.resourceId