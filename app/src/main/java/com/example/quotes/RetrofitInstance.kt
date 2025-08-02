package com.example.quotes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// This object creates and configures a Retrofit instance.
object RetrofitInstance {

    private const val BASE_URL = "https://zenquotes.io/api/"

    // lazy initialization means the Retrofit instance is created only when it's first needed.
    val api: QuoteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson to parse JSON
            .build()
            .create(QuoteApiService::class.java)
    }
}
