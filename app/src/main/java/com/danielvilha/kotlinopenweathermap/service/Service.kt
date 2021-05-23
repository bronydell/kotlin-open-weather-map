package com.danielvilha.kotlinopenweathermap.service

import com.danielvilha.kotlinopenweathermap.objects.OpenWeather
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by danielvilha on 2019-08-19
 */
interface Service {

    // TODO: Might be a good idea to separate APPID from the request
    @GET("forecast?APPID=1af9031b36210770055780c9e6c073f7")
    fun getWeather(@Query("q") cityName: String): Deferred<Response<OpenWeather>>
}