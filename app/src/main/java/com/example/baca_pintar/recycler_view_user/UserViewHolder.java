package com.example.baca_pintar.recycler_view_user;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    ImageView thumbnail;
    TextView title, authors;
    Button button;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.user_books_thumbnail);
        title = (TextView) itemView.findViewById(R.id.user_books_title);
        authors = (TextView) itemView.findViewById(R.id.user_books_authors);
        button = (Button) itemView.findViewById(R.id.deleteButton);
    }
}
