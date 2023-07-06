package com.example.weather.All

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.Day
import com.example.weather.MainModel
import com.example.weather.R
import com.example.weather.adapters.WeatherAdapter
import com.example.weather.databinding.FragmentHourBinding
import org.json.JSONArray
import org.json.JSONObject


class HourFragment : Fragment() {
    private lateinit var  binding: FragmentHourBinding
    private  lateinit var adapter: WeatherAdapter
    private val model: MainModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHourBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataCurrent.observe(viewLifecycleOwner){
            adapter.submitList(getHoursList(it))
        }

    }
    private fun initRcView()= with(binding){
        rcView.layoutManager=LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = WeatherAdapter()
        rcView.adapter=adapter

    }
    private fun getHoursList(wItem: Day): List<Day>{
        val hoursArray = JSONArray(wItem.hours)
        val list = ArrayList<Day>()
        for(i in 0 until hoursArray.length()){
            val item = Day(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
"",
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("icon"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "",
                "",
                "",
                "",
                ""
            )
            list.add(item)
        }
        return list
    }
    companion object {
        @JvmStatic
        fun newInstance() = HourFragment()
    }
}