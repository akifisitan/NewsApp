package com.akifisitan.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;

public class PostComment extends AppCompatActivity {

    TextView txtOutput;
    EditText editTextName, editTextComment;
    Button btnPostComment;
    ProgressBar prg;

    Handler postCommentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            int retVal = (int) msg.obj;
            prg.setVisibility(View.INVISIBLE);
            // Finish the activity and show the posted comment if the comment is sent successfully
            if (retVal == 1)
                finish();
            else
                txtOutput.setText(R.string.comment_error);
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);
        // Set the toolbar and title
        Toolbar toolbar = findViewById(R.id.tbarPostComment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Post Comment");
        // Get the view objects
        txtOutput = findViewById(R.id.txtResultPostComment);
        prg = findViewById(R.id.prgPostComment);
        editTextName = findViewById(R.id.editTextPostCommentName);
        editTextComment = findViewById(R.id.editTextPostCommentComments);
        btnPostComment = findViewById(R.id.btnPostComment);

        // Set on click listener
        btnPostComment.setOnClickListener(v -> {
            String commentName = editTextName.getText().toString();
            String commentText = editTextComment.getText().toString();
            News selectedNews = (News)getIntent().getSerializableExtra("selectedNews");
            int selectedNewsId = selectedNews.getId();
            txtOutput.setText("");
            if (commentName.isEmpty() || commentText.isEmpty()) {
                prg.setVisibility(View.INVISIBLE);
                txtOutput.setText(R.string.empty_comment);
            }
            else {
                prg.setVisibility(View.VISIBLE);
                ExecutorService srv = ((NewsApp)getApplication()).srv;
                CommentRepository repo = new CommentRepository();
                Comment comment = new Comment(selectedNewsId, commentText, commentName);
                repo.postComment(srv, postCommentHandler, comment);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DEV", "On destroy was called PostComment.");
    }
}