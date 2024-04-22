package com.abler31.airlineapp.allTickets.domain.repository

import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.Ticket
import com.abler31.airlineapp.tickets.domain.model.Offer

interface AllTicketsRepository{

    suspend fun getAllTickets(): Resource<List<Ticket>>

}