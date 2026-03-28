package com.example.tripbuddy.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripbuddy.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private final String[] photoUrls;

    public GalleryAdapter(String[] photoUrls) {
        this.photoUrls = photoUrls != null ? photoUrls : new String[0];
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        if (position < photoUrls.length) {
            // Use a default Android drawable as a fallback; replace with custom image logic later
            holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery); // Fallback image
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), FullScreenPhotoActivity.class);
                intent.putExtra("photoPosition", position);
                intent.putExtra("photoUrl", photoUrls[position]); // Pass the photo URL/path
                holder.itemView.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return photoUrls.length;
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        GalleryViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.galleryImage);
            if (imageView == null) throw new IllegalStateException("galleryImage not found");
        }
    }
}