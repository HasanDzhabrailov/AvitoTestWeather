package com.example.avitotesttask.data.network


import com.example.avitotesttask.data.models.CurrentWeather
import com.example.avitotesttask.data.models.WeathersResponseBody
import com.example.avitotesttask.data.network.NetworkUrl.URL_CURRENT_WEATHER
import com.example.avitotesttask.data.network.NetworkUrl.URL_TO_DAY
import com.example.avitotesttask.data.network.NetworkUrl.URL_TO_WEEK
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * lat - latitude
 * lon - longitude
 * q - name city
 * */

interface ApiWeather {
	@GET(URL_TO_DAY)
	suspend fun getToDayWeather(
		@Query("lat") lat: Float = 42.9764F,
		@Query("lon") lon: Float = 47.5024F,
		@Query("q") cityQ: String = "",
	): WeathersResponseBody

	@GET(URL_TO_WEEK)
	suspend fun getToWeekWeather(
		@Query("lat") lat: Float = 42.9764F,
		@Query("lon") lon: Float = 47.5024F,
		@Query("q") cityQ: String = "",
	): WeathersResponseBody

	@GET(URL_CURRENT_WEATHER)
	suspend fun getCurrentWeather(
		@Query("lat") lat: Float = 42.9764F,
		@Query("lon") lon: Float = 47.5024F,
		@Query("q") cityQ: String = "",
	): CurrentWeather
}

