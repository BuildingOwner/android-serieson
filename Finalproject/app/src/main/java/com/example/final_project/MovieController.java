package com.example.final_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MovieController {

  private static List<Movie> listMovies;

  static public List<Movie> readMoviesFromCSV() {
    listMovies = new ArrayList<>();

    try {
      File file = new File("/sdcard/data.csv");
      Scanner scanner = new Scanner(file);

      // Skip the header line if present
      if (scanner.hasNextLine()) {
        scanner.nextLine();
      }

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        // Extract the fields from the record
        String id = fields[0];
        String tmdbId = fields[1];
        String imdbId = fields[2];
        String title = fields[3];
        String language = fields[4];
        String spokenLanguage = fields[5];
        String genres = fields[6];
        int runtime = Integer.parseInt(fields[7]);
        int release = Integer.parseInt(fields[8].substring(0, 4));
        double popularity = Double.parseDouble(fields[9]);
        double voteAverage = Double.parseDouble(fields[10]);
        int voteCount = Integer.parseInt(fields[11]);
        String director = fields[12];
        String cast = fields[13];
        String tag = fields[14];
        String overview = fields[15];
        String countries = fields[16];

        // Create a Movie instance with the extracted data
        Movie movie = new Movie(id, tmdbId, imdbId, title, language, spokenLanguage, genres, runtime, release, popularity, voteAverage, voteCount, director, cast, tag, overview, countries);

        // Add the movie to the list
        listMovies.add(movie);
      }

      scanner.close();

    } catch (FileNotFoundException e) {
      System.out.println("The CSV file was not found: " + e.getMessage());
    }

    return listMovies;
  }

  public static List<Movie> getPList() {
    List<Movie> tmp = new ArrayList<>();
    for (int i = 0; i < listMovies.size(); i++) {
      tmp.add(listMovies.get(i));
    }
    Collections.sort(tmp, new PopularityComparator());
    return tmp;
  }

  public static List<Movie> getVAList() {
    List<Movie> tmp = new ArrayList<>();
    for (int i = 0; i < listMovies.size(); i++) {
      tmp.add(listMovies.get(i));
    }
    Collections.sort(tmp, new VoteAverageComparator());
    return tmp;
  }

}

class PopularityComparator implements Comparator<Movie> {
  @Override
  public int compare(Movie f1, Movie f2) {
    if (f1.getPopularity() < f2.getPopularity()) {
      return 1;
    } else if (f1.getPopularity() > f2.getPopularity()) {
      return -1;
    }
    return 0;
  }
}

class VoteAverageComparator implements Comparator<Movie> {
  @Override
  public int compare(Movie f1, Movie f2) {
    if (f1.getVoteAverage() < f2.getVoteAverage()) {
      return 1;
    } else if (f1.getVoteAverage() > f2.getVoteAverage()) {
      return -1;
    }
    return 0;
  }
}