package com.example.checkin

import android.os.Bundle
import android.widget.Button

class AvisosActivity : BaseMenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avisos_activity)

        // Configurar el botón para abrir el diálogo
        val btnEnviarAviso = findViewById<Button>(R.id.btnAviso)
        btnEnviarAviso.setOnClickListener {
            mostrarDialogoConfirmacion()
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun mostrarDialogoConfirmacion() {
        val dialog = MyDialogFragment()
        dialog.show(supportFragmentManager, "confirm_dialog")
}
    protected override fun onResume() {
        super.onResume()
        // Actualiza el estado compartido indicando que esta es la actividad actual
        BaseMenuActivity.actividadActual = 1
    }
}
