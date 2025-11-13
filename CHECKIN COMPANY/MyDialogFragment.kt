package com.example.checkin

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        builder.setTitle("Confirmar Guardado")
            .setMessage("¿Deseas enviar un aviso urgente al equipo?")
            .setPositiveButton("Aceptar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    // Mostrar Toast cuando se presiona Aceptar
                    Toast.makeText(requireContext(), "Aviso enviado 🔴", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            })
            .setNegativeButton("Cancelar", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    // Mostrar Snackbar cuando se presiona Cancelar
                    val rootView = requireActivity().findViewById<android.view.View>(android.R.id.content)
                    Snackbar.make(rootView, "Operación cancelada", Snackbar.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            })

        return builder.create()
    }
}
