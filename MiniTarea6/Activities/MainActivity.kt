package com.example.minitarea1

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
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : BaseMenuActivity() {


    private val CHANNEL_ID = "smartgarden_channel"
    private val NOTIFICATION_ID = 1
    private val PERMISSION_REQUEST_CODE = 1001

    override fun onResume() {
        super.onResume()
        // Actualiza el estado compartido indicando que esta es la actividad actual
        BaseMenuActivity.actividadActual = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val btnGuardar = findViewById<Button>(R.id.button)
        val btnAviso = findViewById<Button>(R.id.buttonAviso)
        val btnNotificacion = findViewById<Button>(R.id.buttonNotificacion)
        val editText = findViewById<EditText>(R.id.editTextText)
        val rootView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)

        // Botón GUARDAR → muestra Toast
        btnGuardar.setOnClickListener {
            val dialog = ConfirmDialog(
                onConfirm = {
                    // Mostrar Snackbar al aceptar
                    Snackbar.make(rootView, "Datos guardados", Snackbar.LENGTH_LONG).show()
                },
                onCancel = {
                    // Mostrar Toast al cancelar
                    Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
                }
            )
            dialog.show(supportFragmentManager, "ConfirmDialog")
        }

        // Botón MOSTRAR AVISO → muestra Snackbar con acción "Deshacer"
        btnAviso.setOnClickListener {
            Snackbar.make(rootView, "Acción realizada", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // Botón ENVIAR NOTIFICACIÓN
        btnNotificacion.setOnClickListener {
            // Pedir permiso en Android 13+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                enviarNotificacion()
            }
        }
    }

    private fun enviarNotificacion() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Crear canal en Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones SmartGarden",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Intent para abrir la app
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Crear notificación
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("SmartGarden")
            .setContentText("Riego automático activado a las 20:00 🌧️")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    // Manejar respuesta del permiso
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enviarNotificacion()
            } else {
                Toast.makeText(this, "Permiso denegado para enviar notificaciones", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
