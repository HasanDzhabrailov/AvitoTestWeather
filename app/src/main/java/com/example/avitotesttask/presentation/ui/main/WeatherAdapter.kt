package com.example.avitotesttask.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avitotesttask.common.getPathImage
import com.example.avitotesttask.databinding.WeatherItemBinding
import com.example.avitotesttask.domain.models.Weather

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
	private var weathers = mutableListOf<Weather>()

	fun setItems(weatherList: List<Weather>) {
		weathers = weatherList.toMutableList()
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = WeatherItemBinding.inflate(inflater,parent,false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		weathers.getOrNull(position)?.let { weather ->
			holder.bind(weather)
		}
	}

	override fun getItemCount(): Int = weathers.count()

	class ViewHolder(private val binding: WeatherItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(weather: Weather) {
			binding.also { b ->
				b.textTime.text = weather.dateTime
				b.textTemperature.text = weather.temperature.toInt().toString() + "°"
				b.textWeather.text = weather.weather
				b.textFeelsLike.text = "Ощущается как: " + weather.feelsLike.toInt().toString()
				b.textWindDeg.text = "Градус ветра: " + weather.windDeg.toString() + "°"
				b.textWindSpeed.text = "Скорость ветра: " + weather.windSpeed.toInt().toString()
				b.textHumidity.text = "Влажность: " + weather.humidity.toString()
				Glide.with(b.root)
					.load(getPathImage(weather.icon))
					.into(b.iconWeather)
			}
		}
	}
}