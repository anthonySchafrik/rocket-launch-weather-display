package com.example.rocketlaunchweatherapp.api

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val offset: Long,
    val elevation: Long,
    val currently: Currently,
    val minutely: Minutely,
    val hourly: Hourly,
    val daily: Daily,
    val alerts: List<Any?>,
    val flags: Flags,
)

data class Currently(
    val time: Long,
    val summary: String,
    val icon: String,
    val nearestStormDistance: Double,  // Changed from Long to Double
    val nearestStormBearing: Long,
    val precipIntensity: Double,
    val precipProbability: Double,
    val precipIntensityError: Double?,
    val precipType: String?,
    val temperature: Double,
    val apparentTemperature: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windBearing: Double,
    val cloudCover: Double,
    val uvIndex: Double,
    val visibility: Double,
    val ozone: Double
)

data class Minutely(
    val summary: String,
    val icon: String,
    val data: List<Daum>,
)

data class Daum(
    val time: Long,
    val precipIntensity: Double,
    val precipProbability: Double,
    val precipIntensityError: Double,
    val precipType: String,
)

data class Hourly(
    val summary: String,
    val icon: String,
    val data: List<Daum2>,
)

data class Daum2(
    val time: Long,
    val summary: String,
    val icon: String,
    val precipIntensity: Double,
    val precipProbability: Double,
    val precipIntensityError: Double?,  // Nullable
    val precipAccumulation: Double?,  // Nullable
    val precipType: String?,  // Nullable
    val temperature: Double,
    val apparentTemperature: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windBearing: Double,  // Changed from Long to Double
    val cloudCover: Double,
    val uvIndex: Double,
    val visibility: Double,
    val ozone: Double
)

data class Daily(
    val summary: String,
    val icon: String,
    val data: List<Daum3>,
)

data class Daum3(
    val time: Long,
    val summary: String,
    val icon: String,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val moonPhase: Double,
    val precipIntensity: Double,
    val precipIntensityMax: Double,
    val precipIntensityMaxTime: Long,
    val precipProbability: Double,
    val precipAccumulation: Double,
    val precipType: String,
    val temperatureHigh: Double,
    val temperatureHighTime: Long,
    val temperatureLow: Double,
    val temperatureLowTime: Long,
    val apparentTemperatureHigh: Double,
    val apparentTemperatureHighTime: Long,
    val apparentTemperatureLow: Double,
    val apparentTemperatureLowTime: Long,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windGustTime: Long,
    val windBearing: Double,
    val cloudCover: Double,
    val uvIndex: Double,
    val uvIndexTime: Long,
    val visibility: Double,
    val temperatureMin: Double,
    val temperatureMinTime: Long,
    val temperatureMax: Double,
    val temperatureMaxTime: Long,
    val apparentTemperatureMin: Double,
    val apparentTemperatureMinTime: Long,
    val apparentTemperatureMax: Double,
    val apparentTemperatureMaxTime: Long,
)

data class Flags(
    val sources: List<String>,
    val sourceTimes: SourceTimes,
    @JsonProperty("nearest-station")
    val nearestStation: Long,
    val units: String,
    val version: String,
)

data class SourceTimes(
    @JsonProperty("hrrr_subh")
    val hrrrSubh: String,
    @JsonProperty("hrrr_0-18")
    val hrrr018: String,
    val nbm: String,
    @JsonProperty("nbm_fire")
    val nbmFire: String,
    @JsonProperty("hrrr_18-48")
    val hrrr1848: String,
    val gfs: String,
    val gefs: String,
)

data class Alert(
    val title: String,
    val regions: List<String>,
    val severity: String,
    val time: Long,
    val expires: Long,
    val description: String,
    val uri: String
)

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val offset: Double,  // Changed from Long to Double
    val elevation: Long,
    val currently: Currently,
    val minutely: Minutely?,
    val hourly: Hourly?,
    val daily: Daily?,
    val alerts: List<Alert>?,  // Changed from Any? to Alert
    val flags: Flags
)

data class LatLon(
    val lat: Double,
    val lon: Double,
)

private const val BASE_URL = "https://api.pirateweather.net/forecast/EL2CpZtUx4LELVkR86Z8mZOsAKa6qGEw/"

interface WeatherApiService {
    @GET("{lat},{lon}")
    suspend fun getWeatherData(
        @Path("lat") lat: String,
        @Path("lon") lon: String
    ): WeatherResponse
}

var retrofitWeatherService: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

var WeatherService: WeatherApiService = retrofitWeatherService.create(WeatherApiService::class.java)
