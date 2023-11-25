package com.example.baca_pintar.recycler_view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.BooksCategoryActivity;
import com.example.baca_pintar.DetailBookActivity;
import com.example.baca_pintar.MainActivity;
import com.example.baca_pintar.MainPageActivity;
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
        if( getTotalWords(items.get(position).getTitle()) <= 7 && getTotalWords(items.get(position).getTitle()) > 4) {
            holder.titleFirstColl.setTextSize(16);
        } else if(getTotalWords(items.get(position).getTitle()) > 7){
            holder.titleFirstColl.setTextSize(12);
        }

        if(!items.get(position).getThumbnail().equals("")) {
            Picasso.get().load(items.get(position).getThumbnail()).fit().into(holder.thumbnailFirstColl);
        }

        holder.thumbnailFirstColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailBookActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("booksId", items.get(holder.getAdapterPosition()).getId());
                context.startActivity(i);
            }
        });

        String authorList = "";
        for(int i = 0; i < items.get(position).getAuthors().size(); i++) {
            authorList += items.get(position).getAuthors().get(i);
            if(i != items.get(position).getAuthors().size() - 1) {
                authorList += ", ";
            }
        }

        holder.authorsFirstColl.setText(authorList);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getTotalWords(String words) {
        String[] separated = words.split(" ");
        return separated.length;
    }

}
