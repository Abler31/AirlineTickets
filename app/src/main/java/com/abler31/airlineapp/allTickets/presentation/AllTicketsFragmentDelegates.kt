package com.abler31.airlineapp.allTickets.presentation

import android.widget.TextView
import androidx.core.view.isVisible
import com.abler31.airlineapp.R
import com.abler31.airlineapp.allTickets.domain.model.DisplayableItem
import com.abler31.airlineapp.allTickets.domain.model.TicketBadged
import com.abler31.airlineapp.allTickets.domain.model.Ticket
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs
import kotlin.math.round

object AllTicketsFragmentDelegates {

    fun ticketDelegates() = adapterDelegate<Ticket, DisplayableItem>(
        layout = R.layout.item_all_tickets
    ){
        val price = findViewById<TextView>(R.id.tv_all_tickets_price)
        val timeDeparture = findViewById<TextView>(R.id.tv_time_departure)
        val timeArrival = findViewById<TextView>(R.id.tv_time_arrival)
        val airportArrival = findViewById<TextView>(R.id.tv_arrival_airport)
        val airportDeparture = findViewById<TextView>(R.id.tv_departure_airport)
        val tvPathTime = findViewById<TextView>(R.id.tv_path_time)
        val slash = findViewById<TextView>(R.id.tv_slash)
        val transfer = findViewById<TextView>(R.id.tv_transfers)
        val dateFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

        bind {
            price.text = item.price.value.toString().replace("(\\d)(?=(\\d{3})+$)"
                .toRegex(), "$1 ").plus(" ₽")
            timeDeparture.text = dateFormat.format(item.departure.date)
            timeArrival.text = dateFormat.format(item.arrival.date)
            airportArrival.text = item.arrival.airport
            airportDeparture.text = item.departure.airport
            val differenceInMillis = abs(item.departure.date.time - item.arrival.date.time)
            val hoursInHalfHour = (1000 * 60 * 30).toDouble() // количество миллисекунд в полчаса
            val hoursInPath = differenceInMillis.toDouble() / hoursInHalfHour // количество часов в пути, кратное 0.5
            val roundedHours = round(hoursInPath) // округляем до ближайшего целого числа
            val roundedHoursInPath = roundedHours * 0.5 // получаем количество часов, округленное до 0.5
            tvPathTime.text = "${roundedHoursInPath} ч в пути"
            if (!item.has_transfer){
                slash.isVisible = false
                transfer.isVisible = false
            }
        }
    }

    fun ticketBadgedDelegates() = adapterDelegate<TicketBadged, DisplayableItem>(
        layout = R.layout.item_all_tickets_badged
    ){
        val price = findViewById<TextView>(R.id.tv_all_tickets_price_badged)
        val timeDeparture = findViewById<TextView>(R.id.tv_time_departure_badged)
        val timeArrival = findViewById<TextView>(R.id.tv_time_arrival_badged)
        val airportArrival = findViewById<TextView>(R.id.tv_arrival_airport_badged)
        val airportDeparture = findViewById<TextView>(R.id.tv_departure_airport_badged)
        val tvPathTime = findViewById<TextView>(R.id.tv_path_time_badged)
        val slash = findViewById<TextView>(R.id.tv_slash_badged)
        val transfer = findViewById<TextView>(R.id.tv_transfers_badged)
        val badge = findViewById<TextView>(R.id.tv_badge)
        val dateFormat = SimpleDateFormat("HH.mm", Locale.getDefault())

        bind {
            price.text = item.price.value.toString().replace("(\\d)(?=(\\d{3})+$)"
                .toRegex(), "$1 ").plus(" ₽")
            timeDeparture.text = dateFormat.format(item.departure.date)
            timeArrival.text = dateFormat.format(item.arrival.date)
            airportArrival.text = item.arrival.airport
            airportDeparture.text = item.departure.airport
            tvPathTime.text = "${abs((item.departure.date.time - item.arrival.date.time) / (1000 * 60 * 60))} ч в пути"
            if (!item.has_transfer){
                slash.isVisible = false
                transfer.isVisible = false
            }
            badge.text = item.badge
        }
    }

}