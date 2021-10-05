package com.mvvm.kotlin.di

import com.mvvm.kotlin.ui.viewmodel.MatchesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MatchesViewModel(get(), get())
    }
}