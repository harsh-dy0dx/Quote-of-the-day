package com.example.quotes

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// A singleton object to manage storing and retrieving favorite quotes.
// SharedPreferences is used for persistence. Gson helps convert our list to a JSON string.
object FavoritesManager {

    private const val PREFS_NAME = "QuoteAppPrefs"
    private const val FAVORITES_KEY = "FavoriteQuotes"

    private lateinit var preferences: SharedPreferences
    private val gson = Gson()

    // Call this once from your Application class or MainActivity
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addFavorite(quote: Quote) {
        val favorites = getFavorites().toMutableList()
        if (!favorites.contains(quote)) {
            favorites.add(quote)
            saveFavorites(favorites)
        }
    }

    fun removeFavorite(quote: Quote) {
        val favorites = getFavorites().toMutableList()
        if (favorites.contains(quote)) {
            favorites.remove(quote)
            saveFavorites(favorites)
        }
    }

    fun isFavorite(quote: Quote): Boolean {
        return getFavorites().contains(quote)
    }

    fun getFavorites(): List<Quote> {
        val json = preferences.getString(FAVORITES_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<Quote>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun saveFavorites(favorites: List<Quote>) {
        val json = gson.toJson(favorites)
        preferences.edit().putString(FAVORITES_KEY, json).apply()
    }
}
