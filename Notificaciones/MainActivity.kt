package com.example.notificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "parque"
    private val NOTIFICATION_ID = 1001

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                mostrarNotificacion()
            } else {
                Toast.makeText(this, "Permiso denegado ❌", Toast.LENGTH_LONG).show()
            }
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

        crearCanalDeNotificacion()

        val btnToast = findViewById<Button>(R.id.BtnToast)
        val btnSnack = findViewById<Button>(R.id.btnSnack)
        val btnNotification = findViewById<Button>(R.id.btnNotificacion)

        btnToast.setOnClickListener {
            Toast.makeText(this, "¡Hola! Este es un Toast 😄", Toast.LENGTH_SHORT).show()
        }

        btnSnack.setOnClickListener {
            Snackbar.make(findViewById(R.id.main), "Cierre registrado", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    Toast.makeText(this, "Acción deshecha", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        btnNotification.setOnClickListener {
            verificarYPedirPermisoNotificacion()
        }
    }

    private fun verificarYPedirPermisoNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    mostrarNotificacion()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    Snackbar.make(
                        findViewById(R.id.main),
                        "Se necesita permiso para mostrar notificaciones",
                        Snackbar.LENGTH_LONG
                    ).setAction("OK") {
                        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    }.show()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            mostrarNotificacion()
        }
    }

    private fun crearCanalDeNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones del Parque",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Canal para avisos de cierre del parque"
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun mostrarNotificacion() {
        // 🔹 Paso 1: Mostrar el Toast de confirmación
        Toast.makeText(this, "✅ Notificación enviada", Toast.LENGTH_SHORT).show()

        // 🔹 Paso 2: Crear la notificación real que dirá “El parque cierra a las 20:00”
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setContentTitle("ℹ️ Aviso del Parque")
            .setContentText("El parque cierra a las 20:00 ⏰")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("¡Atención! El parque cerrará sus puertas a las 20:00. Planifica tu salida a tiempo.")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())


    }
    }

