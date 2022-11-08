package com.example.avitotesttask.data.models

import com.google.gson.annotations.SerializedName

data class WeathersListMain(
	val temp: Float,
	@SerializedName("feels_like")
	val feelsLike: Float,
	val humidity: Int,
)
