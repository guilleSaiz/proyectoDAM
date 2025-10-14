package com.example.proyectodam.data;

import java.util.List;

public class YouTubeResponse {
    public List<Item> items;

    public static class Item {
        public Id id;

        public static class Id {
            public String videoId;
        }
    }
}
