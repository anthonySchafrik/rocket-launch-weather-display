package com.example.rocketlaunchweatherapp.api

import com.google.gson.GsonBuilder

val gson = GsonBuilder()
    .serializeNulls()  // Ensures nullable fields are handled
    .create()

//var retrofit = Retrofit.Builder()
//    .baseUrl(BASE_URL) // not sure how to make url not be hard coded
//    .addConverterFactory(GsonConverterFactory.create(gson)) // Use modified Gson
//    .build()
