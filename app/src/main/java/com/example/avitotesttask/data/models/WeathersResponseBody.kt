package com.example.avitotesttask.data.models

data class WeathersResponseBody(
	val cod: Int,
	val message: Int,
	val cnt: Int,
	val list: List<WeathersList>,
	val city: WeathersListCity
)
