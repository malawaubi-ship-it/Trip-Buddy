package com.example.tripbuddy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;

public class BudgetSummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_summary);

        TextView subtotalText = findViewById(R.id.subtotalText);
        TextView discountText = findViewById(R.id.discountText);
        TextView finalTotalText = findViewById(R.id.finalTotalText);
        Button backToHomeButton = findViewById(R.id.backToHomeButton);

        double subtotal = getIntent().getDoubleExtra("subtotal", 0.0);
        double discount = getIntent().getDoubleExtra("discount", 0.0);
        double finalTotal = getIntent().getDoubleExtra("finalTotal", 0.0);

        subtotalText.setText(String.format("Subtotal: $%.2f", subtotal));
        discountText.setText(String.format("Discount: $%.2f", discount));
        finalTotalText.setText(String.format("Final Total: $%.2f", finalTotal));

        // Back to Home button implementation
        if (backToHomeButton != null) {
            backToHomeButton.setOnClickListener(v -> {
                Intent intent = new Intent(BudgetSummaryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            });
        } else {
            throw new IllegalStateException("Back to Home button not found in activity_budget_summary.xml");
        }
    }
}