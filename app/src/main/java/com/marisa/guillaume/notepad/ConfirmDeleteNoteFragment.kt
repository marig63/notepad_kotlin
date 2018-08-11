package com.marisa.guillaume.notepad

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

class ConfirmDeleteNoteFragment(val noteTitle: String = "") : DialogFragment(){

    interface ConfirmDeleteDialogListener{
        fun onDialogPosClick()
        fun onDialogNegClick()
    }

    var listener: ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage("Etes-vous sur de vouloir supprimer la note \"$noteTitle\" ?")
                .setPositiveButton("Supprimer",DialogInterface.OnClickListener{dialog, id -> listener?.onDialogPosClick()})
                .setNegativeButton("Annuler",DialogInterface.OnClickListener{dialog, id -> listener?.onDialogNegClick()})

        return builder.create()
    }
}