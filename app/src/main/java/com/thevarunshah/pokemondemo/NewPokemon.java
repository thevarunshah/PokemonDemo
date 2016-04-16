package com.thevarunshah.pokemondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pokemon);

        Button bttn = (Button) findViewById(R.id.add_button);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittext object of the item entered
                EditText text = (EditText) findViewById(R.id.pokemon_name);

                //create new intent to put the item in
                Intent data = new Intent();
                data.putExtra("text", text.getText().toString());

                //attach data to return call and go back to main view
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
