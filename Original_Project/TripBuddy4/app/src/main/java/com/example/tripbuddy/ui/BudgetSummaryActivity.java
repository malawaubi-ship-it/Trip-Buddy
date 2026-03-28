package com.example.tripbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.databinding.ActivityBudgetSummaryBinding;

public class BudgetSummaryActivity extends AppCompatActivity {
    private ActivityBudgetSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBudgetSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        double subtotal = getIntent().getDoubleExtra("subtotal", 0.0);
        double discount = getIntent().getDoubleExtra("discount", 0.0);
        double finalTotal = getIntent().getDoubleExtra("finalTotal", 0.0);

        binding.subtotalText.setText(String.format("Subtotal: $%.2f", subtotal));
        binding.discountText.setText(String.format("Discount: $%.2f", discount));
        binding.finalTotalText.setText(String.format("Final Total: $%.2f", finalTotal));

        // Back to Home button implementation
        binding.backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BudgetSummaryActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}