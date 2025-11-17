package com.example.proyectodam.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectodam.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class GameDetail extends AppCompatActivity {

    private int gameId;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        // Sistema immersivo
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        gameId = getIntent().getIntExtra("gameId", -1);

        ViewPager2 viewPager = findViewById(R.id.gameViewPager);
        TabLayout tabLayout = findViewById(R.id.gameTabs);

        GameDetailFragmentAdapter adapter = new GameDetailFragmentAdapter(this); // solo this
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, pos) -> {
            switch (pos) {
                case 0: tab.setText("Info"); break;
                case 1: tab.setText("Noticias"); break;
                case 2: tab.setText("Tiendas"); break;
                case 3: tab.setText("Comunidad"); break;
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(0xFF8E8E8E); // fondo gris
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(0xFF2A2A2A); // fondo transparente
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        LinearLayout searchPanel = findViewById(R.id.searchPanel);
        if (searchPanel != null) searchPanel.setVisibility(View.GONE);

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
    }

    public int getGameId() {
        return gameId;
    }
}







