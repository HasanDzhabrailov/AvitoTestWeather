package com.example.avitotesttask.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.example.avitotesttask.domain.interfaces.WeatherRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: WeatherRepository):ViewModel(){
}