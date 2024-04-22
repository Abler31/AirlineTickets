package com.abler31.airlineapp.countrySelected.domain.model

data class TicketsOffer(
    val id: Int,
    val price: Price,
    val time_range: List<String>,
    val title: String
)