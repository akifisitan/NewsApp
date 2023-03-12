package com.akifisitan.newsapp;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

// Repo layer for downloading comment data
public class CommentRepository {

    // Get all comments for a specific news from its id
    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int id) {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + id);
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while((line=reader.readLine())!=null)
                    buffer.append(line);
                conn.disconnect();
                JSONObject result = new JSONObject(buffer.toString());
                JSONArray arr = result.getJSONArray("items");
                List<Comment> data = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);
                    Comment comment = new Comment(
                            current.getInt("id"),
                            current.getInt("news_id"),
                            current.getString("text"),
                            current.getString("name")
                    );
                    data.add(comment);
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

    // Post comment from the Post Comment activity
    public void postComment(ExecutorService srv, Handler uiHandler, Comment comment) {
        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");
                JSONObject outputData  = new JSONObject();
                outputData.put("name", comment.getName());
                outputData.put("text", comment.getText());
                outputData.put("news_id", comment.getNewsId());
                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());
                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();
                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line;
                while((line=reader.readLine())!=null)
                    buffer.append(line);

                JSONObject retVal = new JSONObject(buffer.toString());
                conn.disconnect();
                int retValStr = retVal.getInt("serviceMessageCode");

                Message msg = new Message();
                msg.obj = retValStr;

                uiHandler.sendMessage(msg);

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
