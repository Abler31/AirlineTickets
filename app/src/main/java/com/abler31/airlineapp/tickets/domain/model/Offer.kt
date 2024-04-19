package com.abler31.airlineapp.tickets.domain.model

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Price
)