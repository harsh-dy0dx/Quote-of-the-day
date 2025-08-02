package com.example.quotes

// The repository now handles caching to avoid hitting API rate limits.
object QuoteRepository {

    private val quoteCache = mutableListOf<Quote>()

    // This function gets a random quote, managing the cache internally.
    suspend fun getRandomQuote(): Quote? {
        // If the cache is empty, fetch a new batch of quotes.
        if (quoteCache.isEmpty()) {
            val success = fetchAndCacheQuotes()
            if (!success) {
                return null // Return null if the fetch failed.
            }
        }

        // If the cache is still empty after a fetch attempt, something went wrong.
        if (quoteCache.isEmpty()) return null

        // Return a random quote from the cache and remove it to avoid showing it again soon.
        return quoteCache.removeAt(quoteCache.indices.random())
    }

    // This private function handles the actual API call to get the batch.
    private suspend fun fetchAndCacheQuotes(): Boolean {
        return try {
            val response = RetrofitInstance.api.getQuotesBatch()
            if (response.isSuccessful && response.body() != null) {
                // Clear the old cache and add the new quotes.
                quoteCache.clear()
                quoteCache.addAll(response.body()!!)
                true // Return true on success
            } else {
                false // API call was not successful
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false // Network error or other exception
        }
    }
}
