package com.abler31.airlineapp.tickets.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abler31.airlineapp.R
import com.abler31.airlineapp.tickets.domain.model.Offer

class TicketsRecyclerAdapter : RecyclerView.Adapter<TicketsRecyclerAdapter.TicketsViewHolder>() {

    private var itemsList = emptyList<Offer>()

    inner class TicketsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_recycler_item_band)
        private val townTextView: TextView = itemView.findViewById(R.id.tv_recycler_item_city)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_recycler_item_price)
        private val img: ImageView = itemView.findViewById(R.id.iv_recycler_item_main)

        fun bind(offer: Offer) {
            titleTextView.text = offer.title
            townTextView.text = offer.town
            priceTextView.text = offer.price.value.toString()
                .replace("(\\d)(?=(\\d{3})+$)"
                    .toRegex(), "$1 ").plus(" ₽")

            when(offer.id){
                1 -> img.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.img1))
                2 -> img.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.img2))
                3 -> img.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.img3))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_main_screen, parent, false)
        return TicketsViewHolder(
            itemView
        )
    }

    override fun getItemCount() = itemsList.size

    override fun onBindViewHolder(holder: TicketsViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun setData(itemsList: List<Offer>) {
        this.itemsList = itemsList
        notifyDataSetChanged()
    }
}