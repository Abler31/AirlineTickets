package com.abler31.airlineapp.tickets.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.tickets.domain.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val vm by viewModel<TicketsViewModel>()
    private lateinit var ticketsRecyclerAdapter: TicketsRecyclerAdapter
    lateinit var ticketsRecyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ticketsRecyclerAdapter = TicketsRecyclerAdapter()
        ticketsRecyclerView = view.findViewById(R.id.rv_tickets)
        ticketsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        ticketsRecyclerView.adapter = ticketsRecyclerAdapter
        vm.offers.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Success -> {
                    ticketsRecyclerAdapter.setData(it.data!!)
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }
        vm.getOffers()
    }

}