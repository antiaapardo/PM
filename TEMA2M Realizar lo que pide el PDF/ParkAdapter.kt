package com.example.misparques

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParkAdapter(
    private val parks: List<Park>
) : RecyclerView.Adapter<ParkAdapter.ParkViewHolder>() {

    // VIEWHOLDER
    class ParkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parkImage: ImageView = itemView.findViewById(R.id.parkImageView)
        private val parkName: TextView = itemView.findViewById(R.id.parkName)
        private val parkDesc: TextView = itemView.findViewById(R.id.parkDesc)

        fun bind(park: Park) {
            parkImage.setImageResource(park.imageRes)
            parkName.text = park.name
            parkDesc.text = park.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.parque_card, parent, false)
        return ParkViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        holder.bind(parks[position])
    }

    override fun getItemCount(): Int = parks.size
}
