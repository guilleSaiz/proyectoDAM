package com.example.proyectodam.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;

import java.util.List;

public class ScreenshotsAdapter extends RecyclerView.Adapter<ScreenshotsAdapter.VH> {

    private List<Game.Screenshot> items;

    public ScreenshotsAdapter(List<Game.Screenshot> items) {
        this.items = items;
    }

    public void setItems(List<Game.Screenshot> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_screenshot, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Game.Screenshot s = items.get(position);
        Glide.with(holder.image.getContext()).load(s.image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        VH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.screenshotImage);
        }
    }
}
