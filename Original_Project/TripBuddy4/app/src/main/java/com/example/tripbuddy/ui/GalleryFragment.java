package com.example.tripbuddy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.data.db.AppDatabase;
import com.example.tripbuddy.data.model.Memory;
import com.example.tripbuddy.databinding.ActivityGalleryBinding;

import java.util.List;

public class GalleryFragment extends Fragment {
    private ActivityGalleryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityGalleryBinding.inflate(inflater, container, false);

        RecyclerView recyclerView = binding.galleryRecyclerView;
        android.widget.LinearLayout emptyStateLayout = binding.emptyStateLayout;

        if (recyclerView != null && getActivity() != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2)); // 2 columns

            new Thread(() -> {
                try {
                    AppDatabase db = AppDatabase.getInstance(getActivity());
                    List<Memory> memories = db.memoryDao().getAllMemories();
                    String[] photoUrls = memories.stream()
                            .filter(memory -> memory.imagePath != null && !memory.imagePath.isEmpty())
                            .map(memory -> memory.imagePath)
                            .toArray(String[]::new);
                    getActivity().runOnUiThread(() -> {
                        if (photoUrls.length == 0) {
                            recyclerView.setVisibility(View.GONE);
                            if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.VISIBLE);
                        } else {
                            if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            GalleryAdapter adapter = new GalleryAdapter(photoUrls);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            recyclerView.setVisibility(View.GONE);
                            if (emptyStateLayout != null) emptyStateLayout.setVisibility(View.VISIBLE);
                        });
                    }
                }
            }).start();
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
