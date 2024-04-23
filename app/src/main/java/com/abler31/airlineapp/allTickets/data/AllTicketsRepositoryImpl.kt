package com.abler31.airlineapp.allTickets.data

import android.content.Context
import android.util.Log
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.allTickets.domain.model.Arrival
import com.abler31.airlineapp.allTickets.domain.model.Departure
import com.abler31.airlineapp.allTickets.domain.model.HandLuggage
import com.abler31.airlineapp.allTickets.domain.model.Luggage
import com.abler31.airlineapp.allTickets.domain.model.PriceX
import com.abler31.airlineapp.allTickets.domain.model.TicketBadged
import com.abler31.airlineapp.allTickets.domain.repository.AllTicketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class AllTicketsRepositoryImpl(private val context: Context) : AllTicketsRepository {
    override suspend fun getAllTickets(): Resource<List<TicketBadged>> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString =
                    context.assets.open("tickets.json").bufferedReader().use { it.readText() }
                val data = readDataFromJson(jsonString)
                Resource.Success(data = data)
            } catch (e: IOException) {
                Resource.Error("IO Exception")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }

    private fun readDataFromJson(jsonString: String): List<TicketBadged> {
        val ticketBadgeds = mutableListOf<TicketBadged>()
        val jsonObject = JSONObject(jsonString)
        val ticketsArray = jsonObject.getJSONArray("tickets")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        for (i in 0 until ticketsArray.length()) {
            val ticketObject = ticketsArray.getJSONObject(i)
            val id = ticketObject.getInt("id")
            val badge = ticketObject.optString("badge", null)
            val priceValue = ticketObject.getJSONObject("price").getInt("value")
            val price = PriceX(priceValue)
            val providerName = ticketObject.getString("provider_name")
            val company = ticketObject.getString("company")
            val departure = ticketObject.getJSONObject("departure")
            val departureTown = departure.getString("town")
            val departureDate = dateFormat.parse(departure.getString("date"))
            val departureAirport = departure.getString("airport")
            val departureInfo = Departure(departureAirport, departureDate, departureTown)
            val arrival = ticketObject.getJSONObject("arrival")
            val arrivalTown = arrival.getString("town")
            val arrivalDate = dateFormat.parse(arrival.getString("date"))
            val arrivalAirport = arrival.getString("airport")
            val arrivalInfo = Arrival(arrivalAirport, arrivalDate, arrivalTown)
            val hasTransfer = ticketObject.getBoolean("has_transfer")

            val hasVisaTransfer = ticketObject.getBoolean("has_visa_transfer")
            val luggageObject = if (ticketObject.has("luggage")) {
                val luggage = ticketObject.getJSONObject("luggage")
                val hasLuggage = luggage.getBoolean("has_luggage")
                val priceObject = if (luggage.has("price")) {
                    val priceValue = luggage.getJSONObject("price").getInt("value")
                    PriceX(priceValue)
                } else {
                    null
                }
                Luggage(hasLuggage, priceObject)
            } else {
                null
            }
            val handLuggageObject = ticketObject.getJSONObject("hand_luggage")
            val handLuggage = HandLuggage(
                handLuggageObject.getBoolean("has_hand_luggage"),
                handLuggageObject.optString("size", null) // Получаем значение размера или null, если параметр отсутствует
            )

            val isReturnable = ticketObject.getBoolean("is_returnable")
            val isExchangable = ticketObject.getBoolean("is_exchangable")

            val ticketBadged = TicketBadged(
                id = id,
                badge = badge,
                price = price,
                provider_name = providerName,
                company = company,
                departure = departureInfo,
                arrival = arrivalInfo,
                has_transfer = hasTransfer,
                has_visa_transfer = hasVisaTransfer,
                luggage = luggageObject!!,
                hand_luggage = handLuggage,
                is_returnable = isReturnable,
                is_exchangable = isExchangable
            )
            ticketBadgeds.add(ticketBadged)
        }

        return ticketBadgeds
    }
}