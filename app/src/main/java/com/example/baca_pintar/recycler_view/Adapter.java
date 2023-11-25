package com.example.baca_pintar.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
        if( getTotalWords(items.get(position).getTitle()) <= 7 && getTotalWords(items.get(position).getTitle()) > 4) {
            holder.titleFirstColl.setTextSize(16);
        } else if(getTotalWords(items.get(position).getTitle()) > 7){
            holder.titleFirstColl.setTextSize(12);
        }

        if(!items.get(position).getThumbnail().equals("")) {
            Picasso.get().load(items.get(position).getThumbnail()).fit().into(holder.thumbnailFirstColl);
        }

        String authorList = "";
        for(int i = 0; i < items.get(position).getAuthors().size(); i++) {
            authorList += items.get(position).getAuthors().get(i);
            if(i != items.get(position).getAuthors().size() - 1) {
                authorList += ", ";
            }
        }

        holder.authorsFirstColl.setText(authorList);


        //if((position + 1) <= (items.size() - 1)) {
           //holder.titleSecondColl.setText(items.get(position+1).getTitle());
            //holder.authorsSecondColl.setText(items.get(position+1).getAuthors());
        //}
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
