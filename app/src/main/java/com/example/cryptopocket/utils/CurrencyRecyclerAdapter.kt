package com.example.cryptopocket.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.ItemLayoutAddBinding
import com.example.cryptopocket.databinding.ItemLayoutRemoveBinding
import com.example.cryptopocket.domain.Currency

class CurrencyRecyclerAdapter(private val forAdding: Boolean, private val listener: Listener): ListAdapter<Currency, CurrencyViewHolder>(DiffCallBack){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.from(parent, forAdding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.bind(currency, listener)
    }
}

// Create 1 view holder for both similar layouts
class CurrencyViewHolder private constructor (private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup, forAdding: Boolean): CurrencyViewHolder {
            val binding = when(forAdding) {
                true -> DataBindingUtil.inflate<ItemLayoutAddBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_layout_add, parent,false)
                else -> DataBindingUtil.inflate<ItemLayoutRemoveBinding>(
                    LayoutInflater.from(parent.context), R.layout.item_layout_remove, parent,false)
            }
            return CurrencyViewHolder(binding)
        }
    }

    fun bind(currency: Currency, listener: Listener) {
        when (binding) {
            is ItemLayoutAddBinding -> {
                binding.currency = currency
                binding.addBtn.setOnClickListener{
                    listener.onClick(currency)
                }
                binding.executePendingBindings()
            }
            is ItemLayoutRemoveBinding -> {
                binding.currency = currency
                binding.removeBtn.setOnClickListener {
                    listener.onClick(currency)
                }
                binding.executePendingBindings()
            }
        }
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

class Listener(val listener: (currency: Currency) -> Unit) {
    fun onClick(currency: Currency) = listener(currency)
}