package com.example.baca_pintar.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<Item> items;

    public Adapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleFirstColl.setText(items.get(position).getTitle());
        holder.authorsFirstColl.setText(items.get(position).getAuthors());

        //if((position + 1) <= (items.size() - 1)) {
           //holder.titleSecondColl.setText(items.get(position+1).getTitle());
            //holder.authorsSecondColl.setText(items.get(position+1).getAuthors());
        //}
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
