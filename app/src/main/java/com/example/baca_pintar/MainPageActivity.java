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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.baca_pintar.recycler_view.Adapter;
import com.example.baca_pintar.recycler_view.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

        //set click listener on navigation menu
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

        //get data from API
        getData();
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        List<Item> myItems = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=laskar&key=AIzaSyAyUF8CxetYl7wHOgbk9ynTMBbkKZlkPVs",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("api", "onResponse" + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray objectOfArray = jsonObject.getJSONArray("items");
                            JSONObject data;

                            //data's properties
                            String title = "";
                            List<String> authors;
                            String image = "";
                            String id = "";

                            for(int i = 0; i < 2; i++) {
                                data = (JSONObject) objectOfArray.get(i);
                                title = !data.getJSONObject("volumeInfo").isNull("title") ? data.getJSONObject("volumeInfo").get("title").toString() : "Untitled";
                                image = !data.getJSONObject("volumeInfo").isNull("imageLinks") ? data.getJSONObject("volumeInfo").getJSONObject("imageLinks").get("thumbnail").toString().replace("http", "https") : "";
                                id = data.get("id").toString();

                                JSONArray listAuthor = !data.getJSONObject("volumeInfo").isNull("authors") ? data.getJSONObject("volumeInfo").getJSONArray("authors") : null;
                                if(listAuthor != null) {
                                    authors = new ArrayList<>();
                                    for(int j = 0; j < listAuthor.length(); j++) {
                                        authors.add(listAuthor.get(j).toString());
                                    }
                                } else {
                                    authors = new ArrayList<>();
                                    authors.add("unknown");
                                }
                                myItems.add(new Item(image, title, authors, id));
                            }
                            //Log.d("ddd", myItems.get(3).getTitle());

                            //invoke recycler view to recent activity
                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setLayoutManager(new GridLayoutManager(MainPageActivity.this, 2));
                            recyclerView.setAdapter(new Adapter(getApplicationContext(), myItems, "MainPage"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
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


