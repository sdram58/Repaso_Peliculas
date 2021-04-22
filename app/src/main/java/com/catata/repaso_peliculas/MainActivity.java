package com.catata.repaso_peliculas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.catata.repaso_peliculas.almacenamiento.AlmacenamientoPeliculas;
import com.catata.repaso_peliculas.almacenamiento.PeliculasManagerAsync;
import com.catata.repaso_peliculas.almacenamiento.PeliculasSQLManager;
import com.catata.repaso_peliculas.model.GestorPeliculas;
import com.catata.repaso_peliculas.model.Pelicula;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String DEBUG_TAG = MainActivity.class.getName();

    RecyclerView lista;
    MyAdapater myAdapater;

    LinearLayout contenedor;

    Pelicula curPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();
    }

    private void setUpViews() {

        lista = (RecyclerView) findViewById(R.id.lista);
        contenedor = (LinearLayout)findViewById(R.id.contenedor);

        if(checkTwoPanels()){
            lista.setLayoutManager(new LinearLayoutManager(this));
        }else{
            lista.setLayoutManager(new GridLayoutManager(this,2));

            contenedor.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
                public void onSwipeTop() {
                    //Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                }
                public void onSwipeRight() {
                    //Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                    curPelicula = GestorPeliculas.getNextPelicula(curPelicula);
                    showPelicula();
                }
                public void onSwipeLeft() {
                    //Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                    curPelicula = GestorPeliculas.getPrevPelicula(curPelicula);
                    showPelicula();
                }
                public void onSwipeBottom() {
                    //Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                }

            });
        }

        myAdapater = new MyAdapater();
        lista.setAdapter(myAdapater);





    }


    private boolean checkTwoPanels(){
        return findViewById(R.id.checkTwoPanels)!=null;
    }

    @Override
    public void onBackPressed() {
        if(!checkTwoPanels()){
            if(((LinearLayout)findViewById(R.id.contenedor)).getVisibility()==View.VISIBLE){
                ((LinearLayout)findViewById(R.id.contenedor)).setVisibility(View.GONE);
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }



    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //if (event.getAction() == MotionEvent.sw)
        return false;
    }

    class MyAdapater extends RecyclerView.Adapter<MyAdapater.ViewHolder> implements View.OnClickListener {

        List<Pelicula> peliculas;
        AlmacenamientoPeliculas.TaskCompleted listener;

        AlmacenamientoPeliculas almacenamientoPeliculas;
        public MyAdapater() {
            super();
            this.peliculas = new ArrayList<Pelicula>();
            listener = new AlmacenamientoPeliculas.TaskCompleted() {

                @Override
                public void onTaskCompletedAdd(boolean res) {
                    if(res) Log.i(DEBUG_TAG,"Added!");
                    else Log.i(DEBUG_TAG,"Error Adding!");
                }

                @Override
                public void onTaskCompletedGetByID(Pelicula p) {

                }

                @Override
                public void onTaskCompletedDelete(boolean res) {
                    if(res) notifyDataSetChanged();
                    else Log.i(DEBUG_TAG,"Error Updating");
                }

                @Override
                public void onTaskCompletedUpdate(Pelicula p) {

                }

                @Override
                public void onTaskCompletedGetAll(List<Pelicula> p) {


                    if(p.size()<=0){
                        updatePeliculas(GestorPeliculas.loadPeliculas(MainActivity.this));

                        for (Pelicula peli: peliculas) {
                            almacenamientoPeliculas.execute(AlmacenamientoPeliculas.OPCION.ADD,peli);
                        }
                    }else{
                        updatePeliculas(p);
                    }
                }
            };

            almacenamientoPeliculas = new PeliculasManagerAsync(MainActivity.this, listener);

            almacenamientoPeliculas.execute(AlmacenamientoPeliculas.OPCION.GET_ALL);


        }

        public void updatePeliculas(List<Pelicula> mPeliculas){
            if(this.peliculas!=null) this.peliculas.clear();
            else this.peliculas = new ArrayList<Pelicula>();

            for (Pelicula p: mPeliculas) {
                this.peliculas.add(p);
            }

            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            if (checkTwoPanels()) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_landscape, parent, false);
            } else {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_portrait, parent, false);
            }
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Pelicula p = this.peliculas.get(position);
            holder.itemView.setTag(p);
            holder.itemView.setOnClickListener(this);

            holder.titulo.setText(p.getTitulo());

            Glide.with(MainActivity.this).load(p.getImagen()).into(holder.portada);
        }


        @Override
        public int getItemCount() {
            return this.peliculas.size();
        }

        @Override
        public void onClick(View v) {
            curPelicula = (Pelicula) v.getTag();

            showPelicula();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView portada;
            TextView titulo;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                portada = (ImageView) itemView.findViewById(R.id.ivFoto_portrait);
                titulo = (TextView) itemView.findViewById(R.id.tituloPortrait);
            }
        }
    }

    private void showPelicula() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ((LinearLayout) findViewById(R.id.contenedor)).setVisibility(View.VISIBLE);
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor, DetailFragment.newInstance(curPelicula.getTitulo(), curPelicula.getDescripcion(), curPelicula.getImagen()))
                .addToBackStack(null)
                .commit();
    }
}