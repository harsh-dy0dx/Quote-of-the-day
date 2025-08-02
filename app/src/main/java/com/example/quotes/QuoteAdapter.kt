package com.example.quotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.databinding.ItemQuoteBinding

class QuoteAdapter(private var quotes: MutableList<Quote>) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(quotes[position])
    }

    override fun getItemCount(): Int = quotes.size

    fun updateQuotes(newQuotes: List<Quote>) {
        quotes.clear()
        quotes.addAll(newQuotes)
        notifyDataSetChanged()
    }

    inner class QuoteViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quote: Quote) {
            binding.itemQuoteTextView.text = "“${quote.text}”"
            binding.itemAuthorTextView.text = "- ${quote.author}"
        }
    }
}
