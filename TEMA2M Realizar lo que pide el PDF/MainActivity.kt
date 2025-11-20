package com.example.misparques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.ParksRecyclerView)

        // Datos de ejemplo
        val parks = listOf(
            Park("Parque Central", "Zona verde con fuentes", R.drawable.parque_central),
            Park("Jardín del Sol", "Perfecto para pasear mascotas", R.drawable.jardin_del_sol),
            Park("Bosque Norte", "Rutas de senderismo", R.drawable.bosque_norte)
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        recycler.adapter = ParkAdapter(parks)
    }
}
