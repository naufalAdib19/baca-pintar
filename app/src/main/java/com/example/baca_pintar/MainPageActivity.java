package com.example.baca_pintar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainPageActivity extends AppCompatActivity {

    String dataSource = "https://www.googleapis.com/books/v1/volumes?q=laskar&key=AIzaSyAyUF8CxetYl7wHOgbk9ynTMBbkKZlkPVs";
    JSONObject firstBook;
    TextView firstBookTitle;
    ImageView firstBookView;
    JSONObject secondBook;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_2) {
                    Intent navigateToBooksCat = new Intent(MainPageActivity.this, BooksCategoryActivity.class);
                    startActivity(navigateToBooksCat);
                    return true;
                }
                return false;
            }
        });

        getData();
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataSource,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("api", "onResponse" + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray data = jsonObject.getJSONArray("items");

                            //Set FirstBook Image
                            firstBook = (JSONObject) data.get(0);
                            firstBookView = (ImageView) findViewById(R.id.firstBook);
                            String firstBookImage = firstBook.getJSONObject("volumeInfo").getJSONObject("imageLinks").get("thumbnail").toString().replace("http", "https");
                            Picasso.get().load(firstBookImage).fit().into(firstBookView);

                            //Set First Book title
                            firstBookTitle = (TextView) findViewById(R.id.firstBookTitle);
                            String firstBookTitleText = firstBook.getJSONObject("volumeInfo").get("title").toString();
                            firstBookTitle.setText(firstBookTitleText);

                            //Set First Book Author
                            TextView firstBookAuthors = (TextView) findViewById(R.id.firstBookAuthor);
                            JSONArray firstBookAuthorsData = firstBook.getJSONObject("volumeInfo").getJSONArray("authors");
                            for(int i = 0; i < firstBookAuthorsData.length(); i++) {
                                firstBookAuthors.setText(firstBookAuthorsData.get(i).toString() + "\n");
                            }

                            //Set Second Image
                            secondBook = (JSONObject) data.get(1);
                            ImageView secondBookView = (ImageView) findViewById(R.id.secondBook);
                            String secondBookImage = secondBook.getJSONObject("volumeInfo").getJSONObject("imageLinks").get("thumbnail").toString().replace("http", "https");
                            Picasso.get().load(secondBookImage).fit().into(secondBookView);

                            //Set Second Book Title
                            TextView secondBookTitle = (TextView) findViewById(R.id.secondBookTitle);
                            String secondBookTitleText = secondBook.getJSONObject("volumeInfo").get("title").toString();
                            secondBookTitle.setText(secondBookTitleText);

                            //Set Second Book Author
                            TextView secondBookAuthors = (TextView) findViewById(R.id.secondBookAuthor);
                            JSONArray secondBookAuthorsData = secondBook.getJSONObject("volumeInfo").getJSONArray("authors");
                            for(int i = 0; i < secondBookAuthorsData.length(); i++) {
                                secondBookAuthors.setText(secondBookAuthorsData.get(i).toString());
                            }
                            //Log.d("api", "" + firstBookAuthorsData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse" + error.toString());
            }
        });
        queue.add(stringRequest);
    }

}


