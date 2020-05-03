package com.example.myapplication.clicklisteners.view

import com.example.myapplication.clicklisteners.adapter.NotesAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.clicklisteners.ItemClickListener
import com.example.myapplication.clicklisteners.NotesApp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import db.Notes

import com.example.myapplication.clicklisteners.utils.AppConstant
import com.example.myapplication.clicklisteners.utils.prefConstant
import com.example.myapplication.clicklisteners.workmanager.MyWorker
import kotlinx.android.synthetic.main.activity_my_notes.*
import java.util.*
import java.util.concurrent.TimeUnit

public class MyNotesActivity : AppCompatActivity() {
    val ADD_NOTES_CODE =100
    var fullName: String?=null
    lateinit var fabAddNotes:FloatingActionButton
    val TAG = "MyNotesActivity"
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    var notesList = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindView()
        setupSharedPreferences()
        getIntentData()
        getDataFromDataBase()
        setupRecyclerView()
        setupWorkManager()
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
               // setupDialogBox()
                val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, ADD_NOTES_CODE)
            }

        })

    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest
                .Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

        WorkManager.getInstance().enqueue(request)
    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()

        notesList.addAll(notesDao.getAll())

    }

    private fun setupDialogBox() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout,null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener( object : View.OnClickListener{
            override fun onClick(v: View?) {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val notes = Notes(title =  title, description = description)
                    notesList.add(notes)
                    addNotesToDb(notes)
                }else{
                    Toast.makeText(this@MyNotesActivity,"Title or Description is mandatory",Toast.LENGTH_SHORT).show()
                }

                dialog.hide()
            }
        })
        dialog.show()
    }

    private fun addNotesToDb(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener = object :ItemClickListener{
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE,notes.title)
                intent.putExtra(AppConstant.DESCRIPTION,notes.description)
                startActivity(intent)
            }

            override fun onUpdate(notes: Notes) {
               // to update    need to work on it
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }
        }
        val notesAdapter = NotesAdapter(notesList,itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation=RecyclerView.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter=notesAdapter
    }
    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME)?:""
        }
        if (fullName ==null){
            fullName = sharedPreferences.getString(prefConstant.FULL_NAME, "")?:""
        }
    }
    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(prefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerView = findViewById(R.id.recyclerViewNotes)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ADD_NOTES_CODE) {
            val title = data?.getStringExtra(AppConstant.TITLE)
            val description = data?.getStringExtra(AppConstant.DESCRIPTION)
            val imagePath = data?.getStringExtra(AppConstant.IMAGE_PATH)

            val notes = Notes(title = title!!, description = description!!, imagePath = imagePath!!, isTaskCompleted = false)
            addNotesToDb(notes)
           notesList.add(notes)
            recyclerViewNotes.adapter?.notifyItemChanged(notesList.size - 1)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        Log.d(TAG, "BEFORE BEFORE")
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.blog) {
            Log.d(TAG, "Clicked Sucess")
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item!!)
    }
}