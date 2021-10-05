package com.mvvm.kotlin.di

import com.mvvm.kotlin.data.repository.MatchesRepositories
import org.koin.dsl.module

val repoModule = module {

    single {
        MatchesRepositories(get(), get())
    }
}