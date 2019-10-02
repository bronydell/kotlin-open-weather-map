package com.danielvilha.kotlinopenweathermap.adapters

import com.danielvilha.kotlinopenweathermap.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.adapter_select_city.view.*

/**
 * Created by danielvilha on 2019-09-02
 */
class SelectCityAdapter(): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tvName.text = ""
        viewHolder.itemView.tvTemperature.text = ""

        Picasso.get().load("").into(viewHolder.itemView.image)
    }

    override fun getLayout(): Int {
        return R.layout.adapter_select_city
    }
}