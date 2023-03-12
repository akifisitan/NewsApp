package com.akifisitan.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {
    // Download all the news
    public void getAllNews(ExecutorService srv, Handler uiHandler) {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");
                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while((line=reader.readLine())!=null)
                    buffer.append(line);
                JSONObject result = new JSONObject(buffer.toString());
                conn.disconnect();
                JSONArray arr = result.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    LocalDateTime dateTime = LocalDateTime.parse(current.getString("date"), formatter);
                    // Use a custom formatter to display the date and time in the desired format
                    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDate = dateTime.format(customFormatter);
                    News news = new News(
                            current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            formattedDate,
                            current.getString("image"),
                            current.getString("categoryName"));
                    data.add(news);
                }
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
    // Download news by category
    public void getNewsByCategoryId(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + id);
                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while((line=reader.readLine())!=null)
                    buffer.append(line);
                JSONObject result = new JSONObject(buffer.toString());
                conn.disconnect();
                JSONArray arr = result.getJSONArray("items");
                List<News> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    LocalDateTime dateTime = LocalDateTime.parse(current.getString("date"), formatter);
                    // Use a custom formatter to display the date and time in the desired format
                    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDate = dateTime.format(customFormatter);
                    News news = new News(
                            current.getInt("id"),
                            current.getString("title"),
                            current.getString("text"),
                            formattedDate,
                            current.getString("image"),
                            current.getString("categoryName"));
                    data.add(news);
                }
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
    // Download news by their id
    public void getNewsById(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + id);
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while((line=reader.readLine())!=null)
                    buffer.append(line);
                conn.disconnect();
                JSONObject result = new JSONObject(buffer.toString());
                JSONArray arr = result.getJSONArray("items");
                JSONObject current = arr.getJSONObject(0);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime dateTime = LocalDateTime.parse(current.getString("date"), formatter);
                // Use a custom formatter to display the date and time in the desired format
                DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = dateTime.format(customFormatter);
                News news = new News(
                        current.getInt("id"),
                        current.getString("title"),
                        current.getString("text"),
                        formattedDate,
                        current.getString("image"),
                        current.getString("categoryName")
                );
                Message msg = new Message();
                msg.obj = news;
                uiHandler.sendMessage(msg);
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
    // Download image from a specified path
    public void downloadImage(ExecutorService srv, Handler uiHandler, String path) {
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());
                conn.disconnect();
                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
