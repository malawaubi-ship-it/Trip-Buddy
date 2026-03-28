package com.example.tripbuddy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tripbuddy.R;
import com.example.tripbuddy.data.db.AppDatabase;
import com.example.tripbuddy.data.model.Trip;
import java.util.HashMap;
import java.util.Map;

public class TripPlanningActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "TripBuddyPrefs";
    private EditText destinationEditText, notesEditText, customExpenseEditText;
    private DatePicker startDatePicker, endDatePicker;
    private TextView totalCostTextView;
    private double totalCost = 0.0;
    private Map<String, Double> selectedActivities = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planning);

        destinationEditText = findViewById(R.id.destinationEditText);
        startDatePicker = findViewById(R.id.startDatePicker);
        endDatePicker = findViewById(R.id.endDatePicker);
        notesEditText = findViewById(R.id.notesEditText);
        customExpenseEditText = findViewById(R.id.customExpenseEditText);
        totalCostTextView = findViewById(R.id.totalCostTextView);
        CheckBox sightseeingCheckBox = findViewById(R.id.sightseeingCheckBox);
        CheckBox hikingCheckBox = findViewById(R.id.hikingCheckBox);
        CheckBox diningCheckBox = findViewById(R.id.diningCheckBox);
        View saveTripButton = findViewById(R.id.saveTripButton);

        Map<String, Double> activityCosts = new HashMap<>();
        activityCosts.put("Sightseeing", 100.0);
        activityCosts.put("Hiking", 50.0);
        activityCosts.put("Dining", 75.0);

        sightseeingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateActivitySelection("Sightseeing", activityCosts.get("Sightseeing"), isChecked));
        hikingCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateActivitySelection("Hiking", activityCosts.get("Hiking"), isChecked));
        diningCheckBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                updateActivitySelection("Dining", activityCosts.get("Dining"), isChecked));

        customExpenseEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { calculateTotalCost(); }
        });

        if (saveTripButton != null) {
            saveTripButton.setOnClickListener(v -> {
                if (validateInputs()) {
                    saveTrip();
                }
            });
        }

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        startDatePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH), null);
        endDatePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH),
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
            String expenseText = customExpenseEditText.getText().toString().trim();
            customExpense = expenseText.isEmpty() ? 0.0 : Double.parseDouble(expenseText);
        } catch (NumberFormatException e) {
            customExpense = 0.0;
        }
        double activityCost = selectedActivities.values().stream().mapToDouble(Double::doubleValue).sum();
        totalCost = customExpense + activityCost;
        totalCostTextView.setText(String.format("Total: $%.2f", totalCost));
    }

    private boolean validateInputs() {
        String destination = destinationEditText.getText().toString().trim();
        if (destination.isEmpty()) {
            Toast.makeText(this, "Enter destination", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startDatePicker.getDayOfMonth() > endDatePicker.getDayOfMonth() &&
                startDatePicker.getMonth() >= endDatePicker.getMonth() &&
                startDatePicker.getYear() >= endDatePicker.getYear()) {
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
                destinationEditText.getText().toString().trim(),
                getDateFromPicker(startDatePicker),
                getDateFromPicker(endDatePicker),
                notesEditText.getText().toString().trim(),
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