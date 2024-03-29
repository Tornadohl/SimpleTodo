

package com.codepath.simpletodo;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {



    // a numeric code to identify the edit activity

    public final static int EDIT_REQUEST_CODE = 20;

    // keys used for passing data between activities

    public  final static String ITEM_TEXT ="itemText";

    public final static String ITEM_POSITION = "itemPosition";





    ArrayList<String> items;

    ArrayAdapter<String> itemsAdapter;

    ListView Lvitems;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        readItems();

        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items );

        Lvitems = (ListView) findViewById(R.id.Lvitems);

        Lvitems.setAdapter(itemsAdapter);



        // mock data

        //items.add("First item");

        //items.add("Second item");



        setupListViewListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<String> templist = new ArrayList<>();
                for(String temp :items){
                    String newText = new String();
                    if (temp.toLowerCase().contains(newText.toLowerCase())){
                        templist.add(temp);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, templist);
                Lvitems.setAdapter(itemsAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onAddItem(View v) {

        EditText etItem = (EditText) findViewById(R.id.etItem);

        String itemText = etItem.getText().toString();

        itemsAdapter.add(itemText);

        etItem.setText("");

        writeItems();

        Toast.makeText(getApplicationContext(), "item added to list" , Toast.LENGTH_SHORT).show();



    }



    private void setupListViewListener () {

        Log.i("MainActivity", "Setting up listener on list view");

        Lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {



            @Override

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("MainActivity","Item removed from list" + position);

                items.remove(position);

                itemsAdapter.notifyDataSetChanged();

                writeItems();

                return true;

            }

        });



        // Set up Item Listener for edit (regular click)

        Lvitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // create the new activity

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                // pass the data being edited

                i.putExtra(ITEM_TEXT, items.get(position));

                i.putExtra(ITEM_POSITION, position);

                // display the activity

                startActivityForResult(i,EDIT_REQUEST_CODE);

            }

        });

    }



    //handle results from edit activity





    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // if the edit activity completed ok

        if (resultCode == RESULT_OK && requestCode ==EDIT_REQUEST_CODE){

            // Extract updated item text from result intent extras

            String updatedItem = data.getExtras().getString(ITEM_TEXT);

            // Extract original position of edited item

            int position = data.getExtras().getInt(ITEM_POSITION);

            //Update the model with the new item text at the edited position

            items.set(position, updatedItem);

            // notify the adapter that the model changed

            itemsAdapter.notifyDataSetChanged();

            // Persist the changed model

            writeItems();

            // Notify the user that operation completed ok

            Toast.makeText(this, "item updated successfully",Toast.LENGTH_SHORT).show();

        }

    }



    private File getDataFile(){

        return new File(getFilesDir(), "todo.txt");

    }

    private void readItems(){

        try {

            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));

        } catch (IOException e) {

            Log.e("MainActivity", "Error reading file", e);

            items = new ArrayList<>();

        }

    }

    private void writeItems(){

        try {

            FileUtils.writeLines(getDataFile(), items);

        } catch (IOException e) {

            Log.e("MainActivity", "Error writing file", e);

        }

    }

}