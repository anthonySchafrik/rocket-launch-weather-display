package com.example.rocketlaunchweatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rocketlaunchweatherapp.api.Daum2

class WeatherHourlyDataAdapter(private val items: List<Daum2>) : RecyclerView.Adapter<WeatherHourlyDataAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hourlySummeryTextView: TextView = view.findViewById(R.id.hourly_summery_text_view)
        val hourlyTextView: TextView = view.findViewById(R.id.hour_text_view)
        val tempTextView: TextView = view.findViewById(R.id.temp_text_view)
        val apparentTempTextView: TextView = view.findViewById(R.id.apparent_temp_text_view)
        val windSpeedTextView: TextView = view.findViewById(R.id.wind_speed_text_view)
        val windGustTextView: TextView = view.findViewById(R.id.wind_gust_text_view)
        val windBearingTextView: TextView = view.findViewById(R.id.wind_Bearing_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_weather_data_display, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.hourlySummeryTextView.text = item.summary
        holder.hourlyTextView.text = convertTimestamp(items[position].time)
        holder.tempTextView.text = items[position].temperature.toString()
        holder.apparentTempTextView.text = items[position].apparentTemperature.toString()
        holder.windSpeedTextView.text = items[position].windSpeed.toString()
        holder.windGustTextView.text = items[position].windGust.toString()
        holder.windBearingTextView.text = items[position].windBearing.toString()
    }

    override fun getItemCount() = items.size
}