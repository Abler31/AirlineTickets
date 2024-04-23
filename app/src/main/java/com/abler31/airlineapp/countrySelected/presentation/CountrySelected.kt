package com.abler31.airlineapp.countrySelected.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.abler31.airlineapp.R
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.tickets.presentation.TicketsViewModel
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        val button = view.findViewById<MaterialButton>(R.id.button_show_all)


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

        //change icon
        val changeIcon = view.findViewById<ImageView>(R.id.iv_change)
        val from = view.findViewById<EditText>(R.id.et_search_from_selected)
        val to = view.findViewById<EditText>(R.id.et_search_to_selected)

        changeIcon.setOnClickListener {
            val temp = from.text
            from.text = to.text
            to.text = temp
        }

        val datePickerTo = view.findViewById<CardView>(R.id.cv_date_picker_to)
        val datePickerBack = view.findViewById<CardView>(R.id.cv_date_picker_back)

        datePickerTo.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val dateTo = view.findViewById<TextView>(R.id.tv_countrySelected_date_to)
            val dayOfWeekTo = view.findViewById<TextView>(R.id.tv_countrySelected_dayOfWeek_to)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    val dayOfWeekFormat = SimpleDateFormat("E", Locale.getDefault())
                    val dayOfWeek = dayOfWeekFormat.format(selectedDate.time)

                    dateTo.text = formattedDate.replace(".", "")
                    dayOfWeekTo.text = ", $dayOfWeek"
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            // Отобразите DatePickerDialog
            datePickerDialog.show()
        }

        datePickerBack.setOnClickListener {

            val currentDate = Calendar.getInstance()

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    val dayOfWeekFormat = SimpleDateFormat("E", Locale.getDefault())
                    val dayOfWeek = dayOfWeekFormat.format(selectedDate.time)


                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        val arrowBack = view.findViewById<ImageView>(R.id.iv_arrow_back_country_selected)
        arrowBack.setOnClickListener {
            findNavController().navigate(R.id.action_countrySelected_to_navigation_tickets)
        }

        button.setOnClickListener {
            findNavController().navigate(R.id.action_countrySelected_to_allTicketsFragment)
        }

    }
}