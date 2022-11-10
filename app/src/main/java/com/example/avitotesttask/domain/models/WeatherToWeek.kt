package com.example.avitotesttask.domain.models

data class WeatherToWeek(
	val month:String,
	val day:Int,
	val weathersPeriod: List<Weather>,
	val city: String
)
