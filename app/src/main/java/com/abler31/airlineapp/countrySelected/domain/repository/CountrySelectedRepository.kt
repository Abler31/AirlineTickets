package com.abler31.airlineapp.countrySelected.domain.repository

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.countrySelected.domain.model.TicketsOffer
import com.abler31.airlineapp.tickets.domain.model.Offer

interface CountrySelectedRepository {

    suspend fun getTicketsOffers(): Resource<List<TicketsOffer>>

}