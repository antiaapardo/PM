package com.example.listaproductos


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de ejemplo
        val productos = listOf(
            Producto("Ordenador", 1200.0, 5),
            Producto("Ratón", 25.0, 12),
            Producto("Teclado", 45.0, 7),
            Producto("Monitor", 200.0, 3)
        )

        val adapter = ProductoAdapter(productos)
        recyclerView.adapter = adapter
    }
}
