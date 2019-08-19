package com.danielvilha.kotlinopenweathermap.service

import com.danielvilha.kotlinopenweathermap.objects.OpenWeather
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by danielvilha on 2019-08-19
 */
interface Service {

    @GET("forecast?q=Dublin,IE&APPID=1af9031b36210770055780c9e6c073f7")
    fun getWeather(): Deferred<Response<OpenWeather>>
}