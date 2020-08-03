package com.example.pokemonapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class pokemonAdapter extends RecyclerView.Adapter<pokemonAdapter.pokemonViewHolder> {
    public class pokemonViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout containerView;
        public TextView pokenmonname;

        private pokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            containerView=itemView.findViewById(R.id.row);
            pokenmonname=itemView.findViewById(R.id.textviewname);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pokemon current=(pokemon)containerView.getTag();
                    Intent intent=new Intent(v.getContext(),pokemondetail.class);
                    intent.putExtra("name",current.getName());
                    intent.putExtra("url",current.getUrl());
                    v.getContext().startActivity(intent);
                }
            });

        }

    }

    @NonNull
    @Override
    public pokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row,parent,false);
        return (new pokemonViewHolder(view));
    }
    private List<pokemon> pokemonlist=new ArrayList<>();
    private RequestQueue requestQueue;

    pokemonAdapter(Context context){
        requestQueue=Volley.newRequestQueue(context);
        loadpokemon();
    }

    public void loadpokemon(){
        String url="https://pokeapi.co/api/v2/pokemon/?limit151";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray results= response.getJSONArray("results");
                    for(int i=0;i<results.length();i++){
                        JSONObject result=results.getJSONObject(i);
                        pokemonlist.add(new pokemon(result.getString("name"),result.getString("url")));
                    }
                    notifyDataSetChanged();
                }
                catch(JSONException e){
                 Log.e("pokemon","json error",e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pokemon","url error");
            }
        });


        requestQueue.add(request);



    }

    @Override
    public void onBindViewHolder(@NonNull pokemonViewHolder holder, int position) {
        pokemon current=pokemonlist.get(position);
        holder.pokenmonname.setText(current.getName());
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return pokemonlist.size();
    }


}
