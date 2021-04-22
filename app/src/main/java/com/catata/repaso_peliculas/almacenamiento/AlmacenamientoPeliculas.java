package com.catata.repaso_peliculas.almacenamiento;

import android.content.Context;
import android.os.AsyncTask;

import com.catata.repaso_peliculas.model.Pelicula;

import java.util.List;

public abstract class AlmacenamientoPeliculas extends AsyncTask<Object,Object,Object>{

    TaskCompleted listener;

    public enum OPCION {
            GET_ALL,
            ADD,
            REMOVE,
            UPDATE,
            GET_BY_ID
    }

    AlmacenamientoPeliculas(TaskCompleted listener){
        this.listener = listener;
    }

    public abstract List<Pelicula> getAllFilms();

    public abstract Pelicula getFilmById(int id);

    public abstract boolean delFilm(Pelicula p);

    public abstract Pelicula updateFilm(Pelicula p);

    public abstract boolean addFilm(Pelicula p);

    public interface TaskCompleted{
        void onTaskCompletedAdd(boolean res);
        void onTaskCompletedGetByID(Pelicula p);
        void onTaskCompletedDelete(boolean res);
        void onTaskCompletedUpdate(Pelicula p);
        void onTaskCompletedGetAll(List<Pelicula> peliculas);
    }
}
