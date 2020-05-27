package com.danielvilha.kotlinopenweathermap


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danielvilha.kotlinopenweathermap.adapters.WeatherAdapter
import com.danielvilha.kotlinopenweathermap.objects.OpenWeather
import com.danielvilha.kotlinopenweathermap.service.ApiFactory
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
            val request = service.getWeather()

            try {
                val response = request.await()
                if (response.isSuccessful) {
                    Log.v(TAG, response.body().toString())
                    isVisibleWeather(true)
                    openWeather = response.body()

                    tvWeather.text = openWeather.toString()
                    tvCityName.text = "${openWeather!!.city.name} - ${openWeather!!.city.country}"
                    tvLatLon.text = "Lat: ${openWeather!!.city.coord.lat} - Lon: ${openWeather!!.city.coord.lon}"

                    for (item in openWeather!!.list) {
                        adapter.add(WeatherAdapter(item))
                    }

                } else {
                    Log.d(TAG, response.errorBody().toString())
                    isVisibleWeather(isVisibility = false, error = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        recycler.adapter = adapter
    }
    //endregion

    //region isVisibleWeather
    private fun isVisibleWeather(isVisibility: Boolean, error: Boolean = false) {
        if (error) {
            weather.visibility = if (isVisibility) View.VISIBLE else View.GONE
            loading.visibility = if (!isVisibility) View.VISIBLE else View.GONE
            tvLoadingTryAgain.text = resources.getString(R.string.try_again)
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
