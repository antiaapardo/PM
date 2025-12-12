package com.example.parquesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParquesAdapter(
    private val parques: MutableList<String>,  // Ya está bien como MutableList
    private val onItemClick: (String) -> Unit,
    private val onItemLongClick: (Int, String) -> Unit
) : RecyclerView.Adapter<ParquesAdapter.ParqueViewHolder>() {

    class ParqueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreParque)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParqueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parque, parent, false)
        return ParqueViewHolder(view)
    }

    override fun getItemCount(): Int = parques.size

    override fun onBindViewHolder(holder: ParqueViewHolder, position: Int) {
        val nombre = parques[position]
        holder.txtNombre.text = nombre

        // Click normal → solo avisamos al callback
        holder.itemView.setOnClickListener {
            onItemClick(nombre)
        }

        // Click largo → avisamos con posición y nombre
        holder.itemView.setOnLongClickListener {
            // Usamos bindingAdapterPosition que es más seguro
            val posReal = holder.bindingAdapterPosition
            if (posReal != RecyclerView.NO_POSITION) {
                onItemLongClick(posReal, nombre)
            }
            true  // indicamos que el evento se ha consumido
        }
    }

    // Método opcional para actualizar la lista si es necesario
    fun updateList(newList: MutableList<String>) {
        parques.clear()
        parques.addAll(newList)
        notifyDataSetChanged()
    }
}
