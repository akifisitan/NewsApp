package com.akifisitan.newsapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

// Sports news category
public class FragmentSports extends Fragment {
    // Access ui components
    ProgressBar prgBar;
    RecyclerView recView;
    int categoryId = 3;

    // Create handler for async task
    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<News> data = (List<News>) msg.obj;
            NewsAdapter adp = new NewsAdapter(getContext(), data);
            recView.setAdapter(adp);
            recView.setVisibility(View.VISIBLE);
            prgBar.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sports, null);
        // Set ui components
        prgBar = v.findViewById(R.id.prgBarSports);
        recView = v.findViewById(R.id.recViewSports);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        prgBar.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);

        // Access repository and get list data, send it to the handler
        NewsRepository repo = new NewsRepository();
        repo.getNewsByCategoryId(((NewsApp) getActivity().getApplication()).srv, dataHandler,
                categoryId);
        return v ;
    }
}