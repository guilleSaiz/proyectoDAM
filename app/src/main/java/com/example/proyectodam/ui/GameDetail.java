package com.example.proyectodam.ui;

import static android.content.ContentValues.TAG;
import static com.example.proyectodam.ui.MainActivity.API_KEY;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectodam.R;
import com.example.proyectodam.data.Game;
import com.example.proyectodam.data.ScreenshotsResponse;
import com.example.proyectodam.data.YouTubeResponse;
import com.example.proyectodam.database.AppDatabase;
import com.example.proyectodam.database.GameDao;
import com.example.proyectodam.database.GameEntity;
import com.example.proyectodam.network.GameRepository;
import com.example.proyectodam.utils.GameUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetail extends AppCompatActivity {

    private static final String TAG = "GameDetail";

    private TextView title, releaseDate, metacritic, genres, platforms, developers, tags, website;
    private ImageView gameImage;
    private YouTubePlayerView youTubePlayerView;
    private String gameTitle;
    private GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_detail);

        Log.d(TAG, "onCreate iniciado");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        gameRepository = new GameRepository();

        Game game = (Game) getIntent().getSerializableExtra("game");
        int gameId = getIntent().getIntExtra("gameId", -1);

        Log.d(TAG, "Recibido gameId: " + gameId);

        if (gameId != -1) {
            fetchGameFromCacheOrAPI(gameId);
        } else {
            Log.e(TAG, "ID de juego inválido recibido: " + gameId);
            Toast.makeText(this, "Error al obtener ID del juego", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (game != null) {
            Log.d(TAG, "Game recibido: " + game.getName());
            displayBasicInfo(game);
        } else {
            Log.w(TAG, "Game es null en onCreate");
        }
    }

    private void initViews() {
        title = findViewById(R.id.detailGameTitle);
        releaseDate = findViewById(R.id.detailGameRelease);
        metacritic = findViewById(R.id.detailGameRating);
        gameImage = findViewById(R.id.detailGameImage);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);
        genres = findViewById(R.id.detailGameGenres);
        platforms = findViewById(R.id.detailGamePlatforms);
        developers = findViewById(R.id.detailDevelopers);
        website = findViewById(R.id.detailWebsite);
        tags = findViewById(R.id.detailTags);
        RecyclerView screenshotRecycler = findViewById(R.id.screenshotRecyclerView);
        screenshotRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Log.d(TAG, "Views inicializadas");
    }

    private void fetchGameFromCacheOrAPI(int gameId) {
        Log.d(TAG, "Buscando juego en caché con gameId: " + gameId);

        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        GameDao dao = db.gameDao();

        new Thread(() -> {
            GameEntity cachedGame = dao.getGameById(gameId);
            if (cachedGame != null) {
                Log.d(TAG, "Juego encontrado en caché: " + cachedGame.name);
            } else {
                Log.d(TAG, "Juego no encontrado en caché, se llamará a la API");
            }

            runOnUiThread(() -> {
                if (cachedGame != null) {
                    Game gameCache = cachedGame.toGame();
                    updateUIWithGameDetails(gameCache);
                } else {
                    fetchAndCacheGameDetails(gameId, dao);
                }
            });
        }).start();
    }

    private void fetchAndCacheGameDetails(int gameId, GameDao dao) {
        Log.d(TAG, "Solicitando detalles del juego a la API para gameId: " + gameId);

        gameRepository.getGameDetails(gameId, API_KEY, new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Detalles del juego recibidos correctamente desde la API");
                    Game game = response.body();
                    updateUIWithGameDetails(game);

                    new Thread(() -> {
                        Log.d(TAG, "Insertando juego en base de datos");
                        GameEntity entity = GameEntity.fromGame(game);
                        dao.insertGame(entity);

                        int maxGames = 100;
                        int count = dao.getGameCount();
                        Log.d(TAG, "Número de juegos en base de datos: " + count);
                        if (count > maxGames) {
                            Log.d(TAG, "Eliminando juegos antiguos para mantener límite");
                            dao.deleteOldGames(maxGames);
                        }
                    }).start();
                } else {
                    Log.e(TAG, "Respuesta de API no exitosa o cuerpo vacío en getGameDetails");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Log.e(TAG, "Error al obtener detalles del juego", t);
            }
        });

        fetchScreenshots(gameId);
        fetchYoutubeVideo(gameTitle);
    }

    private void displayBasicInfo(Game game) {
        Log.d(TAG, "Mostrando información básica para el juego: " + game.getName());

        title.setText(game.getName());
        releaseDate.setText("Lanzamiento: " + game.getReleased());
        metacritic.setText("Metacritic: " + game.getMetacritic());
        Glide.with(this).load(game.getBackgroundImage()).into(gameImage);
        gameTitle = game.getName();

        genres.setText("Géneros: " + TextUtils.join(", ", GameUtils.getNamesFromList(game.getGenres())));
        platforms.setText("Plataformas: " + TextUtils.join(", ", GameUtils.getPlatformNames(game.getPlatforms())));
        tags.setText(GameUtils.getEnglishTags(game.getTags()));
    }

    private void updateUIWithGameDetails(Game game) {
        Log.d(TAG, "Actualizando UI con detalles completos del juego: " + game.getName());

        title.setText(game.getName());
        releaseDate.setText("Lanzamiento: " + game.getReleased());
        metacritic.setText("Metacritic: " + (game.getMetacritic() != null ? game.getMetacritic() : "N/A"));
        Glide.with(this).load(game.getBackgroundImage()).into(gameImage);

        genres.setText("Géneros: " + TextUtils.join(", ", GameUtils.getNamesFromList(game.getGenres())));
        platforms.setText("Plataformas: " + TextUtils.join(", ", GameUtils.getPlatformNames(game.getPlatforms())));

        if (game.getDevelopers() != null && !game.getDevelopers().isEmpty()) {
            String devs = TextUtils.join(", ", GameUtils.getNamesFromList(game.getDevelopers()));
            developers.setText(devs);
        } else {
            developers.setText("Desarrollador desconocido");
        }

        website.setText(game.getWebsite() != null && !game.getWebsite().isEmpty() ? game.getWebsite() : "N/A");
        tags.setText(GameUtils.getEnglishTags(game.getTags()));
    }

    private void fetchScreenshots(int gameId) {
        Log.d(TAG, "Solicitando screenshots para gameId: " + gameId);

        gameRepository.getScreenshots(gameId, API_KEY, new Callback<ScreenshotsResponse>() {
            @Override
            public void onResponse(Call<ScreenshotsResponse> call, Response<ScreenshotsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Screenshots recibidos correctamente");
                    List<Game.Screenshot> shots = response.body().getResults();
                    ScreenshotsAdapter adapter = new ScreenshotsAdapter(shots);
                    RecyclerView recycler = findViewById(R.id.screenshotRecyclerView);
                    recycler.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Respuesta no exitosa o vacía en getScreenshots");
                }
            }

            @Override
            public void onFailure(Call<ScreenshotsResponse> call, Throwable t) {
                Log.e(TAG, "Error al obtener screenshots: " + t.getMessage(), t);
            }
        });
    }

    private void fetchYoutubeVideo(String query) {
        Log.d(TAG, "Buscando vídeo de YouTube para la query: " + query);

        gameRepository.searchYoutubeVideo(query, new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().items.isEmpty()) {
                    Log.d(TAG, "Vídeo de YouTube encontrado");
                    String videoId = response.body().items.get(0).id.videoId;

                    getLifecycle().addObserver(youTubePlayerView);
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {
                            Log.d(TAG, "Reproduciendo vídeo con ID: " + videoId);
                            youTubePlayer.loadVideo(videoId, 0);
                        }
                    });
                } else {
                    Log.e(TAG, "No se encontraron vídeos o respuesta vacía en búsqueda YouTube");
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                Log.e(TAG, "Error al buscar vídeo: " + t.getMessage(), t);
            }
        });
    }
}
