package com.example.avitotesttask.domain.usecases

import com.example.avitotesttask.domain.interfaces.WeatherRepository
import com.example.avitotesttask.domain.models.Weather
import com.example.avitotesttask.domain.models.WeatherToWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetToWeekWeather(private val weatherRepository: WeatherRepository) {
	suspend fun getData(lat: Float, lon: Float, cityQ: String): List<WeatherToWeek> {
		val weekData = weatherRepository.getToWeekWeather(lat, lon, cityQ)
		val weekWeatherList = mutableListOf<WeatherToWeek>()
		val daysWeekWeatherList = mutableListOf<Weather>()

		var prevDay = LocalDate.now().dayOfMonth
		var prevMonth = LocalDate.now().month
		for (data in weekData.list) {
			val date =
				LocalDate.parse(data.dtTxt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			val time = data.dtTxt.substring(11, 16)
			if (date.dayOfMonth != prevDay) {
				val list = mutableListOf<Weather>()
				for (item in daysWeekWeatherList)
					list.add(item)
				weekWeatherList.add(WeatherToWeek(
					day = prevDay,
					month = prevMonth.name,
					weathersPeriod = list,
					city = weekData.city.name))
				prevMonth = date.month
				prevDay = date.dayOfMonth
				daysWeekWeatherList.clear()
			}

			daysWeekWeatherList.add(
				Weather(
					temperature = data.main.temp,
					feelsLike = data.main.feelsLike,
					humidity = data.main.humidity,
					weather = data.weather[0].description,
					icon = data.weather[0].icon,
					windSpeed = data.wind.speed,
					windDeg = data.wind.deg,
					dateTime = "$time",
				)
			)
		}
		return weekWeatherList
	}
}