package com.example.avitotesttask.data.models

data class WeathersListCity(
	val id: Int,
	val name: String,
	val coord: CityCoordinates,
	val country: String,
	val population: Int,
	val timeZone: Int,
	val sunrise: Long,
	val sunSet: Long
)
