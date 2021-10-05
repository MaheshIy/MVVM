package com.mvvm.kotlin.di

import android.app.Application
import androidx.room.Room
import com.mvvm.kotlin.data.DatabaseHelperImpl
import com.mvvm.kotlin.data.room.AppDatabase
import com.mvvm.kotlin.data.room.DatabaseHelper
import com.mvvm.kotlin.data.room.dao.RepositoryDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single { provideDatabaseModule(androidApplication()) }
    single { provideRepositoriesDao(get()) }

    single<DatabaseHelper> {
        return@single DatabaseHelperImpl(get())
    }
}

fun provideDatabaseModule(application: Application): AppDatabase {
    return Room.databaseBuilder(application, AppDatabase::class.java, "repositories")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideRepositoriesDao(database: AppDatabase): RepositoryDao {
    return database.repositoryDao()
}