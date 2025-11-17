package com.example.proyectodam.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;
import com.example.proyectodam.data.GameResponse;
import com.example.proyectodam.network.RAWGApi;
import com.example.proyectodam.ui.adapters.GameAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView searchResultsRecycler;
    private GameAdapter adapter;
    private SearchView searchView;

    public static final String API_KEY = "c94644588fca4e12a4258d61a72d476b";
    private static final String BASE_URL = "https://api.rawg.io/api/";

    private RAWGApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);

        // Sistema immersivo
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar RecyclerViews
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchResultsRecycler = findViewById(R.id.searchResultsRecycler);
        searchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar SearchView
        searchView = findViewById(R.id.searchViewPopup);

        // Panel de búsqueda
        LinearLayout searchPanel = findViewById(R.id.searchPanel);
        if (searchPanel != null) searchPanel.setVisibility(View.GONE);

        // Selector de géneros
        LinearLayout genreContainer = findViewById(R.id.genreContainer);
        for (int i = 0; i < genreContainer.getChildCount(); i++) {
            View child = genreContainer.getChildAt(i);
            child.setOnClickListener(v -> {
                for (int j = 0; j < genreContainer.getChildCount(); j++) {
                    genreContainer.getChildAt(j).setSelected(false);
                }
                v.setSelected(true);
                String genre = ((TextView) v).getText().toString();
                Toast.makeText(this, "Seleccionado: " + genre, Toast.LENGTH_SHORT).show();
            });
        }

        // Tabs inferiores
        TextView tabJuegos = findViewById(R.id.tabJuegos);
        TextView tabBiblioteca = findViewById(R.id.tabBiblioteca);
        TextView tabPerfil = findViewById(R.id.tabPerfil);
        TextView tabBuscar = findViewById(R.id.tabBuscar);
        TextView tabInfo = findViewById(R.id.tabInfo);
        TextView headerTitle = findViewById(R.id.header);

        tabJuegos.setOnClickListener(v -> {
            headerTitle.setText("Juegos");
            recyclerView.setVisibility(View.VISIBLE);
            if (searchPanel != null) searchPanel.setVisibility(View.GONE);
        });

        tabBiblioteca.setOnClickListener(v -> {
            headerTitle.setText("Biblioteca");
            recyclerView.setVisibility(View.GONE);
            if (searchPanel != null) searchPanel.setVisibility(View.GONE);
        });

        tabPerfil.setOnClickListener(v -> {
            headerTitle.setText("Perfil");
            recyclerView.setVisibility(View.GONE);
            if (searchPanel != null) searchPanel.setVisibility(View.GONE);
        });

        tabBuscar.setOnClickListener(v -> {
            headerTitle.setText("Buscar");
            recyclerView.setVisibility(View.GONE);
            if (searchPanel != null) searchPanel.setVisibility(View.VISIBLE);
        });

        tabInfo.setOnClickListener(v -> {
            headerTitle.setText("Info");
            recyclerView.setVisibility(View.GONE);
            if (searchPanel != null) searchPanel.setVisibility(View.GONE);
        });


        // Crear instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RAWGApi.class);

        // Cargar juegos populares
        cargarJuegosPopulares();

        // Listener del buscador
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    buscarJuegos(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    private void cargarJuegosPopulares() {
        api.getGames(API_KEY).enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> games = response.body().getResults();
                    adapter = new GameAdapter(MainActivity.this, games);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GameResponse> call, Throwable t) {
                Log.e("RAWG", "Error: " + t.getMessage());
            }
        });
    }

    private void buscarJuegos(String query) {
        api.searchGames(API_KEY, query).enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Game> juegos = response.body().getResults();
                    GameAdapter adapter = new GameAdapter(MainActivity.this, juegos);
                    searchResultsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GameResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la búsqueda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
