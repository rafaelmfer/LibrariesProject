package rafaelmfer.customviews.textwatcher

import android.text.Editable
import android.text.TextWatcher
import android.view.accessibility.AccessibilityEvent
import android.widget.EditText
import rafaelmfer.customviews.extensions.replaceAll

open class AccessibleTextWatcher(private val editText: EditText?) : TextWatcher {

    var nameBefore = ""
    var charDeleted = ""

    override fun afterTextChanged(editable: Editable?) {}
    override fun beforeTextChanged(char: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(char: CharSequence, start: Int, before: Int, count: Int) {
        if (before > count && nameBefore.isNotEmpty()) {
            charDeleted = nameBefore.removePrefix(char).replaceAll("[^\\w\\s]", "")
            if (charDeleted.isNotEmpty()) {
                if (charDeleted == " ") {
                    charDeleted = "espaço"
                }
                announce("$charDeleted excluído", editText)
            }
        } else if (maskWasAdded(char)) {
            announce("${char.last()}, Fim do campo", editText)
        }
        nameBefore = char.toString()
    }

    private fun maskWasAdded(char: CharSequence): Boolean = char.length >= 2 && char[char.lastIndex - 1] in ".()/-"

    private fun announce(text: String, editText: EditText?) {
        editText?.contentDescription = text
        editText?.sendAccessibilityEvent(AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION)
        editText?.contentDescription = ""
    }
}