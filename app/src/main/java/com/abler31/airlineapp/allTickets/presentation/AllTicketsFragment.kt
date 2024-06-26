package com.abler31.airlineapp.allTickets.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.DisplayableItem
import com.abler31.airlineapp.allTickets.domain.model.TicketBadged
import com.abler31.airlineapp.allTickets.domain.model.Ticket
import com.abler31.airlineapp.countrySelected.presentation.CountrySelectedArgs
import com.abler31.airlineapp.tickets.presentation.TicketsFragmentDirections
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllTicketsFragment : Fragment(R.layout.fragment_all_tickets) {
    private val vm by viewModel<AllTicketsViewModel>()
    lateinit var rv: RecyclerView

    val adapter = ListDelegationAdapter<List<DisplayableItem>>(
        AllTicketsFragmentDelegates.ticketDelegates(),
        AllTicketsFragmentDelegates.ticketBadgedDelegates()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val args = AllTicketsFragmentArgs.fromBundle(bundle!!)
        val arrowBack = view.findViewById<ImageView>(R.id.iv_arrow_back_all_tickets)
        val fromTo = view.findViewById<TextView>(R.id.tv_all_tickets_from_to)
        val date = view.findViewById<TextView>(R.id.tv_all_tickets_date)

        fromTo.text = "${args.from}-${args.to}"
        date.text = "${args.date}, 1 пассажир"

        rv = view.findViewById(R.id.rv_all_tickets)
        rv.layoutManager = LinearLayoutManager(requireContext())

        vm.tickets.observe(viewLifecycleOwner){

            when (it) {
                is Resource.Success -> {
                    val data = it.data
                    setData(data!!)
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }

        }
        vm.getTickets()
        rv.adapter = adapter

        arrowBack.setOnClickListener {
            val direction = AllTicketsFragmentDirections.allTicketsFragmentToCountrySelected("", "")
            findNavController().navigate(direction)
        }
    }

    private fun setData(data: List<TicketBadged>){
        val newList = mutableListOf<DisplayableItem>()
        data.forEachIndexed { index, item ->
            if (item.badge == null) {
                newList.add(Ticket(
                    item.arrival,
                    item.company,
                    item.departure,
                    item.hand_luggage,
                    item.has_transfer,
                    item.has_visa_transfer,
                    item.id,
                    item.is_exchangable,
                    item.is_returnable,
                    item.luggage,
                    item.price,
                    item.provider_name
                ))
            } else {
                newList.add(item)
            }
        }
        adapter.items = newList
        adapter.notifyDataSetChanged()
    }

}