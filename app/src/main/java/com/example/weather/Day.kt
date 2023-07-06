package com.example.weather

data class Day(
    val city: String,
    val time: String,
    val condition: String,
    val img: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val wind: String,
    val humidity: String,
    val hours: String
)
