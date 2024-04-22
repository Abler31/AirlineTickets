package com.abler31.airlineapp.countrySelected.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.countrySelected.domain.model.TicketsOffer
import com.abler31.airlineapp.countrySelected.domain.usecase.GetTicketsOffers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountrySelectedViewModel(private val getOffers: GetTicketsOffers
) : ViewModel() {

    private val _ticketsOffers = MutableLiveData<Resource<List<TicketsOffer>>>()
    val ticketsOffers: LiveData<Resource<List<TicketsOffer>>> = _ticketsOffers


    fun getTicketsOffers() = viewModelScope.launch(Dispatchers.IO) {
        _ticketsOffers.postValue(Resource.Loading())
        _ticketsOffers.postValue(getOffers.invoke())
    }
}