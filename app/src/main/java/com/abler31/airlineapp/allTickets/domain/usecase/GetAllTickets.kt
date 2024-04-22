package com.abler31.airlineapp.allTickets.domain.usecase

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.Ticket
import com.abler31.airlineapp.allTickets.domain.repository.AllTicketsRepository
import com.abler31.airlineapp.tickets.domain.model.Offer

class GetAllTickets(
    private val repository: AllTicketsRepository
) {

    suspend operator fun invoke(): Resource<List<Ticket>> {
        return repository.getAllTickets()
    }

}