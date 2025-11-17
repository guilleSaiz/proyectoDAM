package com.example.proyectodam.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyectodam.ui.fragments.GameInfoFragment;
import com.example.proyectodam.ui.fragments.GameNoticiasFragment;
import com.example.proyectodam.ui.fragments.GameTiendasFragment;
import com.example.proyectodam.ui.fragments.GameComunidadFragment;

public class GameDetailFragmentAdapter extends FragmentStateAdapter {

    public GameDetailFragmentAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new GameInfoFragment();
            case 1: return new GameNoticiasFragment();
            case 2: return new GameTiendasFragment();
            case 3: return new GameComunidadFragment();
            default: return new GameInfoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
