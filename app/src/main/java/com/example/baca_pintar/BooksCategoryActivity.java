package com.example.baca_pintar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.baca_pintar.recycler_view.Adapter;
import com.example.baca_pintar.recycler_view.Item;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BooksCategoryActivity extends AppCompatActivity {

    public interface getDataCallback {
        void onSuncces();
    }
    List<Item> myItems = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_category);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Called when the user submits the query (presses Enter)
                // Handle the query submission here
                getData(query);
                Toast.makeText(BooksCategoryActivity.this, "Query submitted: " + query, Toast.LENGTH_SHORT).show();
                return true; // Return true to indicate that the query has been handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the query text changes (e.g., user typing)
                // Handle the text change here if needed
                return false; // Return false if you want to perform more actions on text change
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.item_2).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_1) {
                    Intent navigateToHome = new Intent(BooksCategoryActivity.this, MainPageActivity.class);
                    startActivity(navigateToHome);
                    return true;
                }
                return false;
            }
        });

    }

    private void getData(String userSearch) {
        RequestQueue queue = Volley.newRequestQueue(this);
        List<Item> myItems = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=" + userSearch + "&key=AIzaSyAyUF8CxetYl7wHOgbk9ynTMBbkKZlkPVs",
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

                            for(int i = 0; i < objectOfArray.length(); i++) {
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
                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setLayoutManager(new GridLayoutManager(BooksCategoryActivity.this, 2));
                            recyclerView.setAdapter(new Adapter(getApplicationContext(), myItems));
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
