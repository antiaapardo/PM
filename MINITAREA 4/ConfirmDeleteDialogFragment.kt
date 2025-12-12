package com.example.parquesapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDeleteDialogFragment : DialogFragment() {

    interface OnDeleteConfirmedListener {
        fun onDeleteConfirmed(position: Int)
    }

    private var listener: OnDeleteConfirmedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verificar que la Activity implementa la interfaz
        if (context is OnDeleteConfirmedListener) {
            listener = context
        } else {
            throw ClassCastException("$context debe implementar OnDeleteConfirmedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Recuperamos los argumentos
        val position = requireArguments().getInt(ARG_POSITION)
        val name = requireArguments().getString(ARG_NAME, "este parque")

        return AlertDialog.Builder(requireContext())
            .setTitle("Borrar parque")
            .setMessage("¿Seguro que quieres borrar \"$name\"?")
            .setPositiveButton("Sí") { _, _ ->
                // Avisamos a la Activity usando el listener
                listener?.onDeleteConfirmed(position)
            }
            .setNegativeButton("Cancelar", null)
            .create()
    }

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_NAME = "name"

        // Método de fábrica para crear el diálogo con datos
        fun newInstance(position: Int, name: String): ConfirmDeleteDialogFragment {
            val fragment = ConfirmDeleteDialogFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putString(ARG_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }
}
