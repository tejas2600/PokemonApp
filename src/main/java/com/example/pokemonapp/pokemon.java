package com.example.pokemonapp;

public class pokemon {
    private String name;
    private String url;

    pokemon(String name,String url){
       this.name=name;
       this.url=url;
    }
    public String getName(){
        return name;
    }
    public String getUrl(){
        return url;
    }
}
