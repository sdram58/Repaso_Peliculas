package com.catata.repaso_peliculas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mUrlPortada;
    private String mTitulo;
    private String mDescripcion;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param titulo Parameter 1.
     * @param descripcion Parameter 2.
     * @param urlPortada Parameter 3.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String titulo, String descripcion, String urlPortada) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, titulo);
        args.putString(ARG_PARAM2, descripcion);
        args.putString(ARG_PARAM3, urlPortada);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitulo = getArguments().getString(ARG_PARAM1);
            mDescripcion = getArguments().getString(ARG_PARAM2);
            mUrlPortada = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView tvTitulo = (TextView)v.findViewById(R.id.tituloFragment);
        TextView tvDescripcion = (TextView)v.findViewById(R.id.tvDescripcionFragment);
        ImageView ivFoto = (ImageView)v.findViewById(R.id.ivFotoFragment);

        tvTitulo.setText(mTitulo);
        tvDescripcion.setText(mDescripcion);
        Glide.with(getContext()).load(mUrlPortada).into(ivFoto);

        return v;
    }
}