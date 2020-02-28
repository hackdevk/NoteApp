package com.tecmax.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList <String>notesList = new ArrayList<>();
    static ArrayAdapter notesAdapter;
    SharedPreferences sharedPreferences ;
//    = getApplicationContext().getSharedPreferences("package com.tecmax.noteapp", MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        notesList.add("Example note"); //for setting the screen while opening the app
        ListView noteListView = findViewById(R.id.note_list);    //getting the list view
//        notesList.add("Example note");
        notesAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, notesList);  //making the array adapter
        noteListView.setAdapter(notesAdapter); //setting the adapter for the list view

        sharedPreferences = getApplicationContext().getSharedPreferences("com.tecmax.noteapp", MODE_PRIVATE);
        HashSet<String> hashSet = (HashSet<String>) sharedPreferences.getStringSet("notesList",null);
        if(hashSet == null) {
            notesList.add("Example note");
        } else {
            notesList = new ArrayList(hashSet);
        }


        //making a listener for the list view item
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent addNoteIntent = new Intent(MainActivity.this,AddNoteActivity.class);
                addNoteIntent.putExtra("noteId",position); //for the header for the every intent
                startActivity(addNoteIntent); //starting the intent
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //the id for the item to be deleted which means the item which is long pressed
                final int itemToDelete = position;
                //dialog window for the long press delete dialog
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete the note?")
                        .setMessage("Do you want to delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notesList.remove(itemToDelete);
                                notesAdapter.notifyDataSetChanged();

                                //we are using hashset because we r not dealing with the ordered list
                                //we are not limited here by the order
                                HashSet<String> hashSet = new HashSet<>(MainActivity.notesList);
                                sharedPreferences.edit().putStringSet("notesList",hashSet).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_note) {
            Intent addNoteIntent = new Intent(getApplicationContext() ,AddNoteActivity.class);
            startActivity(addNoteIntent); //starting the intent

            return true;
        }
//        return super.onOptionsItemSelected(item);
        return false;
    }

}
