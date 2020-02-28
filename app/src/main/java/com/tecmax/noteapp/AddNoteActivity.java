package com.tecmax.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class AddNoteActivity extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText addNote = findViewById(R.id.edit_note); //getting the edit text
        Intent intent = getIntent(); //getting the intent
         noteId = intent.getIntExtra("noteId",-1); //making a note id
        //we will get errors in the app because of -1 as its not a valid index
        if(noteId != -1) {
            addNote.setText(MainActivity.notesList.get(noteId));
        } else {
//            Toast

            MainActivity.notesList.add("");
            noteId = MainActivity.notesList.size() - 1;
        }

        addNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                MainActivity.notesList.set(noteId, String.valueOf(charSequence));
                MainActivity.notesAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tecmax.noteapp", Context.MODE_PRIVATE);
               //we are using hashset because we r not dealing with the ordered list
                //we are not limited here by the order and we don't mind what the order is
                HashSet<String> hashSet = new HashSet<>(MainActivity.notesList);
                sharedPreferences.edit().putStringSet("notesList",hashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
