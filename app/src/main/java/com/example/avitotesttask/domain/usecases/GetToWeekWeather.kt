package com.example.avitotesttask.domain.usecases


import com.example.avitotesttask.common.*
import com.example.avitotesttask.domain.interfaces.WeatherRepository
import com.example.avitotesttask.domain.models.Weather
import com.example.avitotesttask.domain.models.WeatherToWeek

class GetToWeekWeather(private val weatherRepository: WeatherRepository) {
	suspend fun getData(lat: Float, lon: Float, cityQ: String): List<WeatherToWeek> {
		val weekData = weatherRepository.getToWeekWeather(lat, lon, cityQ)
		val weekWeatherList = mutableListOf<WeatherToWeek>()
		val daysWeekWeatherList = mutableListOf<Weather>()
		var prevDay = formatterToDay(convertLongToDateString(System.currentTimeMillis())).toInt()

		for (data in weekData.list) {
			val month = formatterToMonth(data.dtTxt)
			val day = formatterToDay(data.dtTxt).toInt()
			val time = formatterToTime(data.dtTxt)
			if (day != prevDay) {
				val list = mutableListOf<Weather>()
				for (item in daysWeekWeatherList)
					list.add(item)
				weekWeatherList.add(WeatherToWeek(
					day = prevDay,
					month = month,
					weathersPeriod = list))
				prevDay = day
				daysWeekWeatherList.clear()
			}
			daysWeekWeatherList.add(
				Weather(
					temperature = data.main.temp,
					feelsLike = data.main.feelsLike,
					humidity = data.main.humidity,
					weather = data.weather[0].main,
					icon = data.weather[0].icon,
					windSpeed = data.wind.speed,
					windDeg = data.wind.deg,
					dateTime = time,
				)
			)
		}
		return weekWeatherList
	}
}