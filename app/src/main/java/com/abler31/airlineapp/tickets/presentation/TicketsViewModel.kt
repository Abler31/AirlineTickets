package com.abler31.airlineapp.tickets.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abler31.airlineapp.tickets.domain.Resource
import com.abler31.airlineapp.tickets.domain.model.Offer
import com.abler31.airlineapp.tickets.domain.usecase.GetOffers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketsViewModel(
    private val getOffers: GetOffers
) : ViewModel() {

    private val _offers = MutableLiveData<Resource<List<Offer>>>()
    val offers: LiveData<Resource<List<Offer>>> = _offers


    fun getOffers() = viewModelScope.launch(Dispatchers.IO) {
        _offers.postValue(Resource.Loading())
        _offers.postValue(getOffers.invoke())

    }
}