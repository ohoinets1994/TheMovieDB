package com.example.themoviewdb

import androidx.room.Room
import com.example.themoviewdb.common.API_KEY
import com.example.themoviewdb.common.BASE_URL
import com.example.themoviewdb.repository.Repository
import com.example.themoviewdb.repository.TheMovieRepository
import com.example.themoviewdb.repository.database.AppDatabase
import com.example.themoviewdb.repository.database.DatabaseRepository
import com.example.themoviewdb.repository.network.NetworkRepository
import com.example.themoviewdb.repository.network.TheMovieApi
import com.example.themoviewdb.viewmodel.MovieViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val applicationModule = module {
    single { DatabaseRepository(Room.databaseBuilder(androidContext(), AppDatabase::class.java, "TheMovieDB").fallbackToDestructiveMigration().build()) }

    single { NetworkRepository(get()) }

    single { TheMovieRepository(androidContext()) } bind Repository::class
}

val viewModelModule = module {
    viewModel { MovieViewModel(get(), Dispatchers.IO) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(TheMovieApi::class.java)
    }
}