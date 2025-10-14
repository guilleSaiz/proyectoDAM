package com.example.proyectodam.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;

import java.util.List;

public class ScreenshotsAdapter extends RecyclerView.Adapter<ScreenshotsAdapter.ViewHolder> {
    private List<Game.Screenshot> screenshots;

    public ScreenshotsAdapter(List<Game.Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_screenshot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Game.Screenshot shot = screenshots.get(position);
        Glide.with(holder.imageView.getContext())
                .load(shot.image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.screenshotImage);
        }
    }
}
