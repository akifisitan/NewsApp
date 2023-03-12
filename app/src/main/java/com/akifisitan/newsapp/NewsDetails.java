package com.akifisitan.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

// Check news details
public class NewsDetails extends AppCompatActivity {
    // Access ui components
    ImageView imgView;
    TextView txtTitleDetails, txtDateDetails, txtTextDetails;
    Button btnSeeComments;
    ProgressBar prgBar;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            News news = (News) msg.obj;
            txtTitleDetails.setText(news.getTitle());
            txtDateDetails.setText(news.getDate());
            txtTextDetails.setText(news.getText());
            // Make data visible when everything is ready
            prgBar.setVisibility(View.INVISIBLE);
            txtTitleDetails.setVisibility(View.VISIBLE);
            txtDateDetails.setVisibility(View.VISIBLE);
            txtTextDetails.setVisibility(View.VISIBLE);
            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApp)getApplication()).srv, imgHandler, news.getImagePath());
            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Bitmap img = (Bitmap) msg.obj;
            imgView.setImageBitmap(img);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        // Get the selected news from the main activity intent
        News selectedNews = (News)getIntent().getSerializableExtra("selectedNews");
        // Set ui components
        imgView = findViewById(R.id.imgNewsDetails);
        txtTitleDetails = findViewById(R.id.txtTitleDetails);
        txtDateDetails = findViewById(R.id.txtDateDetails);
        txtTextDetails = findViewById(R.id.txtTextDetails);
        btnSeeComments = findViewById(R.id.btnShowComments);
        prgBar = findViewById(R.id.prgBarDetails);
        btnSeeComments.setBackgroundColor(Color.argb(255, 255, 87, 51));
        // Access the comments activity via the button
        btnSeeComments.setOnClickListener(view -> {
            Intent i = new Intent(NewsDetails.this, SeeComments.class);
            News selectedNews1 = (News)getIntent().getSerializableExtra("selectedNews");
            i.putExtra("selectedNews", selectedNews1);
            startActivity(i);
        });
        // Download news' data
        NewsRepository repo = new NewsRepository();
        repo.getNewsById(((NewsApp)getApplication()).srv, dataHandler, selectedNews.getId());
        // Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbarNewsDetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(selectedNews.getCategoryName());
    }

    // Finish activity if back button is pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return true;
    }
}