package com.example.quotes

import retrofit2.Response
import retrofit2.http.GET

// This interface defines how Retrofit talks to the web server.
interface QuoteApiService {
    // The @GET annotation tells Retrofit that this is a GET request.
    // We change the endpoint from "random" to "quotes" to get a batch of 50.
    @GET("quotes")
    suspend fun getQuotesBatch(): Response<List<Quote>>
}
