package com.example.proyectodam.utils;

import com.example.proyectodam.data.Game;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    public static List<String> getNamesFromList(List<? extends Object> items) {
        List<String> names = new ArrayList<>();
        if (items != null) {
            for (Object item : items) {
                if (item instanceof Game.GenreItem) names.add(((Game.GenreItem) item).name);
                if (item instanceof Game.DeveloperItem) names.add(((Game.DeveloperItem) item).name);
                if (item instanceof Game.TagItem) names.add(((Game.TagItem) item).name);
            }
        }
        return names;
    }

    public static List<String> getPlatformNames(List<Game.PlatformItem> items) {
        List<String> names = new ArrayList<>();
        if (items != null) {
            for (Game.PlatformItem item : items) {
                if (item.platform != null && item.platform.name != null)
                    names.add(item.platform.name);
            }
        }
        return names;
    }

    public static String getEnglishTags(List<Game.TagItem> tags) {
        if (tags == null || tags.isEmpty()) return "No English tags available";

        StringBuilder englishTags = new StringBuilder();
        for (Game.TagItem tag : tags) {
            if (tag.name.matches("^[a-zA-Z0-9\\s]+$")) {
                englishTags.append(tag.name).append(", ");
            }
        }

        if (englishTags.length() > 0) {
            englishTags.setLength(englishTags.length() - 2);
            return englishTags.toString();
        } else {
            return "No English tags available";
        }
    }
}
