package com.example.avitotesttask.domain.interfaces

import com.example.avitotesttask.data.models.CurrentWeather
import com.example.avitotesttask.data.models.WeathersResponseBody

interface WeatherRepository {
	suspend fun getToDayWeather(lat: Float, lon: Float, cityQ: String): WeathersResponseBody

	suspend fun getToWeekWeather(lat: Float, lon: Float, cityQ: String): WeathersResponseBody

	suspend fun getCurrentWeather(lat: Float, lon: Float, cityQ: String): CurrentWeather
}