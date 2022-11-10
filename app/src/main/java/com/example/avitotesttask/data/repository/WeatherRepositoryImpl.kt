package com.example.avitotesttask.data.repository

import com.example.avitotesttask.data.models.CurrentWeather
import com.example.avitotesttask.data.models.WeathersResponseBody
import com.example.avitotesttask.data.network.ApiWeather
import com.example.avitotesttask.domain.interfaces.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiWeather: ApiWeather) :
	WeatherRepository {
	override suspend fun getToDayWeather(
		lat: Float,
		lon: Float,
		cityQ: String,
	): WeathersResponseBody = apiWeather.getToDayWeather(lat, lon, cityQ)

	override suspend fun getToWeekWeather(
		lat: Float,
		lon: Float,
		cityQ: String,
	): WeathersResponseBody = apiWeather.getToWeekWeather(lat, lon, cityQ)

	override suspend fun getCurrentWeather(lat: Float, lon: Float, cityQ: String): CurrentWeather =
		apiWeather.getCurrentWeather(lat, lon, cityQ)
}