package com.abler31.airlineapp.allTickets.domain.repository

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.TicketBadged

interface AllTicketsRepository{

    suspend fun getAllTickets(): Resource<List<TicketBadged>>

}