package rafaelmfer.customviews.extensions

import android.content.Context
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*

inline fun <reified T : Parcelable>
        T.fromJson(json: String): T = Gson().fromJson(json, T::class.java)

val <T : Parcelable> T.toJson
    get(): String = GsonBuilder().setPrettyPrinting()
        .create()
        .toJson(this)

fun Context.toFile(text: String, name: String = "config.txt") = try {
    OutputStreamWriter(openFileOutput(name, Context.MODE_PRIVATE)).run {
        write(text)
        close()
    }
    "Wrote file:\n\n$text"
} catch (ioException: IOException) {
    "File write failed: $ioException"
}

fun Context.fromFile(name: String = "config.txt") = try {
    val input = openFileInput(name)
    val reader = BufferedReader(InputStreamReader(input))
    val builder = StringBuilder()
    while (reader.readLine() != null) {
        builder.append(reader.readLine())
    }
    input.close()
    builder.toString()
} catch (notFound: FileNotFoundException) {
    "File not found: $notFound"
} catch (ioException: IOException) {
    "Can not read file: $ioException"
}