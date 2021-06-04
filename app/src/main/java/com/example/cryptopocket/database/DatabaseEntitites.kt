package com.example.cryptopocket.database

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
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

@DatabaseView("""
        SELECT 
            tb1.base, 
            tb1.counter,
            tb1.buy_price,
            tb1.sell_price,
            tb1.icon,
            tb1.name,
            CASE WHEN tb2.base IS NULL THEN 0 ELSE 1 END AS added
        FROM currency_table as tb1 
        LEFT JOIN pocket_table as tb2 on tb1.base = tb2.base
        ORDER BY tb1.base
    """, viewName = "currency_view"
)
data class FullInfoCurrency(
    @PrimaryKey
    val base: String,

    val counter: String,

    @ColumnInfo(name = "buy_price")
    val buyPrice: Double,

    @ColumnInfo(name = "sell_price")
    val sellPrice: Double,

    val icon: String,

    val name: String,

    val added: Int
)

fun List<FullInfoCurrency>.asDomainModel(): List<Currency> {
    return map {
        Currency(
            base = it.base,
            counter = it.counter,
            buyPrice = it.buyPrice,
            sellPrice = it.sellPrice,
            icon = it.icon,
            name = it.name,
            added = it.added
        )
    }
}