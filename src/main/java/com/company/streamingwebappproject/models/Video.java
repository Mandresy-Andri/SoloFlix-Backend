package com.company.streamingwebappproject.models;

import java.util.Objects;

public class Video {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(key, video.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
