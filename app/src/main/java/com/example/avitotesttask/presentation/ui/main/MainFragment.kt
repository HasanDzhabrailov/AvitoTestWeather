package com.example.avitotesttask.presentation.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.avitotesttask.R
import com.example.avitotesttask.common.autoCleared
import com.example.avitotesttask.common.getPathImage
import com.example.avitotesttask.common.hideKeyboard
import com.example.avitotesttask.databinding.FragmentMainBinding
import com.example.avitotesttask.di.Injectable
import javax.inject.Inject


class MainFragment : Fragment(), Injectable, LocationListener {
	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	lateinit var mainViewModel: MainViewModel
	lateinit var weekAdapter: WeekAdapter

	private lateinit var weatherAdapter: WeatherAdapter
	private lateinit var locationManager: LocationManager
	private var binding by autoCleared<FragmentMainBinding>()
	private var lon: Float = 0f
	private var lat: Float = 0f
	private var checkLoadGPS: Boolean = false
	private fun clickSearchBtn(city: String = "Махачкала") {
		mainViewModel.getTodayWeather(cityQ = city)
		mainViewModel.getCurrentWeather(cityQ = city)
		mainViewModel.getWeatherForWeek(cityQ = city)
	}

	private fun getDefineLocation(lat: Float, lon: Float) {
		mainViewModel.getWeatherForWeek(lat = lat, lon = lon)
		mainViewModel.getCurrentWeather(lat = lat, lon = lon)
		mainViewModel.getTodayWeather(lat = lat, lon = lon)
	}


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
		mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
		weatherAdapter = WeatherAdapter()
		weekAdapter = WeekAdapter()
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED
		) {
			val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION)
			ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
		}
		binding.also { b ->
			b.mainViewModel = mainViewModel
			b.lifecycleOwner = viewLifecycleOwner

			b.toDayRecyclerView.adapter = weatherAdapter
			b.toDayRecyclerView.layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


			b.weatherWeekRecyclerView.adapter = weekAdapter
			b.weatherWeekRecyclerView.layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			b.weatherWeekRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),
				DividerItemDecoration.VERTICAL))

			mainViewModel.currentWeather.observe(viewLifecycleOwner) { weather ->
				b.currentWeather.visibility = View.VISIBLE
				b.textTime.text = weather.dateTime
				b.textTemperature.text = weather.temperature.toInt().toString() + "°"
				b.textWeather.text = weather.weather
				b.textFeelsLike.text = "Ощущается как: " + weather.feelsLike.toInt().toString()
				b.textWindDeg.text = "Градус ветра: " + weather.windDeg.toString() + "°"
				b.textWindSpeed.text = "Скорость ветра: " + weather.windSpeed.toInt().toString()
				b.textHumidity.text = "Влажность: " + weather.humidity.toString()
				Glide.with(b.iconWeather)
					.load(getPathImage(weather.icon))
					.into(b.iconWeather)
			}

			b.btnSearch.setOnClickListener {
				if (b.searchCity.text.isNotEmpty()) {
					clickSearchBtn(b.searchCity.text.toString())
					b.cityCurrent.text = b.searchCity.text.toString()
					it.hideKeyboard()
				}

				b.searchCity.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
					if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
						v.hideKeyboard()
						return@OnKeyListener true
					}

					false
				})
			}

			b.btnDefineLocation.setOnClickListener {
				locationManager =
					requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5f, this)
				if (checkLoadGPS) getDefineLocation(lat, lon)

			}
		}

		//Check if the entered city exists
		mainViewModel.statusResponse.observe(viewLifecycleOwner) { status ->
			when (status) {
				"404" -> {
					binding.also { b ->
						b.cityCurrent.visibility = View.GONE
						b.currentWeather.visibility = View.INVISIBLE
						b.toDayRecyclerView.visibility = View.INVISIBLE
						b.weatherWeekRecyclerView.visibility = View.INVISIBLE
						b.labelNowText.visibility = View.INVISIBLE
						b.labelWeekText.visibility = View.INVISIBLE
						b.searchFailed.visibility = View.VISIBLE
					}
				}
				"200" -> {
					binding.also { b ->
						b.currentWeather.visibility = View.VISIBLE
						b.toDayRecyclerView.visibility = View.VISIBLE
						b.weatherWeekRecyclerView.visibility = View.VISIBLE
						b.searchFailed.visibility = View.GONE
					}
				}
			}

		}

		mainViewModel.todayWeather.observe(viewLifecycleOwner) { weatherList ->
			binding.cityCurrent.visibility = View.VISIBLE
			weatherAdapter.setItems(weatherList)
			binding.labelNowText.visibility = View.VISIBLE
		}
		mainViewModel.weekWeather.observe(viewLifecycleOwner) { weekWeatherList ->
			binding.cityCurrent.visibility = View.VISIBLE
			weekAdapter.setItems(weekWeatherList)
			binding.labelWeekText.visibility = View.VISIBLE
			binding.cityCurrent.text = weekWeatherList[0].city
		}
	}

	override fun onLocationChanged(location: Location) {
		lat = location.latitude.toFloat()
		lon = location.longitude.toFloat()
		getDefineLocation(location.latitude.toFloat(), location.longitude.toFloat())
		checkLoadGPS = true
	}
}