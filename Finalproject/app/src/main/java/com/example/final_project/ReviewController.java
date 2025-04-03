package com.example.final_project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewController {

  public static List<Review> crawlMovieReviews(String movieId) throws IOException {
    List<Review> reviews = new ArrayList<>();

    String url = "https://www.imdb.com/title/" + movieId + "/reviews?sort=submissionDate&dir=desc&ratingFilter=0";
    Document doc = Jsoup.connect(url).get();

    List<Element> reviewElements = doc.select("div.review-container");
    int limit = Math.min(10, reviewElements.size());
    reviewElements = reviewElements.subList(0, limit);

    for (Element reviewElement : reviewElements) {
      Element userElement = reviewElement.selectFirst("div.lister-item-content > div.display-name-date > span.display-name-link > a");
      Element dateElement = reviewElement.selectFirst("div.lister-item-content > div.display-name-date > span.review-date");
      Element titleElement = reviewElement.selectFirst("a.title");
      Element pointElement = reviewElement.selectFirst("div.lister-item-content > div.ipl-ratings-bar > span > span:nth-child(2)");

      String user = userElement != null ? userElement.text() : "N/A";
      String date = dateElement != null ? dateElement.text() : "N/A";
      String title = titleElement != null ? titleElement.text() : "N/A";
      String point = pointElement != null ? pointElement.text() : "N/A";

      Review review = new Review(movieId, user, date, title, point);
      reviews.add(review);
    }

    return reviews;
  }
}