package com.example.avitotesttask.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.avitotesttask.databinding.WeekItemBinding
import com.example.avitotesttask.domain.models.WeatherToWeek

class WeekAdapter : RecyclerView.Adapter<WeekAdapter.ViewHolder>() {

	private var weathersWeeks = mutableListOf<WeatherToWeek>()
	fun setItems(weatherWeekList: List<WeatherToWeek>) {
		weathersWeeks = weatherWeekList.toMutableList()
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = WeekItemBinding.inflate(inflater)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		weathersWeeks.getOrNull(position)?.let { weatherToWeek ->
			holder.bind(weatherToWeek)
		}
	}

	override fun getItemCount(): Int = weathersWeeks.count()

	class ViewHolder(private val binding: WeekItemBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(weatherToWeek: WeatherToWeek) {
			val day = weatherToWeek.day.toString()
			val month = weatherToWeek.month
			binding.also { b ->
				b.day.text = "$day $month"
				val weatherAdapter = WeatherAdapter()
				b.weatherRecyclerview.adapter = weatherAdapter
				weatherAdapter.setItems(weatherToWeek.weathersPeriod)
			}
		}
	}
}