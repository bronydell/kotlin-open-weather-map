package com.danielvilha.kotlinopenweathermap.service

import android.app.Activity
import android.content.Context
import com.danielvilha.kotlinopenweathermap.objects.OpenWeather
import com.google.gson.Gson
import com.google.gson.JsonObject


const val CITY_KEY = "saved_city"
const val CITY_DATA_KEY = "saved_city_data"

class SaveService {
    fun getSavedCity(activity: Activity?, defaultValue: String): String? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return defaultValue
        return sharedPref.getString(CITY_KEY, defaultValue)
    }

    fun saveCity(activity: Activity?, cityValue: String): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        with (sharedPref.edit()) {
            putString(CITY_KEY, cityValue)
            apply()
            return true
        }
    }

    fun getSavedCityData(activity: Activity?): OpenWeather? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return null
        val data = sharedPref.getString(CITY_DATA_KEY, null)
        if (data != null) {
            return Gson().fromJson(data, OpenWeather::class.java)
        }

        return null
    }

    fun saveCityData(activity: Activity?, data: OpenWeather): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
        with (sharedPref.edit()) {
            putString(CITY_DATA_KEY, Gson().toJson(data))
            apply()
            return true
        }
    }
}