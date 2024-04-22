package com.abler31.airlineapp.di

import com.abler31.airlineapp.countrySelected.presentation.CountrySelectedViewModel
import com.abler31.airlineapp.tickets.presentation.TicketsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<TicketsViewModel>{
        TicketsViewModel(
            getOffers = get()
        )
    }

    viewModel<CountrySelectedViewModel>{
        CountrySelectedViewModel(
            getOffers = get()
        )
    }

}