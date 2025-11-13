package com.example.checkin

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : BaseMenuActivity() {

    protected override fun onResume() {
        super.onResume()
        // Actualiza el estado compartido indicando que esta es la actividad actual
        BaseMenuActivity.actividadActual = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val btnEntrada = findViewById<Button>(R.id.btnEntrada)
        val btnSalida = findViewById<Button>(R.id.btnSalida)
        val btnNotificacion = findViewById<Button>(R.id.btnNotificacion)
        val layoutPrincipal = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)

        btnEntrada.setOnClickListener {
            // Cambiar color de fondo para ENTRADA (Verde)
            layoutPrincipal.setBackgroundColor(Color.parseColor("#C8E6C9"))

            val fechaHora = obtenerFechaHoraActual()
            Toast.makeText(this, "Entrada registrada\n$fechaHora", Toast.LENGTH_LONG).show()
        }


        btnSalida.setOnClickListener {
            Snackbar.make(findViewById(R.id.main),  "Salida registrada", Toast.LENGTH_SHORT)
                .setAction("Deshacer"){
                Toast.makeText(this,"Operacion cancelada", Toast.LENGTH_SHORT).show()
            }.show()
        }

        btnNotificacion.setOnClickListener {
            mostrarNotificacion()
        }
    }
    private fun obtenerFechaHoraActual(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return formato.format(Date())
    }

    private fun mostrarNotificacion() {
        val channelId = "canal_simple"

        // 🟢 Pedir permiso si es Android 13 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
                return
            }
        }

        // 🟢 Crear canal si hace falta (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Canal simple",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE
                    else 0
        )

        // 🟢 Crear y mostrar la notificación
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("CheckIn Company")
            .setContentText("Nuevo comunicado interno disponible")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // 👈 Aquí se añade el PendingIntent
            .setAutoCancel(true) // 👈 Cierra la notificación al pulsarla

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    // 🟢 Si el usuario concede el permiso, vuelve a mostrar la notificación
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100 && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            mostrarNotificacion()
           }
}}
