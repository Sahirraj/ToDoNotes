package com.example.myapplication.clicklisteners

import android.app.Application
import db.NotesDatabase

class NotesApp :Application(){
    override fun onCreate() {
        super.onCreate()
    }
    fun getNotesDb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }
}