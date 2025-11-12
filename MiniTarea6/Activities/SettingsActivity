package com.example.minitarea1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SettingsActivity : BaseMenuActivity() {

    protected override fun onResume() {
        super.onResume()
        // Actualiza el estado compartido indicando que esta es la actividad actual
        BaseMenuActivity.actividadActual = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val btnBack = findViewById<Button>(R.id.btnBackSettings)
        btnBack.setOnClickListener {
            Toast.makeText(this, "Volviendo a la pantalla principal", Toast.LENGTH_SHORT).show()
        }
    }

}
