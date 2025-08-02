package com.example.quotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.quotes.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentQuote: Quote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FavoritesManager.init(this)
        setupClickListeners()
        fetchNewQuote()
    }

    private fun fetchNewQuote() {

        lifecycleScope.launch {

            binding.progressBar.visibility = View.VISIBLE
            binding.quoteCard.visibility = View.GONE
            binding.actionsLayout.visibility = View.GONE

            val quote = QuoteRepository.getRandomQuote()


            binding.progressBar.visibility = View.GONE

            if (quote != null) {
                currentQuote = quote
                binding.quoteTextView.text = "“${quote.text}”"
                binding.authorTextView.text = "- ${quote.author}"
                updateFavoriteIcon()

                binding.quoteCard.visibility = View.VISIBLE
                binding.actionsLayout.visibility = View.VISIBLE
            } else {

                Snackbar.make(binding.root, "Failed to fetch quote. Please try again.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY") { fetchNewQuote() }
                    .show()
            }
        }
    }

    private fun setupClickListeners() {

        binding.quoteCard.setOnClickListener {
            fetchNewQuote()
        }

        binding.favoriteButton.setOnClickListener {
            currentQuote?.let { toggleFavoriteStatus(it) }
        }

        binding.shareButton.setOnClickListener {
            currentQuote?.let { shareQuote(it) }
        }

        binding.viewFavoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toggleFavoriteStatus(quote: Quote) {
        if (FavoritesManager.isFavorite(quote)) {
            FavoritesManager.removeFavorite(quote)
            Snackbar.make(binding.root, R.string.removed_from_favorites, Snackbar.LENGTH_SHORT).show()
        } else {
            FavoritesManager.addFavorite(quote)
            Snackbar.make(binding.root, R.string.added_to_favorites, Snackbar.LENGTH_SHORT).show()
        }
        updateFavoriteIcon()
    }

    private fun updateFavoriteIcon() {
        currentQuote?.let {
            if (FavoritesManager.isFavorite(it)) {
                binding.favoriteButton.setIconResource(R.drawable.ic_favorite)
            } else {
                binding.favoriteButton.setIconResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun shareQuote(quote: Quote) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val quoteText = "“${quote.text}” - ${quote.author}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, quoteText)
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_quote_via)))
    }
}
