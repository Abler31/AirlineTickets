package com.abler31.airlineapp.countrySelected.data.repository

import android.content.Context
import android.util.Log
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.countrySelected.domain.model.Price
import com.abler31.airlineapp.countrySelected.domain.model.TicketsOffer
import com.abler31.airlineapp.countrySelected.domain.repository.CountrySelectedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

class CountrySelectedRepositoryImpl(private val context: Context): CountrySelectedRepository {
    override suspend fun getTicketsOffers(): Resource<List<TicketsOffer>> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open("offers_tickets.json").bufferedReader().use { it.readText() }
                val data = readDataFromJson(jsonString)
                Resource.Success(data = data)
            } catch (e: IOException) {
                Resource.Error("IO Exception")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }

    private fun readDataFromJson(jsonString: String): List<TicketsOffer> {
        val ticketsOffers = mutableListOf<TicketsOffer>()
        val jsonObject = JSONObject(jsonString)
        val offersArray = jsonObject.getJSONArray("tickets_offers")
        for (i in 0 until offersArray.length()) {
            val offerObject = offersArray.getJSONObject(i)
            val id = offerObject.getInt("id")
            val title = offerObject.getString("title")
            val priceObject = offerObject.getJSONObject("price")
            val priceValue = priceObject.getInt("value")
            val timeRangeArray = offerObject.getJSONArray("time_range")
            val timeRange = mutableListOf<String>()
            for (j in 0 until timeRangeArray.length()) {
                val time = timeRangeArray.getString(j)
                timeRange.add(time)
            }
            val price = Price(priceValue)
            val offer = TicketsOffer(id, price, timeRange, title)
            ticketsOffers.add(offer)
        }
        return ticketsOffers
    }
}