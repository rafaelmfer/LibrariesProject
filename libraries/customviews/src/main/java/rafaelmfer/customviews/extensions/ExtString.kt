package rafaelmfer.customviews.extensions

import android.annotation.SuppressLint
import java.util.*

const val LINE_BREAK = "\n"
const val EMPTY_STRING = ""
const val DOT = "."
const val TAB_SPACE = "    "
const val DASH = "-"
const val BAR = "/"

val <T> T.string get() = toString()

fun String.replaceAll(regex: String, newValue: String) = this.replace(regex.toRegex(), newValue)

@SuppressLint("DefaultLocale")
fun String.capitalizeEveryWord(): String {
    val text = this.toLowerCase(Locale.ROOT).split(" ")
    var formattedText = ""
    text.forEach { formattedText += "${it.capitalize()} " }
    return formattedText.trim()
}

fun String.isZero() = try {
    toDouble() == 0.0
} catch (e: NumberFormatException) {
    false
}

fun String?.isNullOrEmptyOrZero() = isNullOrEmpty() || this?.isZero() ?: false

fun String.fillStringWithChar(charToFill: Char?, maxLength: Int): String {
    var term = this
    return if (term.length >= maxLength) term else {
        term = charToFill!! + term
        term.fillStringWithChar(charToFill, maxLength)
    }
}

fun String.formatWithMask(mask: String): String {
    if (this.contains("[(]|[)]|\\s|[-]".toRegex())) return this

    var aMask = mask
    for (i in 0 until this.length) aMask =
        aMask.replaceFirst("#".toRegex(), this.substring(i, i + 1))
    return aMask.replace("#".toRegex(), "")
}

fun String.isDigit(): Boolean {
    for (char in this) {
        if (!char.isDigit()) return false
    }
    return true
}