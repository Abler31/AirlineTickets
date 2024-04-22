package com.abler31.airlineapp.countrySelected.domain.usecase

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.countrySelected.domain.model.TicketsOffer
import com.abler31.airlineapp.countrySelected.domain.repository.CountrySelectedRepository

class GetTicketsOffers(
    private val repository: CountrySelectedRepository
) {

    suspend operator fun invoke(): Resource<List<TicketsOffer>> {
        return repository.getTicketsOffers()
    }

}