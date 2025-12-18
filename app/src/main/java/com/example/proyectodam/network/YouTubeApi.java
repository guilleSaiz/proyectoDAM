package com.example.proyectodam.network;

import com.example.proyectodam.data.YouTubeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApi {
    @GET("search")
    Call<YouTubeResponse> searchVideo(
            @Query("part") String part,
            @Query("maxResults") int maxResults,
            @Query("q") String query,
            @Query("key") String apiKey,
            @Query("type") String type,
            @Query("videoEmbeddable") String embeddable
    );
}
