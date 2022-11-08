package com.example.avitotesttask.domain.usecases

import com.example.avitotesttask.common.getToDayTime
import com.example.avitotesttask.domain.interfaces.WeatherRepository
import com.example.avitotesttask.domain.models.Weather

class GetCurrentWeather(private val weatherRepository: WeatherRepository) {
	suspend fun getData(lat: Float, lon: Float, cityQ: String): Weather {
		val currentData = weatherRepository.getCurrentWeather(lat, lon, cityQ)
		val dateTime = getToDayTime()

		return Weather(
			temperature = currentData.main.temp,
			feelsLike = currentData.main.feelsLike,
			humidity = currentData.main.humidity,
			weather = currentData.weather[0].main,
			icon = currentData.weather[0].icon,
			windSpeed = currentData.wind.speed,
			windDeg = currentData.wind.deg,
			dateTime = dateTime,
		)
	}
}