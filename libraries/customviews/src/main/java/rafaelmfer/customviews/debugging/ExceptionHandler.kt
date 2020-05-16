package rafaelmfer.customviews.debugging

import android.content.Intent
import android.os.Build.*
import android.os.Build.VERSION.*
import android.os.Process.killProcess
import android.os.Process.myPid
import rafaelmfer.customviews.baseviews.ActBase.Companion.currentActivity
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionHandler : Thread.UncaughtExceptionHandler {

    companion object {
        const val EXCEPTION_MESSAGE = "message"
        const val EXCEPTION_LOG = "log"
        const val EXCEPTION_DEVICE = "device"
    }

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        currentActivity.run {
            startActivity(
                Intent(currentActivity, ActException::class.java)
                    .putExtra(EXCEPTION_MESSAGE, exception.message)
                    .putExtra(EXCEPTION_LOG, exception.formattedLog)
                    .putExtra(
                        EXCEPTION_DEVICE,
                        deviceInfo
                    )
            )
            finish()
        }
        killProcess(myPid())
    }
}

private val Throwable.formattedLog
    get() = StringBuilder().apply {
        val stackTrace = StringWriter()
        printStackTrace(PrintWriter(stackTrace))

        append(
            stackTrace.toString()
                .replace("java.lang.", "")
                .replace("Exception", "Ex\n\n")
//                .replace(APPLICATION_ID, "")
                .replace("at ", "")
                .replace("(", "\n(")
                .replace(")", ")\n")
        )
    }.toString()

private val deviceInfo
    get() = StringBuilder().apply {
        val tab = "            "
        append("\n\n\nDevice Information")
        append("\n\n$tab Brand: $BRAND")
        append("\n\n$tab Device: $DEVICE")
        append("\n\n$tab Model}: $MODEL")
        append("\n\n$tab Id: $ID")
        append("\n\n$tab Product: $PRODUCT")

        append("\n\n\nBuild Info")
        append("\n\n$tab SDK: $SDK_INT")
        append("\n\n$tab Release: $RELEASE")
        append("\n\n$tab Incremental: $INCREMENTAL")
    }.toString()