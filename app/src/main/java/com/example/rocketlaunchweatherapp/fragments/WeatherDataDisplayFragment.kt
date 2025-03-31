package com.example.rocketlaunchweatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rocketlaunchweatherapp.databinding.WeatherDataDisplayBinding
import com.example.rocketlaunchweatherapp.viewmodels.WeatherViewModel


class WeatherDataDisplayFragment: Fragment() {
    private var _binding: WeatherDataDisplayBinding? = null
    private val binding get() = _binding!!

    private val weatherDataViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherDataDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        weatherDataViewModel.weatherState.observe(viewLifecycleOwner) { weatherResponse ->
            binding.tempTextView.text = weatherResponse.data?.currently?.temperature?.toString() ?: ""
            binding.apparentTempTextView.text = weatherResponse.data?.currently?.temperature?.toString() ?: ""
            binding.windSpeedTextView.text = weatherResponse.data?.currently?.windSpeed?.toString() ?: ""
            binding.windGustTextView.text = weatherResponse.data?.currently?.windGust?.toString() ?: ""
            binding.windBearingTextView.text =  weatherResponse.data?.currently?.windBearing?.toString() ?: ""
            binding.dailySummaryTextView.text = weatherResponse.data?.currently?.summary ?: ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}