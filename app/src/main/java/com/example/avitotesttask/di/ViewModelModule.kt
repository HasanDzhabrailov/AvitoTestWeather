package com.example.avitotesttask.di

import androidx.lifecycle.ViewModelProvider
import com.example.avitotesttask.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {

	@Binds
	abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}