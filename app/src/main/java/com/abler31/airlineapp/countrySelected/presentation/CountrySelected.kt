package com.abler31.airlineapp.countrySelected.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.tickets.presentation.TicketsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CountrySelected : Fragment(R.layout.fragment_country_selected) {

    private val vm by viewModel<CountrySelectedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name1 = view.findViewById<TextView>(R.id.tv_country_selected_direct_flights1)
        val price1 = view.findViewById<TextView>(R.id.tv_country_selected_price_red)
        val time1 = view.findViewById<TextView>(R.id.tv_country_selected_time1)

        val name2 = view.findViewById<TextView>(R.id.tv_country_selected_direct_flights2)
        val price2 = view.findViewById<TextView>(R.id.tv_country_selected_price_blue)
        val time2 = view.findViewById<TextView>(R.id.tv_country_selected_time2)

        val name3 = view.findViewById<TextView>(R.id.tv_country_selected_direct_flights3)
        val price3 = view.findViewById<TextView>(R.id.tv_country_selected_price_white)
        val time3 = view.findViewById<TextView>(R.id.tv_country_selected_time3)


        vm.ticketsOffers.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Success -> {
                    name1.text = it.data!![0].title
                    price1.text = it.data[0].price.value.toString().replace("(\\d)(?=(\\d{3})+$)"
                        .toRegex(), "$1 ").plus(" ₽")
                    time1.text = it.data[0].time_range.joinToString(separator = " ")

                    name2.text = it.data!![1].title
                    price2.text = it.data[1].price.value.toString().replace("(\\d)(?=(\\d{3})+$)"
                        .toRegex(), "$1 ").plus(" ₽")
                    time2.text = it.data[1].time_range.joinToString(separator = " ")

                    name3.text = it.data!![2].title
                    price3.text = it.data[2].price.value.toString().replace("(\\d)(?=(\\d{3})+$)"
                        .toRegex(), "$1 ").plus(" ₽")
                    time3.text = it.data[2].time_range.joinToString(separator = " ")
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }
        vm.getTicketsOffers()

    }
}