package com.danielvilha.kotlinopenweathermap


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by danielvilha on 2019-08-29
 */
class WeatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }
}
