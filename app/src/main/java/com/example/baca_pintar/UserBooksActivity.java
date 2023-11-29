package com.example.baca_pintar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.recycler_view.Adapter;
import com.example.baca_pintar.recycler_view_user.UserAdapter;
import com.example.baca_pintar.recycler_view_user.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_books);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.item_3).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_1) {
                    Intent navigateToHome = new Intent(UserBooksActivity.this, MainPageActivity.class);
                    startActivity(navigateToHome);
                    return true;
                } else if(item.getItemId() == R.id.item_2) {
                    Intent navigateToUserBooks = new Intent(UserBooksActivity.this, BooksCategoryActivity.class);
                    startActivity(navigateToUserBooks);
                }
                return false;
            }
        });

        getUserData();

    }

    private void getUserData() {
        List<UserItem> userBooks = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(user.getUid().toString());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        try {
                            JSONObject response = new JSONObject(document.getData());
                            //Log.d("userBooks", response.getJSONArray("userBooks").toString());

                            //data properties
                            String title = "";
                            String id = "";
                            List<String> authors;
                            String image = "";

                            JSONArray data = response.getJSONArray("userBooks");
                            JSONObject books = null;
                            for(int i = 0; i < data.length(); i++) {
                                books = (JSONObject) data.get(i);
                                title = !books.isNull("title") ? books.get("title").toString() : "Untitled";
                                image = !books.isNull("thumbnail") ? books.get("thumbnail").toString() : "";
                                id = books.get("booksID").toString();

                                if(!books.isNull("authors")) {
                                    authors = new ArrayList<>();
                                    for(int j = 0; j < books.getJSONArray("authors").length(); j++) {
                                        authors.add(books.getJSONArray("authors").get(j).toString());
                                    }
                                } else {
                                    authors = new ArrayList<>();
                                    authors.add("unknown");
                                }
                                userBooks.add(new UserItem(image, title, authors, id));
                            }
                            RecyclerView recyclerView = findViewById(R.id.user_books_recyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(UserBooksActivity.this));
                            recyclerView.setAdapter(new UserAdapter(getApplicationContext(), userBooks));

                        } catch (Exception e) {
                            Log.e("errorKK", e.toString());
                        }
                    } else {
                        Log.d("usersData", "No such document");
                    }
                } else {
                    Log.d("usersData", "get failed with ", task.getException());
                }
            }
        });

    }

}
