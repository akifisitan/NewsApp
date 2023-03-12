package com.akifisitan.newsapp;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Economics news category
public class FragmentEconomics extends Fragment {
    // Access ui components
    ProgressBar prgBar;
    RecyclerView recView;
    int categoryId = 1;

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
        View v = inflater.inflate(R.layout.fragment_economics, null);
        // Set ui components
        prgBar = v.findViewById(R.id.prgBarEconomics);
        recView = v.findViewById(R.id.recViewEconomics);
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