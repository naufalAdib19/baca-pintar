package com.example.baca_pintar.recycler_view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView thumbnailFirstColl, thumbnailSecondColl;
    TextView titleFirstColl, titleSecondColl, authorsFirstColl, authorsSecondColl;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        thumbnailFirstColl = itemView.findViewById(R.id.thumbnail_firstColl);
        titleFirstColl = itemView.findViewById(R.id.title_firstColl);
        authorsFirstColl = itemView.findViewById(R.id.authors_firstColl);
    }

}
