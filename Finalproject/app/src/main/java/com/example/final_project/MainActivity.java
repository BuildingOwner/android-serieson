package com.example.final_project;

import static com.example.final_project.MovieController.readMoviesFromCSV;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieItemClickListener {

  private List<Slider> listSlides;
  private ViewPager sliderpager;
  private TabLayout indicater;
  private RecyclerView MovieHotRV;
  private RecyclerView MoviePopularRV;
  private RecyclerView genreRV;
  private List<Movie> VClistMovies = MovieController.readMoviesFromCSV();
  private List<Movie> VAlistMovies = MovieController.getVAList();
  private List<Movie> PlistMovies = MovieController.getPList();
  private List<Movie> genreList = new ArrayList<Movie>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sliderpager = findViewById(R.id.slider_pager);
    indicater = findViewById(R.id.indicator);
    MovieHotRV = findViewById(R.id.Rv_movies_hot);
    MoviePopularRV = findViewById(R.id.Rv_movies_popular);
    genreRV = findViewById(R.id.Rv_genre);

    listSlides = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      listSlides.add(new Slider(R.drawable.leon, PlistMovies.get(i).getTitle()));
    }
    SliderPagerAdepter adepter = new SliderPagerAdepter(this, listSlides);
    sliderpager.setAdapter(adepter);
    indicater.setupWithViewPager(sliderpager, true);

    MovieAdepter movieAdepter = new MovieAdepter(this, VClistMovies.subList(0, 19), this);
    MovieHotRV.setAdapter(movieAdepter);
    MovieHotRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    MovieAdepter movieAdepter2 = new MovieAdepter(this, VAlistMovies.subList(0, 19), this);
    MoviePopularRV.setAdapter(movieAdepter2);
    MoviePopularRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    for (int i = 0; i < VClistMovies.size(); i++) {
      for (int j = 0; j < VClistMovies.get(i).getGenres().length; j++) {
        if (VClistMovies.get(i).getGenres()[j].equals("Action")) {
          genreList.add(VClistMovies.get(i));
        }
      }
      if (genreList.size() >= 20) break;
    }

    MovieAdepter movieAdepter3 = new MovieAdepter(this, genreList, this);
    genreRV.setAdapter(movieAdepter3);
    genreRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    String[] genres = {"액션", "스릴러", "드라마"};
    Spinner genreSpinner = findViewById(R.id.main_genre);

    ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, genres);
    genreAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
    genreSpinner.setAdapter(genreAdapter);
    genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        View selectedView = adapterView.getSelectedView();
        if (selectedView instanceof TextView) {
          ((TextView) selectedView).setTextColor(Color.WHITE);
        }
        View firstChild = adapterView.getChildAt(0);
        if (firstChild != null && firstChild instanceof TextView) {
          ((TextView) firstChild).setTextSize(20);
        }


        genreList.clear();
        if (genres[position].equals("액션")) {
          for (int i = 0; i < VClistMovies.size(); i++) {
            for (int j = 0; j < VClistMovies.get(i).getGenres().length; j++)
              if (VClistMovies.get(i).getGenres()[j].equals("Action")) {
                genreList.add(VClistMovies.get(i));
              }
            if (genreList.size() >= 20) break;
          }
        }

        if (genres[position].equals("드라마")) {
          for (int i = 0; i < VClistMovies.size(); i++) {
            for (int j = 0; j < VClistMovies.get(i).getGenres().length; j++)
              if (VClistMovies.get(i).getGenres()[j].equals("Drama")) {
                genreList.add(VClistMovies.get(i));
              }
            if (genreList.size() >= 20) break;
          }
        }

        if (genres[position].equals("스릴러")) {
          for (int i = 0; i < VClistMovies.size(); i++) {
            for (int j = 0; j < VClistMovies.get(i).getGenres().length; j++)
              if (VClistMovies.get(i).getGenres()[j].equals("Thriller")) {
                genreList.add(VClistMovies.get(i));
              }
            if (genreList.size() >= 20) break;
          }
        }

        MovieAdepter movieAdepter3 = new MovieAdepter(MainActivity.this, genreList, MainActivity.this);
        genreRV.setAdapter(movieAdepter3);
        genreRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }

    });

    TextView search = findViewById(R.id.tab_search);
    search.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
      }
    });

    TextView detail = findViewById(R.id.tab_detail);
    detail.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
        Movie movie = VAlistMovies.get(1);
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
        startActivity(intent);
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

    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, movieImageView, "sharedName");

    startActivity(intent, options.toBundle());

  }

}