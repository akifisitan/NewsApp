package com.akifisitan.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

public class SeeComments extends AppCompatActivity {

    ProgressBar prg;
    RecyclerView recView;
    Button btnAddComment;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Comment> data = (List<Comment>) msg.obj;
            CommentAdapter adp = new CommentAdapter(SeeComments.this, data);
            recView.setAdapter(adp);
            recView.setVisibility(View.VISIBLE);
            prg.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_comments);
        prg = findViewById(R.id.prgBarListSeeComments);
        recView = findViewById(R.id.recViewListSeeComments);
        btnAddComment = findViewById(R.id.btnAddComment);
        // Set button's on click listener
        btnAddComment.setOnClickListener(view -> {
            Intent i = new Intent(SeeComments.this, PostComment.class);
            News selectedNews = (News)getIntent().getSerializableExtra("selectedNews");
            i.putExtra("selectedNews", selectedNews);
            startActivity(i);
        });

        recView.setLayoutManager(new LinearLayoutManager(this));
        prg.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);
        // Get the comments
        CommentRepository repo = new CommentRepository();
        News news = (News) getIntent().getSerializableExtra("selectedNews");
        repo.getCommentsByNewsId(((NewsApp) getApplication()).srv, dataHandler, news.getId());
        // Display the toolbar & set the title
        Toolbar toolbar = findViewById(R.id.tbarSeeComments);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Comments");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the comments when coming back from posting a comment
        CommentRepository repo = new CommentRepository();
        News news = (News) getIntent().getSerializableExtra("selectedNews");
        repo.getCommentsByNewsId(((NewsApp) getApplication()).srv, dataHandler, news.getId());
    }

}