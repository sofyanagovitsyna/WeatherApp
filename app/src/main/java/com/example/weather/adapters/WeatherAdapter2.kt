package com.example.weather.adapters


import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.All.Application2.Companion.applicationContext
import com.example.weather.Day
import com.example.weather.R
import com.example.weather.databinding.DayItemBinding
import com.squareup.picasso.Picasso


class WeatherAdapter2() :ListAdapter <Day,WeatherAdapter2.Holder >(Comparator()) {

    class Holder (view:View): RecyclerView.ViewHolder(view){
        val binding = DayItemBinding.bind(view)
        //var mcontext: Context = getApplicationContext()
        fun bind (item: Day) = with (binding){
            val MaxMinTemp = "${item.minTemp.toFloat().toInt().toString()}°C/${item.maxTemp.toFloat().toInt().toString()}°C"
            val t=item.time
            val s="${item.wind.toFloat().toInt().toString()} ${com.example.weather.All.Application2.applicationContext().resources.getString(R.string.speed)}"
           Log.d("MyLog1", "Wind: $s")
            date.text= t.drop(5)
            h.text = "${item.humidity.toFloat().toInt().toString()}%"
                    windspeed.text=s
            MinMax.text=MaxMinTemp
            Picasso.get().load("https:" + item.img).into(imageView4)
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
val view = LayoutInflater.from (parent.context).inflate(R.layout.day_item, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
holder.bind(getItem(position))
    }




}