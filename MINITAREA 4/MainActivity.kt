package com.example.parquesapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(),
    ConfirmDeleteDialogFragment.OnDeleteConfirmedListener {

    private lateinit var rvParques: RecyclerView
    private lateinit var adaptador: ParquesAdapter

    // Lista mutable para poder borrar elementos - ¡YA ESTÁ CORRECTO!
    private val listaParques = mutableListOf(
        "Parque de Castrelos",
        "Parque de Castelao",
        "Parque de Navia",
        "Parque del Castro",
        "Monte dos Pozos"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvParques = findViewById(R.id.rvParques)
        rvParques.layoutManager = LinearLayoutManager(this)

        adaptador = ParquesAdapter(
            parques = listaParques,
            onItemClick = { nombreParque ->
                // Click normal → mostramos mensaje
                Snackbar.make(
                    rvParques,
                    "Has pulsado: $nombreParque",
                    Snackbar.LENGTH_LONG
                ).show()
            },
            onItemLongClick = { position, nombreParque ->
                // Click largo → mostramos DialogFragment
                val dialog = ConfirmDeleteDialogFragment.newInstance(position, nombreParque)
                dialog.show(supportFragmentManager, "ConfirmDeleteDialog")
            }
        )

        rvParques.adapter = adaptador
    }

    // Callback que viene del DialogFragment cuando confirman el borrado
    override fun onDeleteConfirmed(position: Int) {
        if (position in listaParques.indices) {
            val nombre = listaParques[position]

            // 1) Borrar de la lista
            listaParques.removeAt(position)

            // 2) Avisar al adapter para que actualice la UI
            adaptador.notifyItemRemoved(position)

            // 3) Mostrar confirmación
            Snackbar.make(
                rvParques,
                "Parque eliminado: $nombre",
                Snackbar.LENGTH_LONG
            ).setAction("Deshacer") {
                // Opcional: Deshacer la eliminación
                listaParques.add(position, nombre)
                adaptador.notifyItemInserted(position)
            }.show()
        }
    }
}
