package com.example.proyectodam.network;

import com.example.proyectodam.data.Game;
import com.example.proyectodam.data.ScreenshotsResponse;
import com.example.proyectodam.data.YouTubeResponse;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameRepository {
    private RAWGApi rawgApi;
    private YouTubeApi youtubeApi;
    private static final String RAWG_BASE_URL = "https://api.rawg.io/api/";
    private static final String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String YOUTUBE_KEY = "";

    public GameRepository() {
        Retrofit rawgRetrofit = new Retrofit.Builder()
                .baseUrl(RAWG_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rawgApi = rawgRetrofit.create(RAWGApi.class);

        Retrofit ytRetrofit = new Retrofit.Builder()
                .baseUrl(YOUTUBE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        youtubeApi = ytRetrofit.create(YouTubeApi.class);
    }

    public void getGameDetails(int gameId, String apiKey, Callback<Game> callback) {
        rawgApi.getGameDetails(gameId, apiKey).enqueue(callback);
    }

    public void getScreenshots(int gameId, String apiKey, Callback<ScreenshotsResponse> callback) {
        rawgApi.getScreenshots(gameId, apiKey).enqueue(callback);
    }

    public void searchYoutubeVideo(String query, Callback<YouTubeResponse> callback) {
        youtubeApi.searchVideo("snippet", 1, query + " trailer", YOUTUBE_KEY, "video")
                .enqueue(callback);
    }
}
