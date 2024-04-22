package com.abler31.airlineapp.di

import com.abler31.airlineapp.countrySelected.domain.usecase.GetTicketsOffers
import com.abler31.airlineapp.tickets.domain.usecase.GetOffers
import org.koin.dsl.module

val domainModule = module {

    factory<GetOffers> {
        GetOffers(repository = get())
    }

    factory<GetTicketsOffers> {
        GetTicketsOffers(repository = get())
    }

}