package com.example.tripbuddy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;
import com.example.tripbuddy.data.db.AppDatabase;
import com.example.tripbuddy.data.model.Trip;
import com.example.tripbuddy.databinding.ActivityTripPlanningBinding;
import java.util.HashMap;
import java.util.Map;

public class TripPlanningActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private ActivityTripPlanningBinding binding;
    private double totalCost = 0.0;
    private Map<String, Double> selectedActivities = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTripPlanningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Map<String, Double> activityCosts = new HashMap<>();
        activityCosts.put("Sightseeing", 100.0);
        activityCosts.put("Hiking", 50.0);
        activityCosts.put("Dining", 75.0);

        binding.sightseeingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateActivitySelection("Sightseeing", activityCosts.get("Sightseeing"), isChecked));
        binding.hikingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateActivitySelection("Hiking", activityCosts.get("Hiking"), isChecked));
        binding.diningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateActivitySelection("Dining", activityCosts.get("Dining"), isChecked));

        binding.customExpenseEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { calculateTotalCost(); }
        });

        binding.saveTripButton.setOnClickListener(v -> {
            v.performHapticFeedback(android.view.HapticFeedbackConstants.CONTEXT_CLICK);
            if (validateInputs()) {
                saveTrip();
            }
        });

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        binding.startDatePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH), null);
        binding.endDatePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH), null);

        loadPreviousTripData();
    }

    private void updateActivitySelection(String activity, Double cost, boolean isChecked) {
        if (isChecked) selectedActivities.put(activity, cost);
        else selectedActivities.remove(activity);
        calculateTotalCost();
    }

    private void calculateTotalCost() {
        double customExpense = 0.0;
        try {
            String expenseText = binding.customExpenseEditText.getText().toString().trim();
            customExpense = expenseText.isEmpty() ? 0.0 : Double.parseDouble(expenseText);
        } catch (NumberFormatException e) {
            customExpense = 0.0;
        }
        double activityCost = selectedActivities.values().stream().mapToDouble(Double::doubleValue).sum();
        totalCost = customExpense + activityCost;
        binding.totalCostTextView.setText(String.format("Total: $%.2f", totalCost));
    }

    private boolean validateInputs() {
        String destination = binding.destinationEditText.getText().toString().trim();
        if (destination.isEmpty()) {
            Toast.makeText(this, "Enter destination", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.startDatePicker.getDayOfMonth() > binding.endDatePicker.getDayOfMonth() &&
                binding.startDatePicker.getMonth() >= binding.endDatePicker.getMonth() &&
                binding.startDatePicker.getYear() >= binding.endDatePicker.getYear()) {
            Toast.makeText(this, "End date must be after start", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveTrip() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int tripCount = prefs.getInt("tripCount", 0);
        String activitiesString = String.join(", ", selectedActivities.keySet());
        Trip trip = new Trip(
                binding.destinationEditText.getText().toString().trim(),
                getDateFromPicker(binding.startDatePicker),
                getDateFromPicker(binding.endDatePicker),
                binding.notesEditText.getText().toString().trim(),
                totalCost,
                activitiesString
        );

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.tripDao().insert(trip);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("tripCount", tripCount + 1);
            editor.apply();
            double discount = (tripCount + 1 >= 3) ? totalCost * 0.1 : 0.0;
            double finalTotal = totalCost - discount;
            runOnUiThread(() -> {
                Intent intent = new Intent(this, BudgetSummaryActivity.class);
                intent.putExtra("subtotal", totalCost);
                intent.putExtra("discount", discount);
                intent.putExtra("finalTotal", finalTotal);
                startActivity(intent);
                finish();
            });
        }).start();
    }

    private String getDateFromPicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        return String.format("%02d/%02d/%d", day, month, year);
    }

    private void loadPreviousTripData() {
        // Placeholder for loading
    }
}