package com.example.cryptopocket.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.ItemLayoutAddBinding
import com.example.cryptopocket.domain.Currency

class CurrencyRecyclerAdapter: ListAdapter<Currency, CurrencyViewHolder>(DiffCallBack){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.bind(currency)
    }
}

class CurrencyViewHolder private constructor (private val binding: ItemLayoutAddBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): CurrencyViewHolder {
            val binding: ItemLayoutAddBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_layout_add,
                parent,
                false)
            return CurrencyViewHolder(binding)
        }
    }

    fun bind(currency: Currency) {
        binding.currency = currency
    }
}

object DiffCallBack: DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.base == newItem.base
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
}