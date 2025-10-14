package com.example.proyectodam.data;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.io.Serializable;

public class Game implements Serializable {

    private int id;
    private String slug;
    private String name;
    private String released;
    @SerializedName("background_image")
    private String backgroundImage;
    private double rating;
    private Integer metacritic;
    private String website;

    @SerializedName("short_screenshots")
    private List<Screenshot> screenshots;
    private List<PlatformItem> platforms;
    private List<GenreItem> genres;
    private List<DeveloperItem> developers;
    private List<TagItem> tags;

    // Getters originales
    public int getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getReleased() { return released; }
    public double getRating() { return rating; }
    public Integer getMetacritic() { return metacritic; }
    public String getWebsite() { return website; }
    public List<PlatformItem> getPlatforms() { return platforms; }
    public List<GenreItem> getGenres() { return genres; }
    public List<DeveloperItem> getDevelopers() { return developers; }
    public List<TagItem> getTags() { return tags; }
    public List<Screenshot> getScreenshots() { return screenshots; }

    public String getBackgroundImage() {
        return backgroundImage != null ? backgroundImage : backgroundImageString;
    }

    // ===== CAMPOS AUXILIARES PARA ROOM/CACHE =====

    private String genreString;
    private String platformString;
    private String developerString;
    private String tagString;
    private String websiteString;
    private String youtubeVideoId;
    private String backgroundImageString;
    private float ratingFloat;

    // ===== SETTERS para Room =====
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setReleased(String released) {
        this.released = released;
    }
    public void setGenres(String genres) {
        this.genreString = genres;
    }

    public void setPlatforms(String platforms) {
        this.platformString = platforms;
    }

    public void setDevelopers(String developers) {
        this.developerString = developers;
    }

    public void setTags(String tags) {
        this.tagString = tags;
    }

    public void setWebsite(String website) {
        this.websiteString = website;
    }

    public void setYoutubeVideo(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public void setRating(float rating) {
        this.ratingFloat = rating;
    }

    public void setBackgroundImage(String url) {
        this.backgroundImageString = url;
    }

    // ===== GETTERS auxiliares para Room =====

    public String getGenresString() {
        return genreString;
    }

    public String getPlatformsString() {
        return platformString;
    }

    public String getDevelopersString() {
        return developerString;
    }

    public String getTagsString() {
        return tagString;
    }

    public String getYoutubeVideo() {
        return youtubeVideoId;
    }

    public float getRatingFloat() {
        return (float) rating;
    }

    public String getWebsiteString() {
        return websiteString;
    }

    // ===== CLASES INTERNAS =====

    public static class PlatformItem implements Serializable {
        public Platform platform;
    }

    public static class Platform implements Serializable {
        public String name;
    }

    public static class GenreItem implements Serializable {
        public String name;
    }

    public static class DeveloperItem implements Serializable {
        public String name;
    }

    public static class TagItem implements Serializable {
        public String name;
    }

    public static class Screenshot implements Serializable {
        public String image;
    }
}
