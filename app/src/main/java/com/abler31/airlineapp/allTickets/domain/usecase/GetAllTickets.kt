package com.abler31.airlineapp.allTickets.domain.usecase

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.TicketBadged
import com.abler31.airlineapp.allTickets.domain.repository.AllTicketsRepository

class GetAllTickets(
    private val repository: AllTicketsRepository
) {

    suspend operator fun invoke(): Resource<List<TicketBadged>> {
        return repository.getAllTickets()
    }

}