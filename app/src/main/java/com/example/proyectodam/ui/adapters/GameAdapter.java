package com.example.proyectodam.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;
import com.example.proyectodam.ui.GameDetail;

import java.util.HashSet;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private Context context;
    private List<Game> gameList;

    public GameAdapter(Context context, List<Game> gameList) {
        this.context = context;
        this.gameList = gameList;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        Game game = gameList.get(position);

        // Título
        holder.gameName.setText(game.getName());

        // Imagen del juego
        Glide.with(context)
                .load(game.getBackgroundImage())
                .into(holder.gameImage);


        // PLATAFORMAS (limpia duplicados)
        holder.platformContainer.removeAllViews();

        HashSet<String> shownIcons = new HashSet<>();

        for (Game.PlatformItem item : game.getPlatforms()) {

            if (item.platform == null || item.platform.name == null) continue;

            String name = item.platform.name.toLowerCase();
            String icon = null;

            if (name.contains("pc")) icon = "pc";
            else if (name.contains("xbox")) icon = "xbox";
            else if (name.contains("playstation") || name.contains("ps")) icon = "ps";
            else if (name.contains("nintendo") || name.contains("switch")) icon = "switch";

            // evitar iconos repetidos
            if (icon == null || shownIcons.contains(icon)) continue;
            shownIcons.add(icon);

            ImageView logo = new ImageView(context);

            switch (icon) {
                case "pc": logo.setImageResource(R.drawable.b_pc); break;
                case "xbox": logo.setImageResource(R.drawable.b_xbox); break;
                case "ps": logo.setImageResource(R.drawable.b_ps); break;
                case "switch": logo.setImageResource(R.drawable.b_switch); break;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dp(18), dp(18)
            );
            params.setMargins(dp(8), 0, dp(4), 0);
            logo.setLayoutParams(params);

            holder.platformContainer.addView(logo);
        }


        // BOTONES LIKE Y TAG (ImageView ahora)
        holder.btnLike.setOnClickListener(v -> {
            // Ejemplo si se desea rellenar.
            // holder.btnLike.setImageResource(R.drawable.w_heart_full);
        });

        holder.btnTag.setOnClickListener(v -> {
            // Acción del tag
        });


        // Click en item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetail.class);
            intent.putExtra("gameId", game.getId());
            intent.putExtra("game", game);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }


    // ViewHolder
    public static class GameViewHolder extends RecyclerView.ViewHolder {
        TextView gameName;
        ImageView gameImage;
        ImageView btnLike, btnTag;
        LinearLayout platformContainer;

        public GameViewHolder(View itemView) {
            super(itemView);
            gameName = itemView.findViewById(R.id.gameTitle);
            gameImage = itemView.findViewById(R.id.gameImage);
            btnLike = itemView.findViewById(R.id.btnLike);  // ahora ImageView
            btnTag = itemView.findViewById(R.id.btnTag);    // ahora ImageView
            platformContainer = itemView.findViewById(R.id.platformContainer);
        }
    }

    // convertir dp → px
    private int dp(int value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }
}

