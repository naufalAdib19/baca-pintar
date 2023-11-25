package com.example.baca_pintar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.recycler_view.Adapter;
import com.example.baca_pintar.recycler_view.Item;

import java.util.ArrayList;
import java.util.List;

public class BooksCategoryActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_category);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Item> myItems = new ArrayList<Item>();
        myItems.add(new Item("", "Buku 1", "Penulis 1"));
        myItems.add(new Item("", "Buku 2", "Penulis 2"));
        myItems.add(new Item("", "Buku 3", "Penulis 3"));
        myItems.add(new Item("", "Buku 4", "Penulis 4"));
        myItems.add(new Item("", "Buku 5", "Penulis 5"));
        myItems.add(new Item("", "Buku 6", "Penulis 6"));
        myItems.add(new Item("", "Buku 7", "Penulis 7"));
        myItems.add(new Item("", "Buku 8", "Penulis 8"));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new Adapter(getApplicationContext(), myItems));

    }



}
