package com.example.minitarea1

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDialog(private val onConfirm: () -> Unit, private val onCancel: () -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Confirmación")
            .setMessage("¿Deseas guardar los datos de esta planta?")
            .setPositiveButton("Aceptar") { _, _ ->
                onConfirm()
            }
            .setNegativeButton("Cancelar") { _, _ ->
                onCancel()
            }
            .create()
    }
}
