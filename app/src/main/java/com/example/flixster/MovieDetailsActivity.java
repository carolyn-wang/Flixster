package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity{
    // the movie to display
    Movie movie;
    Context context;


    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivDetailPoster;
    Button ivBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivDetailPoster = (ImageView) findViewById(R.id.ivDetailPoster);
        ivBackButton = (Button) findViewById(R.id.ivBackButton);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        // add image
        String imageUrl = movie.getBackdropPath();
        Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .into(ivDetailPoster);

    }

    // button click command to go back to home page
    public void backToMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    // button click command to open Youtube player
    public void openTrailer(View v) {
        Intent intent = new Intent(this, MovieTrailerActivity.class);
        // serialize the movie using parceler, use its short name as a key
        intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
        // show the activity
        this.startActivity(intent);
    }

}