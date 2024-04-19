package com.abler31.airlineapp.tickets.domain.usecase

import com.abler31.airlineapp.tickets.domain.Resource
import com.abler31.airlineapp.tickets.domain.model.Offer
import com.abler31.airlineapp.tickets.domain.repository.TicketsRepository

class GetOffers(
    private val repository: TicketsRepository
) {

    suspend operator fun invoke() : Resource<List<Offer>> {
        return repository.getOffers()
    }

}