package com.example.rocketlaunchweatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketlaunchweatherapp.api.GeocodingResponse
import com.example.rocketlaunchweatherapp.api.GeocodingService
import com.example.rocketlaunchweatherapp.api.LatLon
import kotlinx.coroutines.launch


class GeocodingViewModel: ViewModel() {
    private val _geocodingState = MutableLiveData(GeocodingState())
    val geocodingState: LiveData<GeocodingState> = _geocodingState

    data class GeocodingState(
        val isLoading: Boolean = false,
        val data: GeocodingResponse? = null,
        val error: String? = null,
    )

     fun fetchCityStateData(city: String, state: String) {
        viewModelScope.launch {
            try {
                val response = GeocodingService.getCityCoordinates(city, 10)
                val filteredData = response.first { it.state.lowercase() == state.lowercase() }

                _geocodingState.postValue(GeocodingState(false, filteredData, null)) // Update LiveData
            } catch(e: Exception) {
                println("error => ${e.message}")

                _geocodingState.postValue(GeocodingState(false, null, e.message)) // Update LiveData
            }
        }
    }

    fun getLatLon(): LatLon? {
        return geocodingState.value?.data?.let { LatLon(it.lat, geocodingState.value?.data!!.lon) }
    }
}