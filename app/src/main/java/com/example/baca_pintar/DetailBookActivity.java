package com.example.baca_pintar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailBookActivity extends AppCompatActivity {
    ImageView booksThumbnail, backButton;
    TextView booksTitle, booksDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book);

        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prevIntent = new Intent(DetailBookActivity.this, BooksCategoryActivity.class);
                startActivity(prevIntent);
            }
        });

        Intent intent = getIntent();
        String booksId = intent.getExtras().getString("booksId");

        getBooks(booksId);
    }

    public void getBooks(String booksId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes/" + booksId +"?key=AIzaSyAyUF8CxetYl7wHOgbk9ynTMBbkKZlkPVs",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("api", "onResponse" + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String img = jsonObject.getJSONObject("volumeInfo").getJSONObject("imageLinks").get("thumbnail").toString().replace("http", "https");
                            String title = jsonObject.getJSONObject("volumeInfo").get("title").toString();
                            String desc = jsonObject.getJSONObject("volumeInfo").get("description").toString();
                            Log.d("adibKeys", title);

                            booksThumbnail = (ImageView) findViewById(R.id.detail_book_image);
                            Picasso.get().load(img).fit().into(booksThumbnail);

                            booksTitle = (TextView) findViewById(R.id.detail_book_title);
                            booksTitle.setText(title);

                            booksDescription = (TextView) findViewById(R.id.detail_book_desc);
                            booksDescription.setText(desc);

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
