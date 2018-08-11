package com.marisa.guillaume.notepad

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        notes = mutableListOf<Note>()
        notes.add(Note("Note 1","éergferopgzepkgegkper"))
        notes.add(Note("Note 2","éergferopgzepkgegkper"))
        notes.add(Note("Note 3","éergferopgzepkgegkper"))
        notes.add(Note("Note 4","éergferopgzepkgegkper"))
        notes.add(Note("Note 5","éergferopgzepkgegkper"))
        notes.add(Note("Note 6","éergferopgzepkgegkper"))

        adapter = NoteAdapter(notes,this)

        val recyclerView = findViewById(R.id.notes_recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onClick(view: View) {
        if(view.tag != null){
            Log.i("notepad","click sur une note")
            showNoteDetail(view.tag as Int)
        }
        else{
            when (view.id){
                R.id.fab -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    fun showNoteDetail(noteIndex:Int){

        val note = if(noteIndex == -1){
            Note()
        }
        else{
            notes[noteIndex]
        }

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE,note)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX,noteIndex)
        startActivityForResult(intent,NoteDetailActivity.REQUEST_EDIT_NOTE)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK || data == null){
            return
        }

        when(requestCode){
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }

    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)
        when(data.action){
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note,noteIndex)
            }

            NoteDetailActivity.ACTION_DELTE_NOTE -> {
                deleteNote(noteIndex)
            }
        }


    }

    private fun deleteNote(noteIndex: Int) {
        if(noteIndex < 0 ) return
        val note = notes.removeAt(noteIndex)
        adapter.notifyDataSetChanged()
    }

    fun saveNote(note: Note, noteIndex: Int){
        if(noteIndex < 0){
            notes.add(0,note)
        }
        else{
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }
}
