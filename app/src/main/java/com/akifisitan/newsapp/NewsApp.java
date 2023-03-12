package com.akifisitan.newsapp;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Store the executor service to access from anywhere
public class NewsApp extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();
}
