package com.example.avitotesttask.di

import com.example.avitotesttask.data.network.ApiWeather
import com.example.avitotesttask.data.network.CustomInterceptor
import com.example.avitotesttask.data.network.NetworkUrl.BASE_URL
import com.example.avitotesttask.data.repository.WeatherRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
	@Singleton
	@Provides
	fun loggingHttp(): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor(CustomInterceptor())
			.build()
	}

	@Singleton
	@Provides
	fun provideRetrofit(gson: Gson): WeatherRepositoryImpl {
		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(loggingHttp())
			.build()
		val weatherApi = retrofit.create(ApiWeather::class.java)
		return WeatherRepositoryImpl(weatherApi)
	}


	@Provides
	fun provideGson(): Gson = GsonBuilder().create()




}