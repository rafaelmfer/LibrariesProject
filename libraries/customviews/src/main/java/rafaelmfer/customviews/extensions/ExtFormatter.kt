package rafaelmfer.customviews.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

private val FORMAT_WITH_SYMBOL = DecimalFormatMonetary.getBrazilianInstance()
private val brazilianCurrency = Currency.getInstance(locale)
private val moneyFormatter = NumberFormat.getCurrencyInstance(locale).apply { currency =
    brazilianCurrency
}

fun String.formatToBrazilianCurrency() = FORMAT_WITH_SYMBOL.format(this.toDouble())
fun Double.formatToBrazilianCurrency() = FORMAT_WITH_SYMBOL.format(this)
fun Double.formatToBrazilianMonetary() = moneyFormatter.format(this)
fun Double.formatForBrazilianMonetary() = DecimalFormat.getCurrencyInstance(locale).format(this)

fun Double.toPercentage(): String {
    val formatter = DecimalFormat().apply {
        decimalFormatSymbols = DecimalFormatSymbols(locale)
        isDecimalSeparatorAlwaysShown = false
    }
    return "${formatter.format(this)}%"
}

object DecimalFormatMonetary : DecimalFormat() {
    private fun newInstance(func: DecimalFormat.() -> Unit): DecimalFormat {
        val instance = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
        return instance.apply(func)
    }

    fun getBrazilianInstance() = newInstance {
        val symbol = "R$"
        negativePrefix = "-$symbol "
        negativeSuffix = ""
        positivePrefix = "$symbol "
        maximumFractionDigits = 2

        with(decimalFormatSymbols) {
            currencySymbol = symbol
            groupingSeparator = '.'
            monetaryDecimalSeparator = ','
        }
    }
}