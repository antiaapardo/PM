package com.example.minitareas

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class RiegoActivity : BaseMenuActivity() {

    private lateinit var btnOn: Button
    private lateinit var btnOff: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riego)

        title = "Riego"

        btnOn = findViewById(R.id.btnOn)
        btnOff = findViewById(R.id.btnOff)

        btnOn.setOnClickListener {
            Toast.makeText(this, "Riego activado", Toast.LENGTH_SHORT).show()
        }

        btnOff.setOnClickListener {
            val root = findViewById<android.view.View>(android.R.id.content)
            Snackbar.make(root, "Riego detenido", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    Toast.makeText(this, "Riego activado", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}
