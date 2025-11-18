package com.example.proyectodam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.proyectodam.R;
import com.example.proyectodam.ui.adapters.GameDetailFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GameDetail extends AppCompatActivity {

    private int gameId;
    private TextView tabHome, tabBiblioteca, tabPerfil, tabBuscar, tabInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        activarModoInmersivo();
        initFooterTabs();

        // Obtener gameId desde intent
        gameId = getIntent().getIntExtra("gameId", -1);

        // ViewPager + TabLayout
        ViewPager2 viewPager = findViewById(R.id.gameViewPager);
        TabLayout tabLayout = findViewById(R.id.gameTabs);

        GameDetailFragmentAdapter adapter = new GameDetailFragmentAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, pos) -> {
            switch (pos) {
                case 0: tab.setText("Info"); break;
                case 1: tab.setText("Noticias"); break;
                case 2: tab.setText("Tiendas"); break;
                case 3: tab.setText("Comunidad"); break;
            }
        }).attach();

        aplicarColoresTabs(tabLayout);
    }

    //Este metodo es para evitar que se vea la hora, bateria y demás, y dejar espacio a la interfaz
    private void activarModoInmersivo() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    private void initFooterTabs() {
        tabHome = findViewById(R.id.tabHomeDetail);
        tabBiblioteca = findViewById(R.id.tabBibliotecaDetail);
        tabPerfil = findViewById(R.id.tabPerfilDetail);
        tabBuscar = findViewById(R.id.tabBuscarDetail);
        tabInfo = findViewById(R.id.tabInfoDetail);


        View.OnClickListener listener = v -> {
            Intent intent = new Intent(this, MainActivity.class);

            if (v == tabHome) intent.putExtra("fragment", "Home");
            else if (v == tabBiblioteca) intent.putExtra("fragment", "Biblioteca");
            else if (v == tabPerfil) intent.putExtra("fragment", "Perfil");
            else if (v == tabBuscar) intent.putExtra("fragment", "Buscar");
            else if (v == tabInfo) intent.putExtra("fragment", "Info");

            startActivity(intent);
        };

        tabHome.setOnClickListener(listener);
        tabBiblioteca.setOnClickListener(listener);
        tabPerfil.setOnClickListener(listener);
        tabBuscar.setOnClickListener(listener);
        tabInfo.setOnClickListener(listener);
    }

    private void aplicarColoresTabs(TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(0xFF8E8E8E);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(0xFF2A2A2A);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public int getGameId() {
        return gameId;
    }
}









