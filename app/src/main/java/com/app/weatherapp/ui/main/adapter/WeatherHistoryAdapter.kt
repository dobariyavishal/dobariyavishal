package com.app.weatherapp.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.weatherapp.data.ListData
import com.app.weatherapp.databinding.ItemWeatherHistoryBinding
import com.app.weatherapp.util.getImagePath
import com.app.weatherapp.util.loadImage
import com.app.weatherapp.util.temperature
import com.app.weatherapp.util.timeStampToDate

class WeatherHistoryAdapter(
    val context: Context,
    private val list: List<ListData>
) : RecyclerView.Adapter<WeatherHistoryAdapter.ViewHolder>() {
    lateinit var binding: ItemWeatherHistoryBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemWeatherHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.setIsRecyclable(false)
        binding.apply {
            val weatherData = list[pos]
            data = weatherData
            tvDate.text = weatherData.dt?.toLong()?.timeStampToDate() ?: ""
            weatherData.main?.let {
                it.temp?.let {
                    tvTemperature.text = it.temperature()
                }
            }
            weatherData.weather?.let { weather ->
                ivWeather.loadImage(
                    binding.root.context,
                    (weather[0].icon ?: "").getImagePath()
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var binding: ItemWeatherHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

}