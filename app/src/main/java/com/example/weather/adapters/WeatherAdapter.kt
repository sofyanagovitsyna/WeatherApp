package com.example.weather.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Day
import com.example.weather.R
import com.example.weather.databinding.HourItemBinding
import com.squareup.picasso.Picasso

class WeatherAdapter() :ListAdapter <Day,WeatherAdapter.Holder >(Comparator()) {

    class Holder (view:View): RecyclerView.ViewHolder(view){
        val binding = HourItemBinding.bind(view)
        fun bind (item: Day) = with (binding){
            val t=item.time
            time.text=t.drop(11)
            temp.text = "${item.currentTemp.toFloat().toInt().toString()}°C"
            Picasso.get().load("https:" + item.img).into(imageView3)
        }
    }
    class  Comparator : DiffUtil.ItemCallback <Day> (){
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
val view = LayoutInflater.from (parent.context).inflate(R.layout.hour_item, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
holder.bind(getItem(position))
    }




}