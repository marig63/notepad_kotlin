package com.marisa.guillaume.notepad

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NoteAdapter(val notes: List<Note>,
                         val itemListener: View.OnClickListener): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val viewItem = LayoutInflater.from(p0.context).inflate(R.layout.item_note, p0,false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val note = notes[p1]
        p0.cardView.setOnClickListener(itemListener)
        p0.cardView.tag = p1
        p0.titleView.text = note.title
        p0.excertView.text = note.text
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById(R.id.card_view) as CardView
        val titleView =  cardView.findViewById(R.id.title) as TextView
        val excertView =  cardView.findViewById(R.id.excert)as TextView
    }

}
