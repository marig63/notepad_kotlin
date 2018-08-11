package com.marisa.guillaume.notepad.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.marisa.guillaume.notepad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

private val TAG = "storage"

fun persistNote(context: Context, note: Note){

    if(TextUtils.isEmpty(note.filename)){
        note.filename = UUID.randomUUID().toString() + ".note"
    }

    val fileOutput = context.openFileOutput(note.filename,Context.MODE_PRIVATE)
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)
    outputStream.close()
}

fun loadNotes(context: Context):MutableList<Note>{
    val notes = mutableListOf<Note>()

    val notesDir = context.filesDir
    for(filename in notesDir.list()){
        val note = loadNote(context, filename)
        Log.i(TAG,"loaded note ${note.title}")
        notes.add(note)
    }

    return  notes
}

fun deleteNote(context: Context, note: Note){
    context.deleteFile(note.filename)
}

private fun loadNote(context: Context,fileName:String): Note{
    val fileInput= context.openFileInput(fileName)
    val inputStream = ObjectInputStream(fileInput)
    val note = inputStream.readObject() as Note
    inputStream.close()

    return note
}