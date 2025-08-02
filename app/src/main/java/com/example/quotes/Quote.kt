package com.example.quotes

import com.google.gson.annotations.SerializedName

// The @SerializedName annotation maps the JSON keys from the API response
// to our data class properties.
data class Quote(
    @SerializedName("q")
    val text: String,

    @SerializedName("a")
    val author: String
)
