package com.danielvilha.kotlinopenweathermap.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by danielvilha on 2019-08-19
 */
object RetrofitFactory {

    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(OkHttpClient().newBuilder().build())
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}