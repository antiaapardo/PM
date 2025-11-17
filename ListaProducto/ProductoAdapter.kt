package com.example.listaproductos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(private val lista: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreText: TextView = itemView.findViewById(R.id.textViewNombre)
        val precioText: TextView = itemView.findViewById(R.id.textViewPrecio)
        val stockText: TextView = itemView.findViewById(R.id.textViewStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]
        holder.nombreText.text = producto.nombre
        holder.precioText.text = "Precio: €${producto.precio}"
        holder.stockText.text = "Stock: ${producto.stock}"
    }

    override fun getItemCount(): Int = lista.size
}
