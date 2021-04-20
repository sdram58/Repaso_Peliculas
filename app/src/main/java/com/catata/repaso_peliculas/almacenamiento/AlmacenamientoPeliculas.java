package com.catata.repaso_peliculas.almacenamiento;

import com.catata.repaso_peliculas.model.Pelicula;

import java.util.List;

public interface AlmacenamientoPeliculas {

    public List<Pelicula> getAllFilms();

    public Pelicula getFilmById(int id);

    public boolean delFilm(Pelicula p);

    public Pelicula updateFilm(Pelicula p);

    public boolean addFilm(Pelicula p);
}
