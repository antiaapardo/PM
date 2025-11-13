package com.example.checkin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PerfilActivity : BaseMenuActivity() {

    protected override fun onResume() {
        super.onResume()
        // Actualiza el estado compartido indicando que esta es la actividad actual
        BaseMenuActivity.actividadActual = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_activity) // 👈 tu layout para el perfil

        val btnGuardar =  findViewById<Button>(R.id.btnGuardar)
        val editNombre = findViewById<EditText>(R.id.editTextText) // 👈 Tu campo de texto
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        btnGuardar.setOnClickListener {
            val nombre = editNombre.text.toString().trim() // Obtiene el texto

            if (nombre.isNotEmpty()) {
                Toast.makeText(this, "Perfil actualizado: $nombre", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, introduce un nombre", Toast.LENGTH_SHORT).show()
            }
           }
}}
