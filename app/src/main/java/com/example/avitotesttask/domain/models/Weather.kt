package com.example.avitotesttask.domain.models

data class Weather(
	val temperature: Float,
	val feelsLike: Float,
	val weather: String,
	val windSpeed: Float,
	val windDeg: Int,
	val humidity: Int,
	val dateTime: String,
	val icon: String,
)