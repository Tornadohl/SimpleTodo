package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static com.codepath.simpletodo.MainActivity.ITEM_POSITION;
import static com.codepath.simpletodo.MainActivity.ITEM_TEXT;

public class EditItemActivity extends AppCompatActivity {

    // Track edit text
    EditText etItemText;
    // position of edited item in list
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        // resolve edit text from layout
        etItemText = (EditText) findViewById(R.id.etItemText);
        // set edit text value from intent extra
        etItemText.setText(getIntent().getStringExtra(ITEM_TEXT));
        // Update position from item extra
        position = getIntent().getIntExtra(ITEM_POSITION, 0);
        //Update the title bar of the activity
        getSupportActionBar().setTitle("Edit item");
    }
    // handler for save button
    public  void onSaveItem(View v) {
        // prepare new intent for result
        Intent i = new Intent();
        // Pass updated item text as extra
        i.putExtra(ITEM_TEXT, etItemText.getText().toString());
        // Pass original position as extra
        i.putExtra(ITEM_POSITION, position);
        // Set intent as the result of the activity
        setResult(RESULT_OK, i);
        // Close the activity and redirect to main
        finish();
    }
}
