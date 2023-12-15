package com.kodev.moview.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApi {

    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public static class Movie {
        @SerializedName("original_language")
        private String originalLanguage;

        @SerializedName("original_title")
        private String originalTitle;

        @SerializedName("overview")
        private String overView;

        @SerializedName("vote_average")
        private float voteAverage;

        @SerializedName("id")
        private Long id;

        @SerializedName("poster_path")
        private String posterPath;

        @SerializedName("release_date")
        private String releaseDate;

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getOriginalLanguage() {
            return originalLanguage;
        }

        public void setOriginalLanguage(String originalLanguage) {
            this.originalLanguage = originalLanguage;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public String getOverView() {
            return overView;
        }

        public void setOverView(String overView) {
            this.overView = overView;
        }

        public float getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(float voteAverage) {
            this.voteAverage = voteAverage;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }
    }
}
