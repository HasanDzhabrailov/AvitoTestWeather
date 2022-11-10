package com.example.avitotesttask.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.avitotesttask.data.repository.WeatherRepositoryImpl
import com.example.avitotesttask.domain.models.Weather
import com.example.avitotesttask.domain.models.WeatherToWeek
import com.example.avitotesttask.domain.usecases.GetCurrentWeather
import com.example.avitotesttask.domain.usecases.GetToDayWeather
import com.example.avitotesttask.domain.usecases.GetToWeekWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: WeatherRepositoryImpl) :
	ViewModel() {
	private val _statusResponse = MutableLiveData<String>()
	val statusResponse: LiveData<String>
		get() = _statusResponse

	private val _currentWeather = MutableLiveData<Weather>()
	val currentWeather: LiveData<Weather>
		get() = _currentWeather

	private val _todayWeather = MutableLiveData<List<Weather>>()
	val todayWeather: LiveData<List<Weather>>
		get() = _todayWeather

	private val _weekWeather = MutableLiveData<List<WeatherToWeek>>()
	val weekWeather: LiveData<List<WeatherToWeek>>
		get() = _weekWeather

	private val getCurrentWeather by lazy(LazyThreadSafetyMode.NONE) {
		GetCurrentWeather(repository)
	}
	private val getWeatherForPeriod by lazy(LazyThreadSafetyMode.NONE) {
		GetToDayWeather(repository)
	}
	private val getWeatherForWeek by lazy(LazyThreadSafetyMode.NONE) {
		GetToWeekWeather(repository)
	}

	fun getCurrentWeather(lat: Float = 42.9764F, lon: Float = 47.5024F, cityQ: String = "") =
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_currentWeather.postValue(getCurrentWeather.getData(lat, lon, cityQ))
				_statusResponse.postValue("200")
			} catch (ex: Exception) {
				_statusResponse.postValue("404")
			}
		}

	fun getTodayWeather(lat: Float = 42.9764F, lon: Float = 47.5024F, cityQ: String = "") =
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_todayWeather.postValue(getWeatherForPeriod.getData(lat, lon, cityQ))
				_statusResponse.postValue("200")
			} catch (ex: Exception) {
				_statusResponse.postValue("404")
			}

		}

	fun getWeatherForWeek(lat: Float = 42.9764F, lon: Float = 47.5024F, cityQ: String = "") =
		viewModelScope.launch(Dispatchers.IO) {
			try {
				_weekWeather.postValue(getWeatherForWeek.getData(lat, lon, cityQ))
				_statusResponse.postValue("200")
			} catch (ex: Exception) {
				_statusResponse.postValue("404")
			}
		}
}