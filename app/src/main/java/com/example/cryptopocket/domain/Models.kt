package com.example.cryptopocket.domain

data class Currency(
    val base: String,
    val counter: String,
    val buyPrice: Double,
    val sellPrice: Double,
    val icon: String,
    val name: String,
    val added: Int
)