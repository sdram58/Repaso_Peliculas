package com.catata.repaso_peliculas.almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.BaseColumns;

import com.catata.repaso_peliculas.model.Pelicula;

import java.util.ArrayList;
import java.util.List;

public class PeliculasSQLManager {

    public static PeliculasDBHelper peliculasDBHelper;

    Context c;

    public PeliculasSQLManager(Context c){
        this.c = c;
    }

    public PeliculasDBHelper getInstance(){
        if(peliculasDBHelper == null){
            peliculasDBHelper = new PeliculasDBHelper(c);
        }

        return peliculasDBHelper;
    }

    public void cerrar(){
        PeliculasDBHelper dbHelper = getInstance();
        dbHelper.close();
    }



    public List<Pelicula> getAllFilms() {
        PeliculasDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Seleccionamos los campos que queremos recupear
        String[] projection = {
                BaseColumns._ID,
                ContratoPelicula.PeliculaInfo.COLUMN_ID,
                ContratoPelicula.PeliculaInfo.COLUMN_TITULO,
                ContratoPelicula.PeliculaInfo.COLUMN_DESCRIPCION,
                ContratoPelicula.PeliculaInfo.COLUMN_IMAGEN
        };

        // Elegimos el orden, en este caso por apellidos ascendente
        String sortOrder =
                ContratoPelicula.PeliculaInfo.COLUMN_ID + " ASC";

        //Lanzamos la consulta
        Cursor cursor = db.query(
                ContratoPelicula.PeliculaInfo.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        //Leemos el cursor
        List<Pelicula> peliculas = new ArrayList<Pelicula>();

        while(cursor.moveToNext()) {
            //Buscamos todas por el _ID
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo._ID));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_TITULO));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_DESCRIPCION));
            String imagen = cursor.getString(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_IMAGEN));

            peliculas.add(new Pelicula(id,titulo,descripcion,imagen));
        }
        cursor.close();

        return peliculas;
    }

    public Pelicula getFilmById(int id) {
        PeliculasDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Seleccionamos los campos que queremos recupear
        String[] projection = {
                BaseColumns._ID,
                ContratoPelicula.PeliculaInfo.COLUMN_ID,
                ContratoPelicula.PeliculaInfo.COLUMN_TITULO,
                ContratoPelicula.PeliculaInfo.COLUMN_DESCRIPCION,
                ContratoPelicula.PeliculaInfo.COLUMN_IMAGEN
        };

        // Filtramos los resultados donde el nombre = name (parámetro)
        String where = ContratoPelicula.PeliculaInfo.COLUMN_ID + " = ?";
        String[] selectionArgs = { ""+id };

        // Elegimos el orden, en este caso por apellidos ascendente
        String sortOrder =
                ContratoPelicula.PeliculaInfo.COLUMN_ID + " ASC";

        //Lanzamos la consulta
        Cursor cursor = db.query(
                ContratoPelicula.PeliculaInfo.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                where,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        Pelicula p = null;

        if(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo._ID));
            int mId = cursor.getInt(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_ID));
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_TITULO));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_DESCRIPCION));
            String imagen = cursor.getString(cursor.getColumnIndexOrThrow(ContratoPelicula.PeliculaInfo.COLUMN_IMAGEN));

            p = new Pelicula(mId, titulo, descripcion, imagen);
        }

        cursor.close();

        return p;


    }

    public boolean delFilm(Pelicula p) {
        PeliculasDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Definimos el Where de la consulta
        String selection = ContratoPelicula.PeliculaInfo.COLUMN_ID + " = ?";
        // Vinculamos el valor al que corresponda, como la consulta solo tiene un ?, solo habrá un argunmento.
        String[] selectionArgs = { String.valueOf(p.getId()) };
        // ejecutamos la orden que nos devuelve las filas eliminadas
        int a = db.delete(
                ContratoPelicula.PeliculaInfo.TABLE_NAME,
                selection,
                selectionArgs);
        return a > 0;
    }

     public Pelicula updateFilm(Pelicula p) {
        PeliculasDBHelper dbHelper = getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Elegimos el nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(ContratoPelicula.PeliculaInfo.COLUMN_ID, p.getId());
        values.put(ContratoPelicula.PeliculaInfo.COLUMN_TITULO, p.getTitulo());
        values.put(ContratoPelicula.PeliculaInfo.COLUMN_DESCRIPCION, p.getDescripcion());
        values.put(ContratoPelicula.PeliculaInfo.COLUMN_IMAGEN, p.getImagen());

        // Qué fila actualizamos. La que coincida con el ID
        String selection = ContratoPelicula.PeliculaInfo.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(p.getId()) };

        int numAfectedRows= db.update(
                ContratoPelicula.PeliculaInfo.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(numAfectedRows>0){
            return p;
        }else{
            return null;
        }
    }

    public boolean addFilm(Pelicula p) {
        PeliculasDBHelper dbHelper = getInstance();

        Pelicula pelicula = getFilmById(p.getId());

        if(pelicula!=null){
            updateFilm(p);
            return false;
        }else {

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ContratoPelicula.PeliculaInfo.COLUMN_ID, p.getId());
            values.put(ContratoPelicula.PeliculaInfo.COLUMN_TITULO, p.getTitulo());
            values.put(ContratoPelicula.PeliculaInfo.COLUMN_DESCRIPCION, p.getDescripcion());
            values.put(ContratoPelicula.PeliculaInfo.COLUMN_IMAGEN, p.getImagen());

            long newRowId = db.insert(ContratoPelicula.PeliculaInfo.TABLE_NAME, null, values);

            return newRowId > 0;
        }
    }

}
