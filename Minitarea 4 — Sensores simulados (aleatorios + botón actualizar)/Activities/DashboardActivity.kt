package com.example.minitareas
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : BaseMenuActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        title = "Dashboard"

        // Configurar los botones de navegación
        val btnRiego: Button = findViewById(R.id.btnRiego)
        val btnSensores: Button = findViewById(R.id.btnSensores)

        btnRiego.setOnClickListener {
            startActivity(Intent(this, RiegoActivity::class.java))
        }

        btnSensores.setOnClickListener {
            startActivity(Intent(this, SensoresActivity::class.java))
            }
}
    }

