package com.thevarunshah.pokemondemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    private ArrayList<String> list = new ArrayList<String>(); //the actual list
    private ListView listView = null; //main view of list
    ArrayAdapter<String> listAdapter = null; //how to manage the list

    /*
    This method is where the activity layout is set up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen); //connect the layout file to this activity

        //obtain list view and create new list custom adapter
        listView = (ListView) findViewById(R.id.listview);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        //attach adapter to list view
        listView.setAdapter(listAdapter);

        //fetch button from layout
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_item);
        //add an on click listener for when the user taps on the button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("home screen", "fab clicked"); //how to do logging in android
                Snackbar snackbar = Snackbar.make(v, "it works!", Snackbar.LENGTH_SHORT); //how to show a message at the bottom of the screen
                snackbar.show();

                //how to go from one activity to another
                //start add item activity and wait for result (in onActivityResult(...))
                Intent i = new Intent(HomeScreen.this, NewPokemon.class);
                startActivityForResult(i, 0);
            }
        });
    }

    /*
    This method waits for the user to finish another activity and processes the results
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK && requestCode == 0){

            String item = data.getStringExtra("text"); //fetch data passed from previous activity

            //add item to main list and update view
            list.add(item);
            listAdapter.notifyDataSetChanged();
        }
    }

    /*
    This method sets up the action bar menu options
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //fetch and set actionbar menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
    This method detects when options from the menu are selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.get_pokemons:
                //perform a get-request
                UpdateListTask task = new UpdateListTask();
                task.execute(new String[] { "http://koolaid.ngrok.io/pokemon" });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //how to run an asynchronous task in the background
    private class UpdateListTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String response = "";
            for (String url : urls) {
                HttpURLConnection urlConnection = null;
                try {
                    //make get-reqquest
                    URL _url = new URL(url);
                    urlConnection = (HttpURLConnection) _url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    response = readStream(in);

                    //parse the JSON response
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonArray = json.getJSONArray("pokemon");
                    list.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        list.add(obj.getString("name"));
                    }
                }
                catch(Exception e){

                }
                finally {
                    urlConnection.disconnect();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            //when done with the asynchronous task, update the list view
            if(!result.equals("")){
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    //parsing the response to a string
    private String readStream(InputStream is) {

        try {
            String response = "";
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                response += s;
            }

            return response;
        } catch (IOException e) {
            return "error";
        }
    }
}
