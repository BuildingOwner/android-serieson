package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter2 extends RecyclerView.Adapter<MovieAdapter2.MyViewHolder2> {
  Context context;
  List<Movie> mData;
  MovieItemClickListener movieItemClickListener;

  public MovieAdapter2(Context context, List<Movie> mData, MovieItemClickListener listener) {
    this.context = context;
    this.mData = mData;
    movieItemClickListener = listener;
  }

  @NonNull
  @Override
  public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(context).inflate(R.layout.item_movie2, parent, false);
    return new MyViewHolder2(view);

  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
    holder.searchPoster.setImageResource(R.drawable.leon);
    holder.searchTitle.setText(mData.get(position).getTitle());
    holder.searchId.setText("ID " + mData.get(position).getId());
    holder.searchOverview.setText(mData.get(position).getOverview());
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  public class MyViewHolder2 extends RecyclerView.ViewHolder {

    private ImageView searchPoster;
    private TextView searchTitle;
    private TextView searchId;
    private TextView searchOverview;

    public MyViewHolder2(@NonNull View itemView) {
      super(itemView);

      searchPoster = itemView.findViewById(R.id.search_poster);
      searchTitle = itemView.findViewById(R.id.search_title);
      searchId = itemView.findViewById(R.id.search_id);
      searchOverview = itemView.findViewById(R.id.search_overview);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          movieItemClickListener.onMovieClick(mData.get(getAdapterPosition()), searchPoster);
        }
      });

    }
  }
}
