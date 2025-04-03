package com.example.final_project;

import java.io.Serializable;
import java.util.Arrays;

public class Movie implements Serializable {
  private String id;
  private String tmdbId;
  private String imdbId;
  private String title;
  private String lenguage;
  private String spokenLenguage;
  private String[] genres;
  private int runtime;
  private int release;
  private double popularity;
  private double voteAverage;
  private int voteCount;
  private String director;
  private String[] cast;
  private String tag;
  private String overview;
  private String[] countries;


  public Movie(String title) {
    this.title = title;
  }

  public Movie(String id, String tmdbId, String imdbId, String title, String lenguage, String spokenLenguage, String genres, int runtime, int release, double popularity, double voteAverage, int voteCount, String director, String cast, String tag, String overview, String countries) {
    this.id = id;
    this.tmdbId = tmdbId;
    this.imdbId = imdbId;
    this.title = title;
    this.lenguage = lenguage;
    this.spokenLenguage = spokenLenguage;
    String tmp = genres.substring(1, genres.length() - 1);
    this.genres = tmp.split(", ");
    this.runtime = runtime;
    this.release = release;
    this.popularity = popularity;
    this.voteAverage = voteAverage;
    this.voteCount = voteCount;
    this.director = director;
    tmp = cast.substring(3, cast.length() - 3);
    this.cast = tmp.split("', '");
    this.tag = tag;
    this.overview = overview;
    this.countries = countries.split(", ");
  }

  @Override
  public String toString() {
    return "Movie{" +
      "id='" + id + '\'' +
      ", tmdbId='" + tmdbId + '\'' +
      ", imdbId='" + imdbId + '\'' +
      ", title='" + title + '\'' +
      ", length='" + lenguage + '\'' +
      ", spokenLanguage='" + spokenLenguage + '\'' +
      ", genres=" + Arrays.toString(genres) +
      ", runtime=" + runtime +
      ", release=" + release +
      ", popularity=" + popularity +
      ", voteAverage=" + voteAverage +
      ", voteCount=" + voteCount +
      ", director='" + director + '\'' +
      ", cast=" + Arrays.toString(cast) +
      ", tag='" + tag + '\'' +
      ", overview='" + overview + '\'' +
      ", countries=" + Arrays.toString(countries) +
      '}';
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTmdbId() {
    return tmdbId;
  }

  public void setTmdbId(String tmdbId) {
    this.tmdbId = tmdbId;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLenguage() {
    return lenguage;
  }

  public void setLenguage(String lenguage) {
    this.lenguage = lenguage;
  }

  public String getSpokenLenguage() {
    return spokenLenguage;
  }

  public void setSpokenLenguage(String spokenLenguage) {
    this.spokenLenguage = spokenLenguage;
  }

  public String[] getGenres() {
    return genres;
  }

  public void setGenres(String[] genres) {
    this.genres = genres;
  }

  public int getRuntime() {
    return runtime;
  }

  public void setRuntime(int runtime) {
    this.runtime = runtime;
  }

  public int getRelease() {
    return release;
  }

  public void setRelease(int release) {
    this.release = release;
  }

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public int getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String[] getCast() {
    return cast;
  }

  public void setCast(String[] cast) {
    this.cast = cast;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String[] getCountries() {
    return countries;
  }

  public void setCountries(String[] countries) {
    this.countries = countries;
  }
}
