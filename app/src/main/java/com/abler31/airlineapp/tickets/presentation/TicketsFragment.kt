package com.abler31.airlineapp.tickets.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.tickets.domain.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val vm by viewModel<TicketsViewModel>()
    private lateinit var ticketsRecyclerAdapter: TicketsRecyclerAdapter
    lateinit var ticketsRecyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchCardView = view.findViewById<CardView>(R.id.cv_search_view)
        val tvFrom = view.findViewById<TextView>(R.id.et_search_from)
        val tvTo = view.findViewById<TextView>(R.id.et_search_to)
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

        tvFrom.setOnClickListener {

            showBottomDialog()

        }

        tvTo.setOnClickListener {

            showBottomDialog()

        }

    }

    private fun showBottomDialog(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_search)

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)


    }

}