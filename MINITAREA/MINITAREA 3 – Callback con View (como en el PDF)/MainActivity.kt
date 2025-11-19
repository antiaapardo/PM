  package com.example.listaempleados

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    private lateinit var recycler: RecyclerView
    private lateinit var adapter: EmpleadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "CheckIn Company - Empleados"

        recycler = findViewById(R.id.recyclerEmpleados)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        val listaEmpleados = listOf(
            Empleado("Ana López", "Administrativa", "600123456"),
            Empleado("Carlos Pérez", "Técnico de soporte", "622987654"),
            Empleado("Lucía Torres", "Desarrolladora Android", "633222444"),
            Empleado("Miguel Romero", "Diseñador UX", "611555777"),
            Empleado("Sofía Gil", "Comercial", "699333888"),
            Empleado("Javier Marín", "CEO", "655111222"),
            Empleado("Elena Ruiz", "Marketing Digital", "677888999")
        )

        // Adapter con callback que recibe también la View
        adapter = EmpleadoAdapter(listaEmpleados) { empleado, vista ->
            // Cambiamos color de fondo de la fila clicada
            vista.setBackgroundColor(getColor(android.R.color.holo_green_light))

            // Mostramos un Toast
            Toast.makeText(this, "Empleado seleccionado: ${empleado.nombre}", Toast.LENGTH_SHORT).show()
        }

        recycler.adapter = adapter
    }

}
