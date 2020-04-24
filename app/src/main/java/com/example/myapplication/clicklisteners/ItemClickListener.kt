package com.example.myapplication.clicklisteners

import db.Notes


interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}