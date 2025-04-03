package com.example.final_project;

public class Review {
  private String movieId;
  private String user;
  private String date;
  private String title;
  private String point;

  public Review(String movieId, String user, String date, String title, String point) {
    this.movieId = movieId;
    this.user = user;
    this.date = date;
    this.title = title;
    this.point = point;
  }

// Add getters and setters

  public String getMovieId() {
    return movieId;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPoint() {
    return point;
  }

  public void setPoint(String point) {
    this.point = point;
  }
}
