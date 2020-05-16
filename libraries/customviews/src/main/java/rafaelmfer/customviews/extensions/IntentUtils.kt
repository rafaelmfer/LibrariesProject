package rafaelmfer.customviews.extensions

import android.content.Context
import android.content.Intent

fun internalIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)