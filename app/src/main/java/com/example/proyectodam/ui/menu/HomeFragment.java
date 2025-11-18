package com.example.proyectodam.ui.menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;
import com.example.proyectodam.data.GameResponse;
import com.example.proyectodam.network.RAWGApi;
import com.example.proyectodam.ui.adapters.GameAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private GameAdapter adapter;
    private RAWGApi api;

    public static final String API_KEY = "c94644588fca4e12a4258d61a72d476b";
    private static final String BASE_URL = "https://api.rawg.io/api/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Carrusel de géneros (solo visible en home)
        LinearLayout genreContainer = view.findViewById(R.id.genreContainer);
        for (int i = 0; i < genreContainer.getChildCount(); i++) {
            View child = genreContainer.getChildAt(i);
            child.setOnClickListener(v -> {
                for (int j = 0; j < genreContainer.getChildCount(); j++)
                    genreContainer.getChildAt(j).setSelected(false);
                v.setSelected(true);
                String genre = ((TextView) v).getText().toString();
                Toast.makeText(getContext(), "Seleccionado: " + genre, Toast.LENGTH_SHORT).show();
            });
        }

        // Inicializar Retrofit y API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RAWGApi.class);

        // Cargar juegos populares
        cargarJuegosPopulares();

        return view;
    }

    private void cargarJuegosPopulares() {
        api.getGames(API_KEY).enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new GameAdapter(getContext(), response.body().getResults());
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("RAWG", "Respuesta inválida del servidor, usando fallback");
                    cargarFallback();
                }
            }

            @Override
            public void onFailure(Call<GameResponse> call, Throwable t) {
                Log.e("RAWG", "Error en la API: " + t.getMessage());
                Toast.makeText(getContext(), "No se pudieron cargar los juegos, mostrando ejemplo", Toast.LENGTH_SHORT).show();
                cargarFallback();
            }
        });
    }

    // carga juegos de prueba en caso de perder la conexion
    private void cargarFallback() {
        List<Game> juegosDePrueba = new ArrayList<>();


        juegosDePrueba.add(new Gson().fromJson("{\"name\":\"Juego de prueba 1\"}", Game.class));
        juegosDePrueba.add(new Gson().fromJson("{\"name\":\"Juego de prueba 2\"}", Game.class));
        juegosDePrueba.add(new Gson().fromJson("{\"name\":\"Juego de prueba 3\"}", Game.class));

        adapter = new GameAdapter(getContext(), juegosDePrueba);
        recyclerView.setAdapter(adapter);
    }
}


