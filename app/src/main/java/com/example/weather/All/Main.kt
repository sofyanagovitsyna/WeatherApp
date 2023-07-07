package com.example.weather.All

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.Day
import com.example.weather.DialogManager
import com.example.weather.DialogManager2
import com.example.weather.MainModel
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.isPermissionGranted
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.Locale

object GlobalVariable {
  var cityRu: String ="Москва"
}
class Main (): Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private val model : MainModel by activityViewModels()
    private lateinit var fLocationClient: FusedLocationProviderClient
    //private  lateinit var cityRu: String
    //private lateinit var  swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //requestWeatherData("Moscow")
      //  updateCurrentCard()
      // checkPermission()
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if(isLocationEnabled()) {
            getLocation()
            getLocationRU()
        }
        //updateCurrentCard()
       // updateCurrentCard()

    }
    private  fun refresh()= with(binding){
        swipe?.setOnRefreshListener {

            fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

            getLocation()
            getLocationRU()
            updateCurrentCard()
            updateCurrentCard()
            getLocation()
            getLocationRU()
            updateCurrentCard()
            updateCurrentCard()
            swipe?.isRefreshing=false
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        init()
      //  checkPermission()
        if(Locale.getDefault().getDisplayLanguage()=="русский")
        {
            if(!isLocationEnabled()){
                requestWeatherData("Moscow")
                updateCurrentCard()
            }
            else {
                checkPermission()
                fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                getLocation()
                getLocationRU()
                updateCurrentCard()
                updateCurrentCard()
                fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                getLocation()
                getLocationRU()
                updateCurrentCard()
                updateCurrentCard()
            }

        }
else{
            checkPermission()
            updateCurrentCard()

}

        refresh()
    }

    override fun onResume() {
        super.onResume()
        checkLocation()

    }

    private fun init() = with(binding){
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun checkLocation(){
        if(isLocationEnabled()){
            getLocation()
        } else {
            DialogManager.locationSettingsDialog(requireContext(), object : DialogManager.Listener{
                override fun onClick(i: Int) {
                    if (i==1)
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    else
                    {
                        requestWeatherData("Moscow")
                        updateCurrentCard()
                    }
                }
            })
        }
    }

    private fun isLocationEnabled(): Boolean{
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getLocation(){
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
            permissionListener()
          //  checkPermission()
        }
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener{
                Log.d("MyLog1", "Time: ${it.result.latitude}")
                requestCity("${it.result.latitude}","${it.result.longitude}")
                requestWeatherData("${it.result.latitude},${it.result.longitude}")

            }
    }
    private fun getLocationRU(){
        val ct = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, ct.token)
            .addOnCompleteListener{
                Log.d("MyLog1", "Time: ${it.result.latitude}")
                //requestWeatherData("${it.result.latitude},${it.result.longitude}")
                requestCity("${it.result.latitude}","${it.result.longitude}")
            }
    }
    @SuppressLint("ResourceType")
    private fun updateCurrentCard()= with(binding){
    model.liveDataCurrent.observe(viewLifecycleOwner){

       val MaxMinTemp = "${it.minTemp}°C/${it.maxTemp}°C"
            if(Locale.getDefault().getDisplayLanguage()=="русский") {
            location?.text = GlobalVariable.cityRu
                Log.d("MyLog6", "namecity: ${GlobalVariable.cityRu}")
        }
        else location?.text  =it.city
        data?.text =it.time
        tempNow?.text="${it.currentTemp}°C"
        MinMax?.text= MaxMinTemp
            wind?.text="${it.wind} ${getString(R.string.speed)}"
        humidity?.text="${it.humidity} %"
Picasso.get().load("https:"+it.img).into(imageView2)
    }

}
    private fun permissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            // Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
            if (it==false){
                DialogManager2.locationSettingsDialog(requireContext(), object : DialogManager2.Listener{
                    override fun onClick(i: Int) {
                        if (i==1)
                        {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:" + Application2.applicationContext().packageName)
                            startActivity(intent)
                        }

                        else
                        {
                           // pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            requestWeatherData("Moscow")
                            updateCurrentCard()
                        }
                    }
                })
            }
        }
    }

    private fun checkPermission(){
        when {
            isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
                ->{}
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            ->{

            }
                else ->{

                    pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }



    private fun requestCity(l: String, lt: String){
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$l&lon=$lt&appid=4c273337bfa872f4e7706321555cf689&lang=ru"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,            url,
            {                    result ->  parseCityData(result)
            },            {
                    error ->                Log.d("MyLog", "Error: $error")
            }        )
        queue.add(stringRequest)
    }
    private fun parseCityData(result: String) {
        val mainObject = JSONObject(result)
        val name =  mainObject.getString("name")       //  cityRu=name
        Log.d("MyLog", "namecity: $name")
        GlobalVariable.cityRu=name
        Log.d("MyLog3", "namecity: ${GlobalVariable.cityRu}")
    }

            private fun requestWeatherData(city: String){
        val url = "https://api.weatherapi.com/v1/forecast.json" +
                "?key=cfb3dcd30f164912a46165558233006&q=" +
                city +
                "&days=10&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    result ->  parseWeatherData(result)
            },
            {
                error ->
                Log.d("MyLog", "Error: $error")
            }
        )
        queue.add(stringRequest)
    }
    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
 parseCurrentData(mainObject, list [0])
    }
    private fun parseDays(mainObject: JSONObject): List<Day>{
        val list = ArrayList<Day>()
        val daysArray = mainObject.getJSONObject("forecast")
            .getJSONArray("forecastday")
        val name =  mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()){
            val day = daysArray[i] as JSONObject
            val item = Day(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("text"),
                day.getJSONObject("day").getJSONObject("condition")
                    .getString("icon"),
                "",
                day.getJSONObject("day").getString("maxtemp_c"),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getString("maxwind_kph"),
                day.getJSONObject("day").getString("avghumidity"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        model.liveDataList.value=list
        return list
    }
    private fun parseCurrentData(mainObject: JSONObject, weatherItem: Day){
        val item = Day(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current")
                .getJSONObject("condition").getString("icon"),
            mainObject.getJSONObject("current").getString("temp_c").toFloat().toInt().toString(),
            weatherItem.maxTemp.toFloat().toInt().toString(),
            weatherItem.minTemp.toFloat().toInt().toString(),
            mainObject.getJSONObject("current").getString("wind_kph").toFloat().toInt().toString(),
            mainObject.getJSONObject("current").getString("humidity").toFloat().toInt().toString(),
            weatherItem.hours
        )
        model.liveDataCurrent.value=item
        Log.d("MyLog", "City: ${item.maxTemp}")
        Log.d("MyLog", "Time: ${item.minTemp}")
        Log.d("MyLog", "Time: ${item.hours}")
    }
    /*companion object {
        @JvmStatic
       fun newInstance() = Main().onSaveInstanceState(Bundle())

    }*/
}