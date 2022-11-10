package com.example.avitotesttask.domain.usecases

import com.example.avitotesttask.common.formatterToTime
import com.example.avitotesttask.common.getToDayTime
import com.example.avitotesttask.domain.interfaces.WeatherRepository
import com.example.avitotesttask.domain.models.Weather
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetToDayWeather(private val weatherRepository: WeatherRepository) {
	suspend fun getData(lat: Float, lon: Float, cityQ: String): List<Weather> {

		val toDayData = weatherRepository.getToDayWeather(lat, lon, cityQ)
		val listWeather = mutableListOf<Weather>()
		for (data in toDayData.list){
			val date = LocalDate.parse(data.dtTxt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			val time = data.dtTxt.substring(11,17)
			if(date.dayOfMonth != LocalDate.now().dayOfMonth)
				continue
			listWeather.add(Weather(
				temperature = data.main.temp,
				feelsLike = data.main.feelsLike,
				humidity = data.main.humidity,
				weather = data.weather[0].description,
				icon = data.weather[0].icon,
				windSpeed = data.wind.speed,
				windDeg = data.wind.deg,
				dateTime = time,
			))
		}
		return listWeather
	}
}