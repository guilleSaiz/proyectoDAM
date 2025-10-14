package com.example.proyectodam.database;

import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.proyectodam.data.Game;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "games")
public class GameEntity {
    @PrimaryKey
    public int id;

    public String name;
    public String released;
    public String backgroundImageUrl;
    public float rating;
    public String genres;
    public String platforms;
    public String developers;
    public String website;
    public String tags;
    public String youtubeVideoId;

    public static GameEntity fromGame(Game game) {
        GameEntity entity = new GameEntity();
        entity.id = game.getId();
        entity.name = game.getName();
        entity.released = game.getReleased();
        entity.rating = (float) game.getRating();
        entity.backgroundImageUrl = game.getBackgroundImage();
        entity.genres = joinGenreNames(game.getGenres());
        entity.platforms = joinPlatformNames(game.getPlatforms());
        entity.developers = joinDeveloperNames(game.getDevelopers());
        entity.website = game.getWebsite();
        entity.tags = joinTagNames(game.getTags());
        entity.youtubeVideoId = game.getYoutubeVideo();
        return entity;
    }

    public Game toGame() {
        Game game = new Game();
        game.setId(this.id);
        game.setName(this.name);
        game.setReleased(this.released);
        game.setRating(this.rating);
        game.setBackgroundImage(this.backgroundImageUrl);
        game.setWebsite(this.website);
        game.setYoutubeVideo(this.youtubeVideoId);

        // Si quieres restaurar las listas, crea métodos en Game para ello (opcional)
        return game;
    }

    private static String joinGenreNames(List<Game.GenreItem> genres) {
        if (genres == null || genres.isEmpty()) return "";
        List<String> names = new ArrayList<>();
        for (Game.GenreItem genre : genres) {
            names.add(genre.name);
        }
        return TextUtils.join(", ", names);
    }

    private static String joinDeveloperNames(List<Game.DeveloperItem> devs) {
        if (devs == null || devs.isEmpty()) return "";
        List<String> names = new ArrayList<>();
        for (Game.DeveloperItem dev : devs) {
            names.add(dev.name);
        }
        return TextUtils.join(", ", names);
    }

    private static String joinTagNames(List<Game.TagItem> tags) {
        if (tags == null || tags.isEmpty()) return "";
        List<String> names = new ArrayList<>();
        for (Game.TagItem tag : tags) {
            names.add(tag.name);
        }
        return TextUtils.join(", ", names);
    }

    private static String joinPlatformNames(List<Game.PlatformItem> items) {
        if (items == null || items.isEmpty()) return "";
        List<String> names = new ArrayList<>();
        for (Game.PlatformItem item : items) {
            if (item.platform != null && item.platform.name != null)
                names.add(item.platform.name);
        }
        return TextUtils.join(", ", names);
    }
}