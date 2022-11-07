package com.example.avitotesttask.data.models

data class CurrentWeather(
	val coord: CityCoordinates,
	val weather: List<WeathersListWeather>,
	val main: WeathersListMain,
	val wind: WeathersListWind,
	val clouds: WeathersListClouds,
	val id: Int,
	val name: String,
	val cod: Int
)
