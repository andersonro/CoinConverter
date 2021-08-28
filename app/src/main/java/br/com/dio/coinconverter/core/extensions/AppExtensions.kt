package br.com.dio.coinconverter.core.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

var TextInputLayout.text: String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Double.formatCurrency(locale: Locale = Locale.getDefault()): String {
    return NumberFormat.getCurrencyInstance(locale).format(this)
}

fun String.formatDateStr(dateString: Date): String {
    var simpleFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = simpleFormat.format(dateString)
    return date.toString()
}

fun DateTimeCurrent(): String {
    val dateString = Date()
    var simpleFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = simpleFormat.format(dateString)
    return date.toString()
}

fun String.formatDateBr(dateString: String): String {
    var simpleFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var simpleFormatBr = SimpleDateFormat("dd/MM/y HH:mm:ss")
    val date = simpleFormat.parse(dateString)
    return simpleFormatBr.format(date)
}