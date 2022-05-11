package com.app.weatherapp.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.weatherapp.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(
    context: Context,
    url: String,
    placeholder: Int = R.drawable.ic_launcher_foreground
) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    val requestOptions = RequestOptions().placeholder(circularProgressDrawable).error(placeholder)
    Glide.with(context)
        .load(url)
        .apply(requestOptions)
        .into(this)
    Glide.with(context).load(url).apply(requestOptions).into(this)
}

fun String.getImagePath(): String {
    return "http://openweathermap.org/img/w/${this}.png"

}

fun Double.temperature(): String {
    // kelvin to celcius
    val b: Double = this.minus(273.15)
    return String.format("%.2f", b).plus("°C")
}

fun Long.timeStampToDate(): String {
    val timeD = Date(this * 1000)
    val sdf = SimpleDateFormat("EEE dd.MM.yyyy - HH:mm")
    return sdf.format(timeD)
}

