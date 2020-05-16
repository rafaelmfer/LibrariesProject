package rafaelmfer.customviews.extensions

import android.os.Bundle

@Suppress("UNCHECKED_CAST")
fun <T> Bundle.getAs(key: String): T? = if (!containsKey(key)) null else get(key) as T