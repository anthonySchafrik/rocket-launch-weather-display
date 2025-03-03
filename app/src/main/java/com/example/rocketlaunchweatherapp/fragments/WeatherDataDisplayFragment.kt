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

            if ( weatherResponse.data?.currently?.temperature != null) {
//                binding.tempTextViewText.visibility = View.VISIBLE
//                binding.apparentTempTextViewText.visibility = View.VISIBLE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}