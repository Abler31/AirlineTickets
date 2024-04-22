package com.abler31.airlineapp.allTickets.domain.model

import java.util.Date

data class Arrival(
    val airport: String,
    val date: Date,
    val town: String
)