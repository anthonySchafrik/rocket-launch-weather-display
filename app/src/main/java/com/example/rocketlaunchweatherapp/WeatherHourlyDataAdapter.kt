package com.example.rocketlaunchweatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.rocketlaunchweatherapp.api.Daum2

class WeatherHourlyDataAdapter(private val items: List<Daum2>) : RecyclerView.Adapter<WeatherHourlyDataAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hourlySummaryTextView: TextView = view.findViewById(R.id.hourly_summary_text_view)
        val hourlyTextView: TextView = view.findViewById(R.id.hour_text_view)
        val tempTextView: TextView = view.findViewById(R.id.temp_text_view)
        val windSpeedTextView: TextView = view.findViewById(R.id.wind_speed_text_view)
        val windGustTextView: TextView = view.findViewById(R.id.wind_gust_text_view)
        val windBearingArrow: ImageView = view.findViewById(R.id.wind_bearing_arrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hourly_weather_data_display, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val bearing = item.windBearing ?: 0.0

        holder.hourlySummaryTextView.text = item.summary
        holder.hourlyTextView.text = convertTimestamp(items[position].time)
        holder.tempTextView.text = items[position].temperature.toString()
        holder.windSpeedTextView.text = items[position].windSpeed.toString()
        holder.windGustTextView.text = items[position].windGust.toString()
        holder.windBearingArrow.rotation = ((bearing + 180) % 360).toFloat()

        val defaultColor = getThemeTextColor(holder.windSpeedTextView)

        holder.windSpeedTextView.setTextColor(defaultColor)
        holder.windGustTextView.setTextColor(defaultColor)

        if (items[position].windSpeed > SAFE_LAUNCH_WIND_SPEED) {
            holder.windSpeedTextView.setTextColor(Color.RED)
        }

        if (items[position].windGust > SAFE_LAUNCH_WIND_SPEED) {
            holder.windGustTextView.setTextColor(Color.RED)
        }
    }

    override fun getItemCount() = items.size
}