package com.example.myapplication;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

import adapter.NotesAdapter;
import model.Notes;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerViewNotes;
    ArrayList<Notes> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_notes );

        bindView();
        setupSharedPreference();
        getIntentData();


        fabAddNotes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupDialogBox();
            }
        } );

        getSupportActionBar().setTitle( fullName );
    }

    private void setupSharedPreference() {
        sharedPreferences = getSharedPreferences(prefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra( AppConstant.FULL_NAME );
        if(TextUtils.isEmpty(fullName))
            fullName = sharedPreferences.getString(prefConstant.FULL_NAME,"");
    }

    private void bindView() {
        fabAddNotes = findViewById( R.id.fabAddNotes );


    }

    private void setupDialogBox() {
        View view = LayoutInflater.from( MyNotesActivity.this ).inflate( R.layout.add_notes_dialog_layout,null );
        final EditText editTextTitle = view.findViewById( R.id.editTextTitle );
        final EditText editTextDescription = view.findViewById( R.id.editTextDescription );
        Button buttonSubmit = view.findViewById( R.id.buttonSubmit );

        final AlertDialog dialog = new AlertDialog.Builder( this )
                .setView( view )
                .setCancelable( false )
                .create();
        buttonSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                Notes notes = new Notes();
                notes.setTitle(title);
                notes.setDescription(description);
                notesList.add(notes);
                setupRecyclerView();
                Log.d("TAG", String.valueOf(notesList.size()));


                dialog.hide();
            }
        } );
        dialog.show();
    }

    private void setupRecyclerView() {
        NotesAdapter notesAdapter = new NotesAdapter(notesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }
}