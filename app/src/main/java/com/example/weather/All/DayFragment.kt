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
import com.example.weather.adapters.WeatherAdapter2
import com.example.weather.databinding.FragmentDayBinding
import com.example.weather.databinding.FragmentHourBinding
import org.json.JSONArray
import org.json.JSONObject


class DayFragment : Fragment() {
    private lateinit var  binding: FragmentDayBinding
    private  lateinit var adapter: WeatherAdapter2
    private val model: MainModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.liveDataList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }
    private fun initRcView()= with(binding){
        rcView.layoutManager=LinearLayoutManager(activity)
        adapter = WeatherAdapter2()
        rcView.adapter=adapter

    }

    companion object {
        @JvmStatic
        fun newInstance() = DayFragment()
    }
}