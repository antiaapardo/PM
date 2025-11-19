import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listaempleados.Empleado
import com.example.listaempleados.EmpleadoAdapter
import com.example.listaempleados.R
import com.google.android.material.snackbar.Snackbar

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


        adapter = EmpleadoAdapter(listaEmpleados) { empleado ->
            Snackbar.make(
                findViewById(R.id.recyclerEmpleados), // o root layout de tu activity
                "Empleado seleccionado: ${empleado.nombre}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        recycler.adapter = adapter
    }
}
