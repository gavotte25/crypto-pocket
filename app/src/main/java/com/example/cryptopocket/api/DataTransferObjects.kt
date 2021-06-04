package com.example.cryptopocket.api

import com.example.cryptopocket.database.DatabaseCurrency
import com.example.cryptopocket.domain.Currency
import com.squareup.moshi.Json
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

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

fun List<NetworkCurrency>.asDatabaseModel(): List<DatabaseCurrency> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    val now = Calendar.getInstance().time
    val dateString = dateFormat.format(now)
    return map {
        DatabaseCurrency(
            base = it.base,
            counter = it.counter,
            buyPrice = it.buyPrice,
            sellPrice = it.sellPrice,
            icon = it.icon,
            name = it.name,
            updateDate = dateString
        )
    }
}

fun parseCurrencyJsonResult(jsonResult: JSONObject):List<NetworkCurrency> {
    val data = jsonResult.getJSONArray("data")
    val currencyArray = mutableListOf<NetworkCurrency>()
    for (i in 0 until data.length()) {
        val currencyJsonObject = data.getJSONObject(i)
        val currency = NetworkCurrency(
            base = currencyJsonObject.getString("base"),
            counter = currencyJsonObject.getString("counter"),
            buyPrice = currencyJsonObject.getString("buy_price").toDouble(),
            sellPrice = currencyJsonObject.getString("sell_price").toDouble(),
            icon = currencyJsonObject.getString("icon"),
            name = currencyJsonObject.getString("name"),
        )
        currencyArray.add(currency)
    }
    return currencyArray.toList()
}