package com.example.quotes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
// This 'Unresolved reference' error for 'databinding' means the auto-generated
// ActivityFavoritesBinding class was not created during the build process.
// This is not an error in this file, but is caused by a configuration issue,
// typically in the build.gradle.kts file or an error in the associated XML layout.
import com.example.quotes.databinding.ActivityFavoritesBinding
import com.example.quotes.Quote
import com.example.quotes.QuoteAdapter
import com.example.quotes.FavoritesManager

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var quoteAdapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        quoteAdapter = QuoteAdapter(mutableListOf<Quote>())
        binding.favoritesRecyclerView.adapter = quoteAdapter
    }

    private fun loadFavorites() {
        val favoriteQuotes = FavoritesManager.getFavorites()
        quoteAdapter.updateQuotes(favoriteQuotes)

        if (favoriteQuotes.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
            binding.favoritesRecyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.favoritesRecyclerView.visibility = View.VISIBLE
        }
    }
}
