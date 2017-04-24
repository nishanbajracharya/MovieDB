package com.nishan.moviedb;

public class Movie {
    private String imdbID, title, genre, rating, plot, director, writer, cast, imdbLink, externalLink, poster, runtime, releasedDate;

    public Movie(String imdbID, String title, String genre, String rating, String plot, String director, String writer, String cast, String imdbLink, String externalLink, String poster, String runtime, String releasedDate) {
        this.imdbID = imdbID;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.plot = plot;
        this.director = director;
        this.writer = writer;
        this.cast = cast;
        this.imdbLink = imdbLink;
        this.externalLink = externalLink;
        this.poster = poster;
        this.runtime = runtime;
        this.releasedDate = releasedDate;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public void setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }
}
