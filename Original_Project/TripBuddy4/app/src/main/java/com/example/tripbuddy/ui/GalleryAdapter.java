package com.example.tripbuddy.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripbuddy.R;

import com.example.tripbuddy.databinding.ItemGalleryBinding;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private final String[] photoUrls;

    public GalleryAdapter(String[] photoUrls) {
        this.photoUrls = photoUrls != null ? photoUrls : new String[0];
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGalleryBinding binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GalleryViewHolder(binding);
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

                if (holder.itemView.getContext() instanceof android.app.Activity) {
                    android.app.ActivityOptions options = android.app.ActivityOptions.makeSceneTransitionAnimation(
                            (android.app.Activity) holder.itemView.getContext(),
                            holder.imageView,
                            "photoTransition");
                    holder.itemView.getContext().startActivity(intent, options.toBundle());
                } else {
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return photoUrls.length;
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        GalleryViewHolder(ItemGalleryBinding binding) {
            super(binding.getRoot());
            imageView = binding.galleryImage;
            if (imageView == null) throw new IllegalStateException("galleryImage not found");
        }
    }
}