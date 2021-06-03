package com.example.cryptopocket.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptopocket.R
import com.example.cryptopocket.domain.Currency
import com.squareup.picasso.Picasso

@BindingAdapter("dataList")
fun bindRecyclerviewAdapter(recyclerView: RecyclerView, currencyList: List<Currency>) {
    currencyList.apply {
        val adapter = recyclerView.adapter as CurrencyRecyclerAdapter
        adapter.submitList(currencyList)
    }
}

@BindingAdapter("currencyImage")
fun bindCurrencyImageView(imageView: ImageView, imgUrl: String) {
    Picasso.get().load(imgUrl)
        .error(R.drawable.ic_placeholder_coin)
        .placeholder(R.drawable.ic_placeholder_coin)
        .into(imageView)
}