package com.example.myapplication

import adapter.NotesAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.AppConstant.DESCRIPTION
import com.example.myapplication.AppConstant.TITLE
import com.example.myapplication.clicklisteners.ItemClickListener
import com.example.myapplication.prefConstant.SHARED_PREFERENCE_NAME
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Notes
import java.util.*

 public class MyNotesActivity : AppCompatActivity() {
    lateinit var fullName: String
   lateinit var fabAddNotes: FloatingActionButton
   lateinit var sharedPreferences: SharedPreferences
   lateinit var recyclerViewNotes: RecyclerView
    var notesList = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindView()
        setupSharedPreference()
        getintentData()
        setupRecyclerView()

        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
               setupDialogBox()
            }

        })
        supportActionBar?.title = fullName
    }

     private fun getintentData() {
         val intent = intent
         if (intent.hasExtra(AppConstant.FULL_NAME)) {
             fullName = intent.getStringExtra(AppConstant.FULL_NAME)
         }
         if (fullName.isEmpty()){
             fullName = sharedPreferences.getString(prefConstant.FULL_NAME,"")?:""
         }
     }

     private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

    }



    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener({ object : View.OnClickListener{
            override fun onClick(v: View?) {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val notes = Notes(title, description)
                    notesList.add(notes)
                } else {
                    Toast.makeText(this@MyNotesActivity, "Title And Description is Mandatory", Toast.LENGTH_SHORT).show()
                }

                dialog.hide()
            }

        }



        })
        dialog.show()


    }

        private fun setupRecyclerView(){
        val itemClickListener: ItemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
        }
        val notesAdapter = NotesAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = notesAdapter
    }
}