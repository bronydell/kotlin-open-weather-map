package com.danielvilha.kotlinopenweathermap

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danielvilha.kotlinopenweathermap.service.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by danielvilha on 2019-08-22
 */
@RunWith(AndroidJUnit4::class)
class WeatherTest {
    @Test
    fun weatherTest() {
        val service = ApiFactory.api

        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getWeather()

            try {
                val response = request.await()
                if (response.isSuccessful) {
                    Log.v("WeatherTest", response.body().toString())
                } else {
                    Log.d("WeatherTest", response.errorBody().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}