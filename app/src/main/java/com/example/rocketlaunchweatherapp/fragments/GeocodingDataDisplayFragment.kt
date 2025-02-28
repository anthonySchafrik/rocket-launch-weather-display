package com.example.rocketlaunchweatherapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.rocketlaunchweatherapp.databinding.GeocodingDataDisplayBinding
import com.example.rocketlaunchweatherapp.toEditable
import com.example.rocketlaunchweatherapp.viewmodels.GeocodingViewModel
import com.example.rocketlaunchweatherapp.viewmodels.WeatherViewModel

class GeocodingDataDisplayFragment: Fragment() {
    private var _binding: GeocodingDataDisplayBinding? = null
    private val binding get() = _binding!!
    private var city: String = ""
    private var state: String = ""
    private val weatherDataViewModel: WeatherViewModel by activityViewModels()  // Initialize ViewModel
    private val geocodingViewModel: GeocodingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GeocodingDataDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle Listener
        binding.selectedCityInput.addTextChangedListener { handleSelectCityInput(it) }
        binding.selectedStateInput.addTextChangedListener { handleSelectStateInput(it) }
        binding.selectedCityButton.setOnClickListener { handleCitySearch() }

        // Observers
        geocodingViewModel.geocodingState.observe(viewLifecycleOwner) { geocodingResponse ->
            binding.latTextView.text = geocodingResponse.data?.lat?.toString() ?: "Latitude"
            binding.lonTextView.text = geocodingResponse.data?.lon?.toString() ?: "Longitude"

            binding.selectedCityTextView.text = geocodingResponse.data?.name?.toEditable()
            binding.selectedStateTextView.text = geocodingResponse.data?.state?.toEditable()

            println("geocodingViewModel observe ${geocodingResponse.data?.lat} ${geocodingResponse.data?.lon}")
            if (geocodingResponse.data?.lat != null && geocodingResponse.data?.lon != null) {
                weatherDataViewModel.fetchWeather(
                    geocodingResponse.data.lat.toString(),
                    geocodingResponse.data.lon.toString()
                )
            }
        }

        weatherDataViewModel.weatherState.observe(viewLifecycleOwner) { weatherResponse ->
            binding.tempTextView.text = weatherResponse.data?.currently?.temperature?.toString() ?: "Temperature"
            binding.apparentTempTextView.text = weatherResponse.data?.currently?.temperature?.toString() ?: "Apparent Temperature"

            if ( weatherResponse.data?.currently?.temperature != null) {
                binding.tempTextViewText.visibility = View.VISIBLE
                binding.apparentTempTextViewText.visibility = View.VISIBLE
            }

        }

        // when you click away from the text field it clearFocus
        binding.root.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                binding.selectedCityInput.clearFocus()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.selectedCityInput.windowToken, 0)

                binding.selectedStateInput.clearFocus()
                val immState = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                immState.hideSoftInputFromWindow(binding.selectedStateInput.windowToken, 0)
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleSelectCityInput(it: Editable?) {
        city = it.toString()
    }

    private fun handleSelectStateInput(it: Editable?) {
        state = it.toString()
    }

    private fun handleClearSearchField() {
        binding.selectedCityInput.clearFocus()
        binding.selectedCityInput.text.clear()

        binding.selectedStateInput.clearFocus()
        binding.selectedStateInput.text.clear()
    }

    private fun handleDisplaySelectedArea() {
        binding.selectedCityTextView.text = city
        binding.selectedStateTextView.text = state
    }


    @SuppressLint("SetTextI18n")
    private fun handleCitySearch() {
//        geocodingViewModel.fetchCityStateData(city, state)
        geocodingViewModel.fetchCityStateData("bellevue", "nebraska")

        handleDisplaySelectedArea()
        handleClearSearchField()
    }
}
