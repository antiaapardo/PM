package com.example.minitarea1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class HomeActivity : BaseMenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnBack = findViewById<Button>(R.id.btnBackHome)
        btnBack.setOnClickListener {
            Toast.makeText(this, "Volviendo a la pantalla principal", Toast.LENGTH_SHORT).show()
        }
    }

    protected override fun onResume() {
        super.onResume()
        // Actualiza el estado compartido indicando que esta es la actividad actual
        BaseMenuActivity.actividadActual = 0
    }
}
