package com.example.proyectodam.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectodam.R;
import com.example.proyectodam.repository.UserRepository;
import com.example.proyectodam.db.*;



public class RegistroActivity extends AppCompatActivity {

    private TextView tabHome, tabBiblioteca, tabPerfil, tabBuscar, tabInfo;
    private EditText user, pass, email;
    private Button registro;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        activarModoInmersivo();
        initFooterTabs();

        userRepository = new UserRepository(this);

        user = findViewById(R.id.R_user);
        pass = findViewById(R.id.R_pass);
        email = findViewById(R.id.R_email);
        registro = findViewById(R.id.R_registro);

        //Toast.makeText(this, "LLEGUÉ", Toast.LENGTH_SHORT).show();

        registro.setOnClickListener(v -> {
            registrarUsuario();
        });
    }

    private void registrarUsuario() {
        String username = user.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (username.isEmpty() || mail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        userRepository.userExists(username, mail, exists -> {
            if (exists) {
                Toast.makeText(this, "El usuario o email ya existen", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar usuario
                UserEntity newUser = new UserEntity();
                newUser.username = username;
                newUser.email = mail;
                newUser.password = password;
                newUser.createdAt = System.currentTimeMillis();

                userRepository.register(newUser);

                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }





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

}
