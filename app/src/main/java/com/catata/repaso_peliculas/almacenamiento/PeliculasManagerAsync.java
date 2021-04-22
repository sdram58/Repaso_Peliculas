package com.catata.repaso_peliculas.almacenamiento;

import android.content.Context;
import android.os.AsyncTask;

import com.catata.repaso_peliculas.model.Pelicula;

import java.util.List;

public class PeliculasManagerAsync extends AlmacenamientoPeliculas {
    Context c;

    AlmacenamientoPeliculas.OPCION opcion;

    AlmacenamientoPeliculas.TaskCompleted listener;

    public PeliculasManagerAsync(Context ctx, TaskCompleted listener){
        super(listener);
        this.c = ctx;
        this.listener = listener;
    }

    public Context getC() {
        return c;
    }

    public void setC(Context c) {
        this.c = c;
    }

    public OPCION getOpcion() {
        return opcion;
    }

    public void setOpcion(OPCION opcion) {
        this.opcion = opcion;
    }

    public TaskCompleted getListener() {
        return listener;
    }

    public void setListener(TaskCompleted listener) {
        this.listener = listener;
    }

    PeliculasSQLManager peliculasSQLManager;

    @Override
    protected Object doInBackground(Object... objects) {
        this.opcion = (OPCION) objects[0];

        switch (this.opcion){
            case ADD: return addFilm((Pelicula) objects[1] );
            case GET_ALL: return getAllFilms();
            case GET_BY_ID: return getFilmById((Integer) objects[1] );
            case REMOVE: return addFilm((Pelicula) objects[1] );
            case UPDATE: return updateFilm((Pelicula) objects[1] );
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.peliculasSQLManager = new PeliculasSQLManager(this.c);

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        switch (opcion){
            case ADD: listener.onTaskCompletedAdd((Boolean) o); break;
            case GET_ALL: listener.onTaskCompletedGetAll((List<Pelicula>) o); break;
            case GET_BY_ID: listener.onTaskCompletedGetByID((Pelicula) o ); break;
            case REMOVE: listener.onTaskCompletedDelete((Boolean) o ); break;
            case UPDATE: listener.onTaskCompletedUpdate((Pelicula) o ); break;
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
    }

    @Override
    public List<Pelicula> getAllFilms() {
        return this.peliculasSQLManager.getAllFilms();
    }

    @Override
    public Pelicula getFilmById(int id) {
        return this.peliculasSQLManager.getFilmById(id);
    }

    @Override
    public boolean delFilm(Pelicula p) {
        return this.peliculasSQLManager.delFilm(p);
    }

    @Override
    public Pelicula updateFilm(Pelicula p) {
        return null;
    }

    @Override
    public boolean addFilm(Pelicula p) {
        return false;
    }
}
