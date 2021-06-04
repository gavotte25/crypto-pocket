package com.example.cryptopocket.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptopocket.domain.Currency

@Entity(tableName = "currency_table")
data class DatabaseCurrency(
    @PrimaryKey
    val base: String,

    val counter: String,

    @ColumnInfo(name = "buy_price")
    val buyPrice: Double,

    @ColumnInfo(name = "sell_price")
    val sellPrice: Double,

    val icon: String,

    val name: String,

    @ColumnInfo(name = "update_date")
    val updateDate: String
)

@Entity(tableName = "pocket_table")
data class PocketRecord(
    @PrimaryKey
    val base: String
)

fun List<DatabaseCurrency>.asDomainModel(): List<Currency> {
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