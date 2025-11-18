package com.example.proyectodam.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.proyectodam.R;
import com.example.proyectodam.ui.menu.BibliotecaFragment;
import com.example.proyectodam.ui.menu.BuscarFragment;
import com.example.proyectodam.ui.menu.HomeFragment;
import com.example.proyectodam.ui.menu.InfoFragment;
import com.example.proyectodam.ui.menu.PerfilFragment;

public class MainActivity extends AppCompatActivity {

    private TextView header;
    private TextView tabHome, tabBiblioteca, tabPerfil, tabBuscar, tabInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sistema inmersivo
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        header = findViewById(R.id.header);

        tabHome = findViewById(R.id.tabHome);
        tabBiblioteca = findViewById(R.id.tabBiblioteca);
        tabPerfil = findViewById(R.id.tabPerfil);
        tabBuscar = findViewById(R.id.tabBuscar);
        tabInfo = findViewById(R.id.tabInfo);

        // Cargar HomeFragment por defecto
        cambiarFragment(new HomeFragment(), "Home");

        tabHome.setOnClickListener(v -> cambiarFragment(new HomeFragment(), "Home"));
        tabBiblioteca.setOnClickListener(v -> cambiarFragment(new BibliotecaFragment(), "Biblioteca"));
        tabPerfil.setOnClickListener(v -> cambiarFragment(new PerfilFragment(), "Perfil"));
        tabBuscar.setOnClickListener(v -> cambiarFragment(new BuscarFragment(), "Buscar"));
        tabInfo.setOnClickListener(v -> cambiarFragment(new InfoFragment(), "Info"));


        //Logica para cambiar el color de la pestaña

        tabHome.setOnClickListener(v -> {
            cambiarFragment(new HomeFragment(), "Home");
            setActiveTab(tabHome);
        });
        tabBiblioteca.setOnClickListener(v -> {
            cambiarFragment(new BibliotecaFragment(), "Biblioteca");
            setActiveTab(tabBiblioteca);
        });
        tabPerfil.setOnClickListener(v -> {
            cambiarFragment(new PerfilFragment(), "Perfil");
            setActiveTab(tabPerfil);
        });
        tabBuscar.setOnClickListener(v -> {
            cambiarFragment(new BuscarFragment(), "Buscar");
            setActiveTab(tabBuscar);
        });
        tabInfo.setOnClickListener(v -> {
            cambiarFragment(new InfoFragment(), "Info");
            setActiveTab(tabInfo);
        });

        //Logica para navegar correctamente entre fragments cuando vuelves de GameDetail

        String fragment = getIntent().getStringExtra("fragment");
        if (fragment != null) {
            switch (fragment) {
                case "Home":
                    cambiarFragment(new HomeFragment(), "Home");
                    setActiveTab(tabHome);
                    break;
                case "Biblioteca":
                    cambiarFragment(new BibliotecaFragment(), "Biblioteca");
                    setActiveTab(tabBiblioteca);
                    break;
                case "Perfil":
                    cambiarFragment(new PerfilFragment(), "Perfil");
                    setActiveTab(tabPerfil);
                    break;
                case "Buscar":
                    cambiarFragment(new BuscarFragment(), "Buscar");
                    setActiveTab(tabBuscar);
                    break;
                case "Info":
                    cambiarFragment(new InfoFragment(), "Info");
                    setActiveTab(tabInfo);
                    break;
            }
        } else {

            // Por defecto
            cambiarFragment(new HomeFragment(), "Home");
            setActiveTab(tabHome);
        }

    }

    private void cambiarFragment(Fragment fragment, String titulo) {
        header.setText(titulo);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .commit();
    }
    private void setActiveTab(TextView activeTab) {
        tabHome.setSelected(false);
        tabBiblioteca.setSelected(false);
        tabPerfil.setSelected(false);
        tabBuscar.setSelected(false);
        tabInfo.setSelected(false);

        activeTab.setSelected(true);
    }
}


