package com.example.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainModel:ViewModel() {
    val liveDataCurrent = MutableLiveData <Day> ()
    val liveDataList = MutableLiveData <List <Day>> ()
}