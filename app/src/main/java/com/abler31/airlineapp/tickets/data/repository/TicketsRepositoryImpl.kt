package com.abler31.airlineapp.tickets.data.repository

import android.content.Context
import android.util.Log
import com.abler31.airlineapp.Resource
import com.abler31.airlineapp.tickets.domain.model.Offer
import com.abler31.airlineapp.tickets.domain.model.Price
import com.abler31.airlineapp.tickets.domain.repository.TicketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException

class TicketsRepositoryImpl(private val context: Context): TicketsRepository {
    override suspend fun getOffers(): Resource<List<Offer>> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = context.assets.open("offers.json").bufferedReader().use { it.readText() }
                val data = readDataFromJson(jsonString)
                Resource.Success(data = data)
            } catch (e: IOException) {
                Resource.Error("IO Exception")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }

    }

    private fun readDataFromJson(jsonString: String): List<Offer> {
        val offers = mutableListOf<Offer>()
        val jsonObject = JSONObject(jsonString)
        val offersArray = jsonObject.getJSONArray("offers")
        for (i in 0 until offersArray.length()) {
            val offerObject = offersArray.getJSONObject(i)
            val id = offerObject.getInt("id")
            val title = offerObject.getString("title")
            val town = offerObject.getString("town")
            val priceObject = offerObject.getJSONObject("price")
            val priceValue = priceObject.getInt("value")
            val price = Price(priceValue)
            val offer = Offer(id, title, town, price)
            offers.add(offer)
        }
        return offers
    }
}