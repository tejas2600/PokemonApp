package com.example.pokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class pokemondetail extends AppCompatActivity {
    TextView pokemon_name;
    TextView pokemon_number;
    TextView pokemon_type1;
    TextView pokemon_type2;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemondetail);
        pokemon_name=findViewById(R.id.pokemon_name);
        pokemon_number=findViewById(R.id.pokemon_number);
        pokemon_type1=findViewById(R.id.pokemon_type1);
        pokemon_type2=findViewById(R.id.pokemon_type2);

        String name=getIntent().getStringExtra("name");
        String url=getIntent().getStringExtra("url");
        pokemon_name.setText(name);
        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                  try{
                      pokemon_number.setText(String.format("#%03d",response.getInt("id")));
                      JSONArray types=response.getJSONArray("types");
                      for(int i=0;i<types.length();i++){
                          JSONObject type=types.getJSONObject(i);
                          int slot=type.getInt("slot");
                          String typename=type.getJSONObject("type").getString("name");
                          if(slot==1){
                              pokemon_type1.setText(typename);
                          }
                          else if(slot==2){
                             pokemon_type2.setText(typename) ;
                          }

                      }

                  }
                  catch(JSONException e){

                  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
         requestQueue.add(request);
    }
}
