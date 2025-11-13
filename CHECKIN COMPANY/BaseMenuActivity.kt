package com.example.checkin
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseMenuActivity : AppCompatActivity() {
    var actividadActual : Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Infla el menú con las tres opciones
        getMenuInflater().inflate(R.menu.menu_principal, menu)
        return true // Siempre mostrar todas las opciones
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == R.id.menu_avisos) {
            val intent: Intent = Intent(this, AvisosActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
            return true
        } else if (id == R.id.menu_perfil) {
            val intent: Intent = Intent(this, PerfilActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
            return true
        } else if (id == R.id.menu_logout) {
            Snackbar.make(
                findViewById<View?>(android.R.id.content),
                "Sesión cerrada correctamente.",
                Snackbar.LENGTH_SHORT
            ).show()
            return true
        }else if (id == R.id.menu_inicio) {
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        // 0 = Main, 1 = Home, 2 = Settings (solo como referencia)
        var actividadActual: Int = 0
    }
}
