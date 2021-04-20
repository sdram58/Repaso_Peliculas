package com.catata.repaso_peliculas.model;

import android.content.Context;

import com.catata.repaso_peliculas.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorPeliculas {
    public static List<Pelicula> PELICULAS = new ArrayList<Pelicula>();

    public static List<Pelicula> loadPeliculas(Context c){
        InputStream raw = c.getResources().openRawResource(R.raw.peliculas);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Type listType = new TypeToken<List<Pelicula>>() {}.getType(); Gson gson = new Gson();
        PELICULAS = gson.fromJson(rd, listType);
        return  PELICULAS;
    }

    public static Pelicula getPeliculaById(int mId,Context c){
        if(PELICULAS.size()<=0) loadPeliculas(c);

        return null;
    }



    public static Pelicula getPrevPelicula(Pelicula p){
        int i  = PELICULAS.indexOf(p);
        i--;
        if(i<0) i=PELICULAS.size()-1;
        return PELICULAS.get(i);
    }

    public static Pelicula getNextPelicula(Pelicula p){
        int i  = PELICULAS.indexOf(p);
        i++;
        if(i>=PELICULAS.size()) i=0;
        return PELICULAS.get(i);
    }
}
