package com.example.final_project;

import android.app.ActivityOptions;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity implements MovieItemClickListener {

  private Movie movie;
  private List<Movie> VClistMovies;
  private LinearLayout reviewLayout;
  private LinearLayout userReview;
  private ReviewHandler reviewHandler;

  private static class ReviewHandler extends Handler {
    private final WeakReference<MovieDetailActivity> activityWeakReference;

    public ReviewHandler(MovieDetailActivity activity) {
      activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
      MovieDetailActivity activity = activityWeakReference.get();
      if (activity != null) {
        activity.handleReviewMessage((List<Review>) msg.obj);
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);

    reviewLayout = findViewById(R.id.review_layout);
    reviewHandler = new ReviewHandler(this);


    Intent intent = getIntent();
    movie = (Movie) intent.getSerializableExtra("movie");
    VClistMovies = (List<Movie>) intent.getSerializableExtra("VClistMovies");

    ImageView movieImg = findViewById(R.id.detail_movie_img);
    movieImg.setImageResource(R.drawable.leon);

    TextView title = findViewById(R.id.detail_title);
    title.setText(movie.getTitle());

    TextView va = findViewById(R.id.detile_vote_average);
    va.setText(String.valueOf(movie.getVoteAverage()));

    TextView year = findViewById(R.id.detail_year);
    year.setText(String.valueOf(movie.getRelease()));

    TextView runningTime = findViewById(R.id.detail_time);
    runningTime.setText(String.valueOf(movie.getRuntime()) + "분");

    TextView director = findViewById(R.id.detail_director);
    director.setText(movie.getDirector());

    TextView actor = findViewById(R.id.detail_actor);
    String[] actors = movie.getCast();
    String tmp = Arrays.toString(actors);
    actor.setText(tmp.substring(4, tmp.length() - 4));
    actor.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (actor.getLineCount() == 1) actor.setMaxLines(1000);
        else actor.setMaxLines(1);
      }
    });

    TextView overview = findViewById(R.id.detail_overview);
    tmp = movie.getOverview();
    overview.setText(tmp.substring(1, tmp.length() - 1));

    overview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (overview.getLineCount() == 2) overview.setMaxLines(1000);
        else overview.setMaxLines(2);
      }
    });

    userReview = findViewById(R.id.user_review);
    EditText userInput = findViewById(R.id.user_review_input);
    RatingBar rating = findViewById(R.id.ratingBar);
    TextView userReviewButton = findViewById(R.id.user_review_button);
    userReviewButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(MovieDetailActivity.this);
        View reviewItemView = inflater.inflate(R.layout.item_review, null);

        TextView userTextView = reviewItemView.findViewById(R.id.review_user);
        TextView dateTextView = reviewItemView.findViewById(R.id.review_date);
        TextView titleTextView = reviewItemView.findViewById(R.id.review_content);
        TextView pointTextView = reviewItemView.findViewById(R.id.review_point);

        userTextView.setText("유저");
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        String formattedDate = formatter.format(currentDate);
        dateTextView.setText(formattedDate);
        titleTextView.setText(userInput.getText().toString());
        pointTextView.setText("⭐ " + (int)(rating.getRating()*2) + " / 10");

        userReview.addView(reviewItemView, 0);

      }
    });

    ArrayList<Movie> relateMovies = (ArrayList<Movie>) intent.getSerializableExtra("relateMovies");
    RecyclerView MoviePopularRV = findViewById(R.id.Rv_movies_relate);
    MovieAdepter movieAdepter2 = new MovieAdepter(this, relateMovies, this);
    MoviePopularRV.setAdapter(movieAdepter2);
    MoviePopularRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    Thread reviewThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          List<Review> reviews = ReviewController.crawlMovieReviews(movie.getImdbId());
          Message message = reviewHandler.obtainMessage();
          message.obj = reviews;
          reviewHandler.sendMessage(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    reviewThread.start();
  }

  private void handleReviewMessage(List<Review> reviews) {
    if (reviews != null) {
      for (Review review : reviews) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.WRAP_CONTENT,
          LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16);

        LayoutInflater inflater = LayoutInflater.from(this);
        View reviewItemView = inflater.inflate(R.layout.item_review, null);

        TextView userTextView = reviewItemView.findViewById(R.id.review_user);
        TextView dateTextView = reviewItemView.findViewById(R.id.review_date);
        TextView titleTextView = reviewItemView.findViewById(R.id.review_content);
        TextView pointTextView = reviewItemView.findViewById(R.id.review_point);

        userTextView.setText(review.getUser());
        dateTextView.setText(review.getDate());
        titleTextView.setText(review.getTitle());
        pointTextView.setText("⭐ " + review.getPoint() + " / 10");

        reviewLayout.addView(reviewItemView);

      }
    }
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

    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailActivity.this, movieImageView, "sharedName");

    startActivity(intent, options.toBundle());
  }

}