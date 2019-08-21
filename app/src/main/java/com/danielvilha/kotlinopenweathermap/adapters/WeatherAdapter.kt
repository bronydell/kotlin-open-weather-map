package com.danielvilha.kotlinopenweathermap.adapters

import com.danielvilha.kotlinopenweathermap.R
import com.danielvilha.kotlinopenweathermap.objects.WeatherItem
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.adapter_weather.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter

/**
 * Created by danielvilha on 2019-08-20
 */
class WeatherAdapter(private var item: WeatherItem): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvDate.text = item.dt_txt
        viewHolder.itemView.tvTemp.text = kelvinToCelsius(item.main.temp)
        viewHolder.itemView.tvTempMin.text = kelvinToCelsius(item.main.temp_min)
        viewHolder.itemView.tvTempMax.text = kelvinToCelsius(item.main.temp_max)
        viewHolder.itemView.tvPressure.text = "${item.main.pressure} hPa"
        viewHolder.itemView.tvSeaLevel.text = "${item.main.sea_level} hPa"
        viewHolder.itemView.tvGrndLevel.text = "${item.main.grnd_level} hPa"
        viewHolder.itemView.tvHumidity.text = "${item.main.humidity.toInt()}%"
        viewHolder.itemView.tvTempKf.text = item.main.temp_kf.toString()

        viewHolder.itemView.tvMainDescription.text = "${item.weather[0].main} - ${item.weather[0].description}"
        viewHolder.itemView.tvSpeedDeg.text = "Speed: ${item.wind.speed} - Deg: ${item.wind.deg}"
    }

    override fun getLayout(): Int {
        return R.layout.adapter_weather
    }

    private fun kelvinToCelsius(kelvin: Float): String {
        return "${(kelvin - 273.15).toInt()}º"
    }
}