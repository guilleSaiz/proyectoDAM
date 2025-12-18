package com.example.proyectodam.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyectodam.R;
import com.example.proyectodam.db.UserEntity;
import com.example.proyectodam.repository.UserRepository;
import com.example.proyectodam.ui.RegistroActivity;

import java.util.concurrent.Executors;

public class PerfilFragment extends Fragment {
    private EditText user, pass;
    private TextView registro;
    private Button login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_perfil, container, false);

        user = view.findViewById(R.id.user);
        pass = view.findViewById(R.id.pass);
        registro = view.findViewById(R.id.registro);
        login = view.findViewById(R.id.login);

        login.setOnClickListener(v -> login());


        registro.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RegistroActivity.class);
            startActivity(intent);
        });


        return view;
    }

    private void login() {
        String username = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            UserRepository userLogin = new UserRepository(requireContext());
            UserEntity loggedUser = userLogin.login(username, password);

            getActivity().runOnUiThread(() -> {
                if (loggedUser != null) {
                    Toast.makeText(requireContext(), "Login correcto", Toast.LENGTH_SHORT).show();
                    // Aquí luego puedes guardar sesión
                } else {
                    Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
