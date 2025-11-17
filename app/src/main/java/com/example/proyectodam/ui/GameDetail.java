package com.example.proyectodam.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectodam.R;
import com.example.proyectodam.ui.adapters.GameDetailFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class GameDetail extends AppCompatActivity {

    private int gameId;


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







