package com.example.cryptopocket.api

import com.example.cryptopocket.domain.Currency
import com.squareup.moshi.Json

data class NetworkCurrency(
    val base: String,
    val counter: String,
    @Json(name="buy_price") val buyPrice: Double,
    @Json(name="sell_price")val sellPrice: Double,
    val icon: String,
    val name: String
)

fun List<NetworkCurrency>.asDomainModel(): List<Currency> {
    return map {
        Currency(
            base = it.base,
            counter = it.counter,
            buyPrice = it.buyPrice,
            sellPrice = it.sellPrice,
            icon = it.icon,
            name = it.name
        )
    }
}