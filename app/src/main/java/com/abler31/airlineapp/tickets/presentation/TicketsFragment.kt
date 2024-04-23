package com.abler31.airlineapp.tickets.presentation

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketsFragment : Fragment(R.layout.fragment_tickets) {

    private val vm by viewModel<TicketsViewModel>()
    private lateinit var ticketsRecyclerAdapter: TicketsRecyclerAdapter
    lateinit var ticketsRecyclerView: RecyclerView
    lateinit var dataStoredPreferences : SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvFrom = view.findViewById<TextView>(R.id.et_search_from)
        val tvTo = view.findViewById<TextView>(R.id.et_search_to)
        val searchCV = view.findViewById<CardView>(R.id.cv_search_tickets)
        dataStoredPreferences =
            requireContext().getSharedPreferences("dataStored", Context.MODE_PRIVATE)

// Получаем сохраненную строку по ключу "saved_text", если значение не найдено, возвращаем пустую строку
        val savedText = dataStoredPreferences.getString("saved_text", "")
        tvFrom.text = savedText

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
        searchCV.setOnClickListener{
            showBottomDialog()
        }


    }

    private fun showBottomDialog(){
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_search)
        val searchTo = dialog.findViewById<EditText>(R.id.et_search_to_dialog)
        val searchFrom = dialog.findViewById<EditText>(R.id.et_search_from_dialog)
        val close = dialog.findViewById<ImageView>(R.id.iv_close_dialog)

        val difficultRoute = dialog.findViewById<LinearLayout>(R.id.ll_difficult_route)
        val anywhere = dialog.findViewById<LinearLayout>(R.id.ll_anywhere)
        val weekends = dialog.findViewById<LinearLayout>(R.id.ll_weekend)
        val hotWeekdays = dialog.findViewById<LinearLayout>(R.id.ll_hot_weekdays)
        val istanbul = dialog.findViewById<ConstraintLayout>(R.id.cl_dialog_istanbul)
        val sochi = dialog.findViewById<ConstraintLayout>(R.id.cl_dialog_sochi)
        val phuket = dialog.findViewById<ConstraintLayout>(R.id.cl_dialog_phuket)

        val savedText = dataStoredPreferences.getString("saved_text", "")
        searchFrom.setText(savedText)

        // InputFilter для кириллицы
        val cyrillicFilter = InputFilter { source, _, _, _, _, _ ->
            for (char in source) {
                if (!char.isLetter() || char !in '\u0400'..'\u04FF') {
                    return@InputFilter ""
                }
            }
            null
        }

        // Применяем InputFilter к EditText
        searchTo.filters = arrayOf(cyrillicFilter)
        searchFrom.filters = arrayOf(cyrillicFilter)

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
                val direction = TicketsFragmentDirections.actionNavigationTicketsToCountrySelected(searchFrom.text.toString(), searchTo.text.toString())
                findNavController().navigate(direction)
                with(dataStoredPreferences.edit()) {
                    putString("saved_text", searchFrom.text.toString())
                    apply()
                }
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
                    val direction = TicketsFragmentDirections.actionNavigationTicketsToCountrySelected(searchFrom.text.toString(), searchTo.text.toString())
                    findNavController().navigate(direction)
                    with(dataStoredPreferences.edit()) {
                        putString("saved_text", searchFrom.text.toString())
                        apply()
                    }
                    dialog.dismiss()
                }
            }
        })

        close.setOnClickListener {
            searchTo.text.clear()
        }




        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)


    }

}