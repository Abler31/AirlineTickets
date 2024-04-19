package com.abler31.airlineapp.di

import com.abler31.airlineapp.tickets.data.repository.TicketsRepositoryImpl
import com.abler31.airlineapp.tickets.domain.repository.TicketsRepository
import org.koin.dsl.module

val dataModule  = module {

    single<TicketsRepository> { TicketsRepositoryImpl(get()) }

}