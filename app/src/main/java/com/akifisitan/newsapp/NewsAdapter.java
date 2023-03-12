package com.akifisitan.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

// News adapter for the recycler view
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context ctx;
    private final List<News> data;

    public NewsAdapter(Context ctx, List<News> data){
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.news_row_layout, parent, false);
        NewsViewHolder holder = new NewsViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final News currentNews = data.get(position);
        holder.txtListTitle.setText(currentNews.getTitle());
        holder.txtListDate.setText(currentNews.getDate());
        NewsApp app = (NewsApp) ((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(app.srv, currentNews.getImagePath());
        // Check out the news' details
        holder.row.setOnClickListener(v -> {
            Intent i = new Intent(ctx, NewsDetails.class);
            i.putExtra("selectedNews", currentNews);
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        // Access ui components
        TextView txtListDate, txtListTitle;
        ImageView imgList;
        ConstraintLayout row;
        boolean imageDownloaded = false;

        // Create new handler to handle images
        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                Bitmap img = (Bitmap)msg.obj;
                imgList.setImageBitmap(img);
                imageDownloaded = true;
                return true;
            }
        });

        // Bind ui components to the holder in the constructor
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtListDate = itemView.findViewById(R.id.txtListNewsDate);
            txtListTitle = itemView.findViewById(R.id.txtListNewsTitle);
            imgList = itemView.findViewById(R.id.imgListNews);
            row = itemView.findViewById(R.id.row_list_news);
        }

        // Download image from the image path
        public void downloadImage(ExecutorService srv, String path) {
            if (!imageDownloaded) {
                NewsRepository repo = new NewsRepository();
                repo.downloadImage(srv, imgHandler, path);
                Log.i("DEV", "Downloaded image with path: " + path);
            }
        }
        // End of NewsViewHolder inner class
    }
}
