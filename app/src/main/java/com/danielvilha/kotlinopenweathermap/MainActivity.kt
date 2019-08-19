package com.danielvilha.kotlinopenweathermap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.danielvilha.kotlinopenweathermap.objects.OpenWeather
import com.danielvilha.kotlinopenweathermap.service.ApiFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var openWeather: OpenWeather? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    init {
        getWeather()
    }

    private fun getWeather() {
        val service = ApiFactory.api

        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getWeather()

            try {
                val response = request.await()
                if (response.isSuccessful) {
                    Log.v(TAG, response.body().toString())
                    openWeather = response.body()

                    textView.text = openWeather.toString()
                    tvCityName.text = "${openWeather!!.city.name} - ${openWeather!!.city.country}"
                    tvLat.text = "Lat: ${openWeather!!.city.coord.lat}"
                    tvLon.text = "Lon: ${openWeather!!.city.coord.lon}"

                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name.toString()
    }
}
