package com.example.rocketlaunchweatherapp.api

import com.example.rocketlaunchweatherapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"

interface GeocodingApiService {
    @GET("geo/1.0/direct")
    suspend fun getCityCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String = BuildConfig.GEO_API_KEY
    ): List<GeocodingResponse>
}

data class GeocodingResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)

var retrofitGeocodingService: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

var GeocodingService: GeocodingApiService = retrofitGeocodingService.create(GeocodingApiService::class.java)
