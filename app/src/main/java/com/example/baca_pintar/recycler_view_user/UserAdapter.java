package com.example.baca_pintar.recycler_view_user;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baca_pintar.MainPageActivity;
import com.example.baca_pintar.R;
import com.example.baca_pintar.UserBooksActivity;
import com.example.baca_pintar.recycler_view.ViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    Context context;
    List<UserItem> items;

    public UserAdapter(Context context, List<UserItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_books, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());

        if(!items.get(position).getThumbnail().equals("")) {
            Picasso.get().load(items.get(position).getThumbnail()).fit().into(holder.thumbnail);
        }

        String strings = "";
        for(int i = 0; i < items.get(position).getAuthors().size(); i++) {
            strings += items.get(position).getAuthors().get(i);
            if(i < items.get(position).getAuthors().size() - 1) {
                strings += ", ";
            }
        }
        holder.authors.setText(strings);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("user").document(user.getUid().toString());

                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            List<Map<String, Object>> data = (List<Map<String, Object>>) documentSnapshot.get("userBooks");
                            data.remove(holder.getAdapterPosition());
                            docRef.update("userBooks", data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Success to Delete", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, MainPageActivity.class);
                                            context.startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Fail to Delete", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
