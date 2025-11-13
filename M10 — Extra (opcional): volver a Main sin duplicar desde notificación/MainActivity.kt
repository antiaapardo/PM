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
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

    private lateinit var etNombre: EditText

    override fun onResume() {
        super.onResume()
        BaseMenuActivity.actividadActual = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val rootView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnGuardar = findViewById<Button>(R.id.button)
        val btnAviso = findViewById<Button>(R.id.buttonAviso)
        val btnNotificacion = findViewById<Button>(R.id.buttonNotificacion)
        etNombre = findViewById(R.id.editTextText)

        // Registrar EditText para menú contextual
        registerForContextMenu(etNombre)

        // Botón GUARDAR → muestra Toast / Snackbar
        btnGuardar.setOnClickListener {
            val dialog = ConfirmDialog(
                onConfirm = { Snackbar.make(rootView, "Datos guardados", Snackbar.LENGTH_LONG).show() },
                onCancel = { Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show() }
            )
            dialog.show(supportFragmentManager, "ConfirmDialog")
        }

        // Botón AVISO → Snackbar con acción "Deshacer"
        btnAviso.setOnClickListener {
            Snackbar.make(rootView, "Acción realizada", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") { Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show() }
                .show()
        }

        // Botón NOTIFICACIÓN
        btnNotificacion.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
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

    // Menú contextual
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v?.id == R.id.editTextText) {
            menuInflater.inflate(R.menu.menu_contextual, menu)
            menu?.setHeaderTitle("Nombre de la planta")
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_limpiar -> {
                etNombre.setText("")
                true
            }
            R.id.action_copiar -> {
                val cm = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                cm.setPrimaryClip(android.content.ClipData.newPlainText("planta", etNombre.text))
                Toast.makeText(this, "Copiado", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_pegar -> {
                val cm = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                if (cm.hasPrimaryClip()) {
                    etNombre.setText(cm.primaryClip?.getItemAt(0)?.text ?: "")
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun enviarNotificacion() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notificaciones SmartGarden",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Intent actualizado con reordenar al frente y single top
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        else
            PendingIntent.FLAG_UPDATE_CURRENT

        val pending = PendingIntent.getActivity(this, 0, intent, flags)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("SmartGarden")
            .setContentText("Riego automático activado a las 20:00 🌧️")
            .setContentIntent(pending)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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
