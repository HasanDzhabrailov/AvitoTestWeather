package com.example.avitotesttask.data.models

import com.google.gson.annotations.SerializedName

data class WeathersList (
	val dt: Long,
	val main: WeathersListMain,
	val weather: List<WeathersListWeather>,
	val clouds: WeathersListClouds ,
	val wind: WeathersListWind,
	val visibility: Int,
	val pop: Float,
	@SerializedName("dt_txt")
	val dtTxt: String
		)
