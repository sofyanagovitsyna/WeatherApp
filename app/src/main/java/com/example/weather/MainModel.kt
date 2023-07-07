package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.adapters.WeatherAdapter2

class MainModel:ViewModel() {
    val liveDataCurrent = MutableLiveData <Day> ()
    val liveDataList = MutableLiveData <List <Day>> ()
}
