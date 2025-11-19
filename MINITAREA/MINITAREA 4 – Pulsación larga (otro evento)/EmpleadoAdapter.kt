package com.example.listaempleados

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class EmpleadoAdapter(
    private val lista: List<Empleado>,
    private val onItemClick: (Empleado, View) -> Unit
) : RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder>() {


    class EmpleadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvPuesto: TextView = itemView.findViewById(R.id.tvPuesto)
        private val tvTelefono: TextView = itemView.findViewById(R.id.tvTelefono)

        fun bind(emp: Empleado, onItemClick: (Empleado, View) -> Unit) {
            tvNombre.text = emp.nombre
            tvPuesto.text = emp.puesto
            tvTelefono.text = "📞 ${emp.telefono}"

            // Evento de clic normal
            itemView.setOnClickListener {
                onItemClick(emp, itemView)
            }

            // Evento de pulsación larga
            itemView.setOnLongClickListener {
                Toast.makeText(
                    itemView.context,
                    "Pulsación larga sobre ${emp.nombre}",
                    Toast.LENGTH_SHORT
                ).show()
                true // indica que el evento está consumido
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empleado, parent, false)
        return EmpleadoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: EmpleadoViewHolder, position: Int) {
        holder.bind(lista[position], onItemClick)
    }

    override fun getItemCount(): Int = lista.size


}
