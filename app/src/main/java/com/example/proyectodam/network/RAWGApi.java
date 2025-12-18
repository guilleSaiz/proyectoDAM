package com.example.proyectodam.network;
import com.example.proyectodam.data.ScreenshotsResponse;
import com.example.proyectodam.data.Game;
import com.example.proyectodam.data.GameResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RAWGApi {
    @GET("games/{id}")
    Call<Game> getGameDetails(@Path("id") int gameId, @Query("key") String apiKey);
    @GET("games")
    Call<GameResponse> getGames(@Query("key") String apiKey);
    @GET("games")
    Call<GameResponse> searchGames(@Query("key") String apiKey, @Query("search") String query);
    @GET("games/{id}/screenshots")
    Call<ScreenshotsResponse> getScreenshots(@Path("id") int gameId, @Query("key") String apiKey);

}
