package com.removie.model;

import com.google.gson.annotations.SerializedName;

/*
Created by twin on May 11, 2019
*/

public class Movie {

    @SerializedName("poster_path")
    public final String posterPath;

    @SerializedName("overview")
    public final String overview;

    @SerializedName("release_date")
    public final String releaseDate;

    @SerializedName("id")
    public final long id;

    @SerializedName("original_title")
    public final String originalTitle;

    @SerializedName("title")
    public final String title;

    @SerializedName("popularity")
    public final double popularity;

    @SerializedName("vote_count")
    public final int voteCount;

    @SerializedName("vote_average")
    public final float voteAverage;

    @SerializedName("region")
    public final String region;

    @SerializedName("backdrop_path")
    public final String backdropPath;

    @SerializedName("profile_path")
    public final String profilePath;

    @SerializedName("name")
    public final String name;

    @SerializedName("character")
    public final String character;

    public final String tagline;

    public Movie(String posterPath, String overview, String releaseDate,
                 long id, String originalTitle, String title,
                 double popularity, int voteCount, float voteAverage, String region,
                 String tagline, String backdropPath, String profilePath, String name, String character) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.originalTitle = originalTitle;
        this.title = title;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.region = region;
        this.tagline = tagline;
        this.backdropPath = backdropPath;
        this.profilePath = profilePath;
        this.name = name;
        this.character = character;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public long getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getRegion() {
        return region;
    }

    public String getTagline() {
        return tagline;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
    public String getProfilePath() {
        return profilePath;
    }
    public String getName() {
        return name;
    }
    public String getCharacter() {
        return character;
    }


}
