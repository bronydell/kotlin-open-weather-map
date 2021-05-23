package com.danielvilha.kotlinopenweathermap


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.danielvilha.kotlinopenweathermap.adapters.WeatherAdapter
import com.danielvilha.kotlinopenweathermap.objects.OpenWeather
import com.danielvilha.kotlinopenweathermap.service.ApiFactory
import com.danielvilha.kotlinopenweathermap.service.SaveService
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.refresh_layout.*
import kotlinx.android.synthetic.main.weather_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by danielvilha on 2019-08-29
 */
class WeatherFragment : Fragment() {

    //region Variables
    private var openWeather: OpenWeather? = null
    private var saveService: SaveService = SaveService()
    private var adapter = GroupAdapter<ViewHolder>()
    //endregion

    //region onCreateView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }
    //endregion

    //region onStart
    override fun onStart() {
        super.onStart()

        editTextCityName.setText(saveService.getSavedCity(activity, "Minsk"))
        editTextCityName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getWeather()
                return@OnEditorActionListener true
            }
            false
        })

        getWeather()

        floating.setOnClickListener {
            getWeather()
        }
    }
    //endregion

    //region getWeather
    private fun getWeather() {
        val service = ApiFactory.api
        isVisibleWeather(false)

        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getWeather(editTextCityName.text.toString())
            var responseData: OpenWeather? = null
            try {
                val response = request.await()
                responseData = if (response.isSuccessful) {
                    Log.v(TAG, response.body().toString())
                    response.body()
                } else {
                    saveService.getSavedCityData(activity)
                }
            } catch (e: Exception) {
                responseData = saveService.getSavedCityData(activity)
                e.printStackTrace()
            }

            if (responseData != null) {
                fillWeather(responseData)
                isVisibleWeather(true)
                saveService.saveCity(activity, responseData.city.name)
                saveService.saveCityData(activity, responseData)
            }
            else {
                Toast.makeText(context, "Can't fetch the data or city name is wrong", Toast.LENGTH_SHORT).show()
                isVisibleWeather(isVisibility = false, error = "Cannot fetch the data and local data doesn't exist")
            }
        }

        recycler.adapter = adapter
    }
    //endregion

    private fun fillWeather(weatherData: OpenWeather) {
        openWeather = weatherData
        tvWeather.text = weatherData.toString()
        editTextCityName.setText(String.format(
            "%s, %s",
            weatherData.city.name,
            weatherData.city.country
        ))
        tvLatLon.text = String.format(
            "Lat: %s - Long: %s",
            weatherData.city.coord.lat,
            weatherData.city.coord.lon
        )

        for (item in weatherData.list) {
            adapter.add(WeatherAdapter(item))
        }
    }

    //region isVisibleWeather
    private fun isVisibleWeather(isVisibility: Boolean, error: String? = null) {
        if (!error.isNullOrBlank()) {
            weather.visibility = if (isVisibility) View.VISIBLE else View.GONE
            loading.visibility = if (!isVisibility) View.VISIBLE else View.GONE
            tvLoadingTryAgain.text = error
        } else {
            weather.visibility = if (isVisibility) View.VISIBLE else View.GONE
            loading.visibility = if (!isVisibility) View.VISIBLE else View.GONE
            tvLoadingTryAgain.text = resources.getString(R.string.loading)
        }
    }
    //endregion

    companion object {
        private val TAG = WeatherFragment::class.java.name
    }
}
