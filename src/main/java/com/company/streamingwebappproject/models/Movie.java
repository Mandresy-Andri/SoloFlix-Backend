package com.company.streamingwebappproject.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "movie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @JsonProperty("id")
    @Column(name = "movie_ref")
    private BigInteger reference;

    @JsonProperty("original_title")
    @Column(name = "movie_title")
    private String title;

    @JsonProperty("overview")
    @Column(name = "movie_description")
    private String description;

    @JsonProperty("release_date")
    @Column(name = "movie_year")
    private String date;

    @JsonProperty("key")
    @Column(name = "movie_video")
    private String video;

    @JsonProperty("poster_path")
    @Column(name = "movie_img")
    private String image;

    @JsonProperty("vote_average")
    @Column(name = "movie_rating")
    private double rating;

    public BigInteger getReference() {
        return reference;
    }

    public void setReference(BigInteger reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return "https://image.tmdb.org/t/p/w500" + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.rating, rating) == 0 && Objects.equals(reference, movie.reference) && Objects.equals(title, movie.title) && Objects.equals(description, movie.description) && Objects.equals(date, movie.date) && Objects.equals(video, movie.video) && Objects.equals(image, movie.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, title, description, date, video, image, rating);
    }
}
