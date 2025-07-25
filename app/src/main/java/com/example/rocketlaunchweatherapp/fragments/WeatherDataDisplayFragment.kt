package com.example.rocketlaunchweatherapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rocketlaunchweatherapp.R
import com.example.rocketlaunchweatherapp.WeatherHourlyDataAdapter
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
            val hourlyData = weatherResponse.data?.hourly?.data ?: emptyList()

            if (hourlyData.isNotEmpty()) {
                val isCurrentHourSafe = weatherDataViewModel.isCurrentWindsSafe()
                val iconDTO = weatherDataViewModel.isGoodLaunchDay()

                binding.dailyOverAllIconView.setImageResource(iconDTO.icon)
                binding.dailyOverAllIconView.setColorFilter(iconDTO.color.toArgb())

                if (isCurrentHourSafe) {
                    setCurrentHourIconSafe()
                } else {
                    setCurrentHourIconUnsafe()
                }

                binding.currentHourNotSafeIconView.visibility = View.VISIBLE
                binding.dailyOverAllIconView.visibility = View.VISIBLE
            }

            binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.horizontalRecyclerView.adapter = WeatherHourlyDataAdapter(hourlyData)

            binding.tempTextView.text = weatherResponse.data?.currently?.temperature?.toString() ?: ""
            binding.apparentTempTextView.text = weatherResponse.data?.currently?.apparentTemperature?.toString() ?: ""
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

    private fun setCurrentHourIconSafe() {
        binding.currentHourNotSafeIconView.setImageResource(R.drawable.ic_rocket_launch)
        binding.currentHourNotSafeIconView.setColorFilter(Color.Green.toArgb())
    }

    private fun setCurrentHourIconUnsafe() {
        binding.currentHourNotSafeIconView.setImageResource(R.drawable.ic_high_wind)
        binding.currentHourNotSafeIconView.setColorFilter(Color.Red.toArgb())
    }
}
