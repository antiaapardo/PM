package com.example.minitarea1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseMenuActivity : AppCompatActivity() {

    companion object {
        // 0 = Main, 1 = Home, 2 = Settings
        var actividadActual = 0
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        // Ocultar o deshabilitar el item de la actividad actual
        for (i in 0 until menu.size()) {
            menu.getItem(i).isVisible = i != actividadActual
            menu.getItem(i).isEnabled = i != actividadActual
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                if (actividadActual != 1) {
                    val intent = Intent(this, HomeActivity::class.java)
                    // Reordenar al frente para no duplicar actividad
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    actividadActual = 1
                    startActivity(intent)
                }
                true
            }
            R.id.menu_settings -> {
                if (actividadActual != 2) {
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    actividadActual = 2
                    startActivity(intent)
                }
                true
            }
            R.id.menu_logout -> {
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
