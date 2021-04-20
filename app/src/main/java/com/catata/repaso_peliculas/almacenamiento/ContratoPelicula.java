package com.catata.repaso_peliculas.almacenamiento;

import android.provider.BaseColumns;

public class ContratoPelicula {
    //Para evitar que nadie la pueda instanciar hacemos el constructor privado
    private ContratoPelicula(){}

    //Creamos una clase interna estática con la información
    public static class PeliculaInfo implements BaseColumns {
        public static final String TABLE_NAME = "peliculas";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_DESCRIPCION = "descripcion";
        public static final String COLUMN_IMAGEN = "imagen";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PeliculaInfo.TABLE_NAME + " (" +
                        PeliculaInfo._ID + " INTEGER PRIMARY KEY," +
                        PeliculaInfo.COLUMN_ID + " NUMBER," +
                        PeliculaInfo.COLUMN_TITULO + " TEXT," +
                        PeliculaInfo.COLUMN_DESCRIPCION + " TEXT," +
                        PeliculaInfo.COLUMN_IMAGEN + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + PeliculaInfo.TABLE_NAME;
    }
}
