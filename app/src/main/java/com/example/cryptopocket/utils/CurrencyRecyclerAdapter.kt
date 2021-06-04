package com.example.cryptopocket.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopocket.R
import com.example.cryptopocket.databinding.ItemLayoutBinding
import com.example.cryptopocket.domain.Currency

class CurrencyRecyclerAdapter(private val forSearchScreen: Boolean, private val listener: Listener): ListAdapter<Currency, CurrencyViewHolder>(DiffCallBack){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        holder.bind(currency, forSearchScreen, listener)
    }
}

// Create 1 view holder for 3 scenarios
class CurrencyViewHolder private constructor (private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): CurrencyViewHolder {
            val binding: ItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_layout, parent, false)
            return CurrencyViewHolder(binding)
        }
    }

    fun bind(currency: Currency, forSearchScreen: Boolean, listener: Listener) {
        binding.currency = currency

        if (forSearchScreen) {
            binding.removeBtn.visibility = View.INVISIBLE
            binding.addBtn.setOnClickListener {
                listener.onClick(currency)
            }
            binding.removeBtn.setOnClickListener{}
            binding.addedBtn.setOnClickListener{}
            if (currency.added == 0) {
                binding.addBtn.visibility = View.VISIBLE
                binding.addedBtn.visibility = View.INVISIBLE
            }
            else {
                binding.addBtn.visibility = View.INVISIBLE
                binding.addedBtn.visibility = View.VISIBLE
            }
        }
        else {
            binding.removeBtn.visibility = View.VISIBLE
            binding.addBtn.visibility = View.INVISIBLE
            binding.addedBtn.visibility = View.INVISIBLE
            binding.removeBtn.setOnClickListener {
                listener.onClick(currency)
            }
            binding.addedBtn.setOnClickListener{}
            binding.addBtn.setOnClickListener{}
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