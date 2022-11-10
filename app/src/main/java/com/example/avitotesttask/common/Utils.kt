package com.example.avitotesttask.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.avitotesttask.data.network.NetworkUrl.IMAGE_URL
import java.text.SimpleDateFormat
import java.util.*

fun convertLongToDateString(systemTime: Long): String {
	return SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
		.format(systemTime).toString()
}


fun formatterToTime(date: String): String {
	return date.substring(11,16)
}
fun formatterToDay(date: String): String {
	return date.substring(3,5)
}
fun formatterToMonth(date: String): String {
	return date
}
fun dateParser(date: String): String {
	val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	return formatter.format(parser.parse(date))
}
fun getNameMonth(): String {
	val calendar: Calendar = Calendar.getInstance()
	return calendar.getDisplayName(Calendar.MONTH,
		Calendar.LONG_FORMAT, Locale("ru"))
}
fun getNumberToDay(): Int {
	val calendar: Calendar = Calendar.getInstance()
	return calendar.get(Calendar.DAY_OF_MONTH)
}
fun View.hideKeyboard() {
	val inputManager =
		context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun getPathImage(nameImg: String): String = "$IMAGE_URL$nameImg.png"

fun getToDayTime() = convertLongToDateString(System.currentTimeMillis()).substring(11, 16)

