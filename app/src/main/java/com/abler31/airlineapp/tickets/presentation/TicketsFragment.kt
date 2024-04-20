package com.abler31.airlineapp.tickets.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.tickets.domain.Resource
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val vm by viewModel<TicketsViewModel>()
    private lateinit var ticketsRecyclerAdapter: TicketsRecyclerAdapter
    lateinit var ticketsRecyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val searchTo = dialog.findViewById<EditText>(R.id.et_search_to_dialog)

        val difficultRoute = dialog.findViewById<LinearLayout>(R.id.ll_difficult_route)
        val anywhere = dialog.findViewById<LinearLayout>(R.id.ll_anywhere)
        val weekends = dialog.findViewById<LinearLayout>(R.id.ll_weekend)
        val hotWeekdays = dialog.findViewById<LinearLayout>(R.id.ll_hot_weekdays)
        val istanbul = dialog.findViewById<ConstraintLayout>(R.id.cl_dialog_istanbul)
        val sochi = dialog.findViewById<ConstraintLayout>(R.id.cl_dialog_sochi)
        val phuket = dialog.findViewById<ConstraintLayout>(R.id.cl_dialog_phuket)

        difficultRoute.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_tickets_to_plugFragment)
            dialog.dismiss()
        }
        anywhere.setOnClickListener {
            searchTo.setText("Стамбул")
        }
        weekends.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_tickets_to_plugFragment)
            dialog.dismiss()
        }
        hotWeekdays.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_tickets_to_plugFragment)
            dialog.dismiss()
        }
        istanbul.setOnClickListener {
            searchTo.setText("Стамбул")
        }
        sochi.setOnClickListener {
            searchTo.setText("Сочи")
        }
        phuket.setOnClickListener {
            searchTo.setText("Пхукет")
        }
        searchTo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTo.clearFocus() // Убираем фокус с EditText
                true
            } else {
                false
            }
        }
        searchTo.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !searchTo.text.isNullOrBlank()) {
                dialog.dismiss()
            }
        }
        searchTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!searchTo.isFocused && !searchTo.text.isNullOrBlank()) {
                    dialog.dismiss()
                }
            }
        })

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)


    }

}