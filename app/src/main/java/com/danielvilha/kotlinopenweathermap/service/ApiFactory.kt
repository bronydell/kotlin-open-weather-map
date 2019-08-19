package com.danielvilha.kotlinopenweathermap.service

/**
 * Created by danielvilha on 2019-08-19
 */
object ApiFactory {

    val api: Service = RetrofitFactory.retrofit().create(Service::class.java)
}