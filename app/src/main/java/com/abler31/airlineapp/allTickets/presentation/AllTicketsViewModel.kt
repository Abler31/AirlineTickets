package com.abler31.airlineapp.allTickets.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.Ticket
import com.abler31.airlineapp.allTickets.domain.usecase.GetAllTickets
import com.abler31.airlineapp.tickets.domain.model.Offer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllTicketsViewModel(
    private val getTickets: GetAllTickets
) : ViewModel() {

    private val _tickets = MutableLiveData<Resource<List<Ticket>>>()
    val tickets: LiveData<Resource<List<Ticket>>> = _tickets


    fun getTickets() = viewModelScope.launch(Dispatchers.IO) {
        _tickets.postValue(Resource.Loading())
        _tickets.postValue(getTickets.invoke())

    }
}