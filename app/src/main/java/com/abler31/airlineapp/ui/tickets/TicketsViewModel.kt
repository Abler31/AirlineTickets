package com.abler31.airlineapp.ui.tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicketsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {

    }
    val text: LiveData<String> = _text
}