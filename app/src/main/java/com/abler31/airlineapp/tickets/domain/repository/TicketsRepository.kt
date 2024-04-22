package com.abler31.airlineapp.tickets.domain.repository

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.tickets.domain.model.Offer

interface TicketsRepository {

    suspend fun getOffers(): Resource<List<Offer>>

}