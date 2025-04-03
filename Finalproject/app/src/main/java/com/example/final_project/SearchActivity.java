package com.example.final_project;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements MovieItemClickListener {

  List<Movie> movies = MovieController.getVAList();
  List<Movie> VClistMovies = MovieController.readMoviesFromCSV();
  private AutoCompleteTextView searchBar;
  private RecyclerView resultRV;
  private ImageButton searchButton;
  private Spinner searchItem;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search);

    searchBar = findViewById(R.id.search_bar);
    resultRV = findViewById(R.id.search_result);
    searchButton = findViewById(R.id.search_button);
    searchItem = findViewById(R.id.search_item);

    String[] item = {"영화명", "배우명"};

    ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, item);
    genreAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
    searchItem.setAdapter(genreAdapter);
    searchItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) adapterView.getChildAt(0)).setTextSize(20);
        if (item[position].equals("영화명")) {
          List<String> items = new ArrayList<>();
          for (int i = 0; i < movies.size(); i++) {
            items.add(movies.get(i).getTitle());
          }

          ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, items.toArray(new String[0]));
          searchBar.setAdapter(adapter);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }

    });

    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String content = String.valueOf(searchBar.getText());
        List<Movie> resultMovie = new ArrayList<>();

        if (searchItem.getSelectedItem().toString().equals("영화명")) {
          for (int i = 0; i < movies.size(); i++) {
            if (resultMovie.size() >= 5) break;
            if (movies.get(i).getTitle().toLowerCase().contains(content.toLowerCase())) {
              resultMovie.add(movies.get(i));
            }
          }
        }

        if (searchItem.getSelectedItem().toString().equals("배우명")) {
          for (int i = 0; i < movies.size(); i++) {
            if (resultMovie.size() >= 5) break;
            for (int j = 0; j < movies.get(i).getCast().length; j++) {
              if (movies.get(i).getCast()[j].toLowerCase().contains(content.toLowerCase())) {
                resultMovie.add(movies.get(i));
                break;
              }
            }
          }
        }


        MovieAdapter2 movieAdapter2 = new MovieAdapter2(SearchActivity.this, resultMovie, SearchActivity.this);
        resultRV.setAdapter(movieAdapter2);
        resultRV.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
      }
    });
  }

  @Override
  public void onMovieClick(Movie movie, ImageView movieImageView) {
    Intent intent = new Intent(this, MovieDetailActivity.class);
    intent.putExtra("movie", movie);

    ArrayList<Movie> relateMovies = new ArrayList<>();
    for (int i = 0; i < VClistMovies.size(); i++) {
      for (int j = 0; j < VClistMovies.get(i).getCast().length; j++) {
        for (int k = 0; k < movie.getCast().length; k++) {
          if (VClistMovies.get(i).getCast()[j].equals(movie.getCast()[k])) {
            if (VClistMovies.get(i).getId().equals(movie.getId())) continue;
            relateMovies.add(VClistMovies.get(i));
          }
        }
      }
    }
    intent.putExtra("relateMovies", relateMovies);
    intent.putExtra("VClistMovies", (Serializable) VClistMovies);

    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SearchActivity.this, movieImageView, "sharedName");

    startActivity(intent, options.toBundle());
  }
}
