package com.example.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText title;
    EditText note;
    TextView count;
    ListView notes;

    DBHelper helper;
    SQLiteDatabase notesDB;

    SimpleCursorAdapter adapter;

    String[] notes_fields;

    int[] views = { R.id.id, R.id.title, R.id.note };
    Cursor cur_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);
        notesDB = helper.getWritableDatabase();

        notes = findViewById(R.id.notes);
        title = findViewById(R.id.title);
        note = findViewById(R.id.note);

        cur_notes = notesDB.rawQuery("SELECT * FROM notes", null);
        notes_fields = cur_notes.getColumnNames();

        adapter = new SimpleCursorAdapter(this, R.layout.notes_item, cur_notes, notes_fields, views, 0 );
        notes.setAdapter(adapter);
    }

    public void onClick(View v) {
        count = findViewById(R.id.count);
        ContentValues cv = new ContentValues();
        cv.put("title", title.getText().toString());
        cv.put("note", note.getText().toString());
        notesDB.insert(helper.TABLE_NAME, null, cv);

        cur_notes = notesDB.rawQuery("SELECT * FROM notes", null);
        adapter = new SimpleCursorAdapter(this, R.layout.notes_item, cur_notes, notes_fields, views, 0);
        notes.setAdapter(adapter);

        count.setText("Total number of Items are: " + notes.getAdapter().getCount());
        title.setText("");
        note.setText("");
    }
}