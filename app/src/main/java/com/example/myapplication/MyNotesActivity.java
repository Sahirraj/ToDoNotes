package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {
    String fullName;
    FloatingActionButton fabAddNotes;
    TextView textViewTitle,textViewDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        fabAddNotes = findViewById(R.id.fabAddNotes);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textviewDescription);

       Intent intent = getIntent();

        fullName =  intent.getStringExtra("full_name");

        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupDialogBox();
            }
        });

        getSupportActionBar().setTitle(fullName);
    }

    private void setupDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout,null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        //dialog creation
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String desc = editTextDescription.getText().toString();
              //  Log.d("MyNotesActivity",title);
               // Log.d("MyNotesActivity",desc);
            }
        });
        dialog.show();

    }
}