package rafaelmfer.customviews.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
private const val LOCALE_LANGUAGE = "pt"
private const val LOCALE_COUNTRY = "BR"

val locale = Locale(
    LOCALE_LANGUAGE,
    LOCALE_COUNTRY
)

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String.toDate(pattern: String = DATE_TIME_PATTERN): Date =
    SimpleDateFormat(pattern, locale).parse(this@toDate)

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun String.formatFromServer(serverPattern: String, pattern: String): String {
    val parse = SimpleDateFormat(serverPattern, locale)
    val formatterDate = SimpleDateFormat(pattern, locale)
    return formatterDate.format(parse.parse(this))
}