package com.marisa.guillaume.notepad

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

class NoteDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
        val REQUEST_EDIT_NOTE = 1
        val ACTION_SAVE_NOTE = "ACTION_SAVE_NOTE"
        val ACTION_DELTE_NOTE = "ACTION_DELETE_NOTE"

    }

    lateinit var note: Note
    var noteIndex: Int = -1

    lateinit var titleView : TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX,-1)

        titleView = findViewById(R.id.title) as TextView
        textView = findViewById(R.id.text) as TextView

        titleView.text = note.title
        textView.text = note.text

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save -> {
                saveNote()
                return true
            }
            R.id.action_delete -> {
                showConfirmDelete()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmDelete() {
        val confirmDeleteNoteFragment = ConfirmDeleteNoteFragment(note.title)
        confirmDeleteNoteFragment.listener = object: ConfirmDeleteNoteFragment.ConfirmDeleteDialogListener{
            override fun onDialogPosClick() {
                deleteNote()
            }

            override fun onDialogNegClick() {
                return
            }

        }

        confirmDeleteNoteFragment.show(supportFragmentManager, "confirmDelete")
    }

    fun saveNote(){
        note.title = titleView.text.toString()
        note.text = textView.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE,note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX,noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    fun deleteNote(){
        intent = Intent(ACTION_DELTE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }
}
