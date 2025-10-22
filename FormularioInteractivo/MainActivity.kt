package com.example.formulariointeractivo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Recuperar los elementos del layout
        val editTextNombre = findViewById<EditText>(R.id.editTextText)
        val editTextCorreo = findViewById<EditText>(R.id.editTextText2)
        val checkBoxDeporte = findViewById<CheckBox>(R.id.checkBox)
        val checkBoxMusica = findViewById<CheckBox>(R.id.checkBox2)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val switchNotif = findViewById<Switch>(R.id.switch1)
        val buttonEnviar = findViewById<Button>(R.id.button)

        // 🔹 Configurar el Spinner con valores del strings.xml
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.opciones_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // 🔹 Acción al pulsar el botón
        buttonEnviar.setOnClickListener {
            // 1️⃣ Leer los valores introducidos o seleccionados
            val nombre = editTextNombre.text.toString()
            val correo = editTextCorreo.text.toString()
            val deporte = if (checkBoxDeporte.isChecked) "Sí" else "No"
            val musica = if (checkBoxMusica.isChecked) "Sí" else "No"
            val generoSeleccionadoId = radioGroup.checkedRadioButtonId
            val genero = if (generoSeleccionadoId != -1)
                findViewById<RadioButton>(generoSeleccionadoId).text
            else
                "No seleccionado"
            val opcionSpinner = spinner.selectedItem.toString()
            val notificaciones = if (switchNotif.isChecked) "Sí" else "No"

            // 2️⃣ Construir un mensaje resumen
            val resumen = """
                Nombre: $nombre
                Correo: $correo
                Género: $genero
                Le gusta el deporte: $deporte
                Le gusta la música: $musica
                Opción elegida: $opcionSpinner
                Recibir notificaciones: $notificaciones
            """.trimIndent()

            // 3️⃣ Mostrar el resumen en un Toast
            Toast.makeText(this, resumen, Toast.LENGTH_LONG).show()
        }
    }
}
