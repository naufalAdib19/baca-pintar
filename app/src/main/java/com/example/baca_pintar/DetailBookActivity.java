package com.example.baca_pintar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.baca_pintar.recycler_view_user.UserItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailBookActivity extends AppCompatActivity {
    ImageView booksThumbnail, backButton;
    TextView booksTitle, booksDescription, authorsList;

    //Firebase Config
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseUser user = mAuth.getCurrentUser();
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static DocumentReference docRef = db.collection("user").document(user.getUid().toString());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_book);

        backButton = (ImageView) findViewById(R.id.backButton);

        // set back button listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Intent prevIntent = null;
                String prevContext = intent.getExtras().getString("prevContext");

                //conditional intent
                if(prevContext.equals("MainPage")) {
                    prevIntent = new Intent(DetailBookActivity.this, MainPageActivity.class);
                } else {
                    prevIntent = new Intent(DetailBookActivity.this, BooksCategoryActivity.class);
                }
                startActivity(prevIntent);
            }
        });

        //get books id from prev intent
        Intent intent = getIntent();
        String booksId = intent.getExtras().getString("booksId");

        //get books based on booksId
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
                            String id = jsonObject.get("id").toString();
                            JSONArray authors = jsonObject.getJSONObject("volumeInfo").getJSONArray("authors");
                            List<String> listAuthor = new ArrayList<>();
                            //String authorStringify = authors.toString();
                            //String setAuthors = authors.length() > 1 ? authorStringify.substring(1, authors.length() - 2) : authorStringify.substring(1, authors.length()-1);
                            //Log.d("adibKeys", title);

                            booksThumbnail = (ImageView) findViewById(R.id.detail_book_image);
                            Picasso.get().load(img).fit().into(booksThumbnail);

                            booksTitle = (TextView) findViewById(R.id.detail_book_title);
                            booksTitle.setText(title);

                            authorsList = (TextView) findViewById(R.id.detail_book_authors);
                            String strings = "";
                            for(int i = 0; i < authors.length(); i++) {
                                listAuthor.add(authors.get(i).toString());
                                strings += authors.get(i).toString();
                                 if(i < authors.length()-1) {
                                     strings += ", ";
                                 }
                            }
                            authorsList.setText(strings);

                            booksDescription = (TextView) findViewById(R.id.detail_book_desc);
                            booksDescription.setText(desc);

                            UserItem userItem = new UserItem(img, title,listAuthor, id);

                            addBooks(userItem);

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

    public void addBooks(UserItem userItems) {

        Button button = (Button) findViewById(R.id.add_to_fav_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            List<Map<String, Object>> data;

                            if(documentSnapshot.get("userBooks") != null) {
                                data = (List<Map<String, Object>>) documentSnapshot.get("userBooks");
                            } else {
                                data = new ArrayList<>();
                            }

                            Map<String, Object> mapper = new HashMap<>();
                            mapper.put("title", userItems.getTitle());
                            mapper.put("booksID", userItems.getId());
                            mapper.put("thumbnail", userItems.getThumbnail());
                            mapper.put("authors", userItems.getAuthors());
                            data.add(mapper);
                            rewriteData(data);
                            Toast.makeText(DetailBookActivity.this, "Success Add Book", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(DetailBookActivity.this, MainPageActivity.class);
                            //DetailBookActivity.this.finish();
                            startActivity(intent);
                            //Log.d("tesDenDen", data.toString());
                        }
                    }
                });

            }
        });
    }

    public void rewriteData(List<Map<String, Object>> userBooks) {
        docRef.update("userBooks", userBooks).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("userBooks", "Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("userBooks", e.toString());
            }
        });
    }

}
