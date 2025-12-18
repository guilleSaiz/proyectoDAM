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
        return backgroundImage;
    }

    // SETTERS para Room
    public void setId(int id) {
        this.id = id;
    }


    // CLASES INTERNAS

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
