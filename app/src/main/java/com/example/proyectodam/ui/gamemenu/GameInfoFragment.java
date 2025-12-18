package com.example.proyectodam.ui.gamemenu;

import static com.example.proyectodam.ui.menu.HomeFragment.API_KEY;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;
import com.example.proyectodam.data.ScreenshotsResponse;
import com.example.proyectodam.data.YouTubeResponse;
import com.example.proyectodam.network.GameRepository;
import com.example.proyectodam.ui.GameDetail;
import com.example.proyectodam.ui.adapters.ScreenshotsAdapter;
import com.example.proyectodam.utils.GameUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameInfoFragment extends Fragment {

    private GameRepository repo;

    private ImageView gameImage;
    private TextView title, releaseDate, platforms, genres, developers, website, rating, tags;
    private YouTubePlayerView youtubePlayer;
    private RecyclerView screenshotRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_game_info, container, false);

        repo = new GameRepository();

        // Obtener ID del juego desde la Activity
        int gameId = ((GameDetail) requireActivity()).getGameId();

        // Inicializar vistas
        gameImage = v.findViewById(R.id.detailGameImage);
        title = v.findViewById(R.id.detailGameTitle);
        releaseDate = v.findViewById(R.id.detailGameRelease);
        platforms = v.findViewById(R.id.detailGamePlatforms);
        genres = v.findViewById(R.id.detailGameGenres);
        developers = v.findViewById(R.id.detailDevelopers);
        website = v.findViewById(R.id.detailWebsite);
        rating = v.findViewById(R.id.detailGameRating);
        tags = v.findViewById(R.id.detailTags);
        youtubePlayer = v.findViewById(R.id.youtubePlayerView);
        screenshotRecycler = v.findViewById(R.id.screenshotRecyclerView);

        screenshotRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        // Registrar lifecycle para el YouTubePlayer
        getLifecycle().addObserver(youtubePlayer);

        // Cargar datos del juego
        fetchGame(gameId);
        fetchScreenshots(gameId);

        return v;
    }

    private void fetchGame(int id) {
        repo.getGameDetails(id, API_KEY, new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful() || response.body() == null) return;

                Game game = response.body();

                // Imagen y título
                Glide.with(requireContext()).load(game.getBackgroundImage()).into(gameImage);
                title.setText(game.getName());

                // Campos de info
                releaseDate.setText(game.getReleased());
                platforms.setText(TextUtils.join(", ", GameUtils.getPlatformNames(game.getPlatforms())));
                genres.setText(TextUtils.join(", ", GameUtils.getNamesFromList(game.getGenres())));
                developers.setText(TextUtils.join(", ", GameUtils.getNamesFromList(game.getDevelopers())));
                website.setText(game.getWebsite() != null ? game.getWebsite() : "N/A");
                rating.setText(game.getMetacritic() != null ? String.valueOf(game.getMetacritic()) : "N/A");
                tags.setText(GameUtils.getEnglishTags(game.getTags()));

                // Cargar video de YouTube si hay nombre
                if (game.getName() != null) fetchYoutube(game.getName());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.e("GameInfoFragment", "Error API: " + t.getMessage());
            }
        });
    }

    private void fetchScreenshots(int id) {
        repo.getScreenshots(id, API_KEY, new Callback<ScreenshotsResponse>() {
            @Override
            public void onResponse(Call<ScreenshotsResponse> call, Response<ScreenshotsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    screenshotRecycler.setAdapter(new ScreenshotsAdapter(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(Call<ScreenshotsResponse> call, Throwable t) {
                Log.e("GameInfoFragment", "Error screenshots: " + t.getMessage());
            }
        });
    }

    private void fetchYoutube(String query) {
        repo.searchYoutubeVideo(query, new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                if (!response.isSuccessful() || response.body() == null) return;

                for (YouTubeResponse.Item item : response.body().items) {
                    if (item.id.videoId != null) {
                        String videoId = item.id.videoId;
                        youtubePlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(YouTubePlayer player) {
                                player.loadVideo(videoId, 0);
                            }
                        });
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                Log.e("GameInfoFragment", "Error YouTube: " + t.getMessage());
            }
        });
    }
}

