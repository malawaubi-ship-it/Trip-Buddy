package com.example.tripbuddy.utils;

import android.widget.DatePicker;
import java.util.Calendar;

public class InputValidator {
    public static boolean isValidDestination(String destination) {
        return destination != null && !destination.trim().isEmpty() && destination.length() <= 50;
    }

    public static boolean isValidDate(DatePicker startDate, DatePicker endDate) {
        int startDay = startDate.getDayOfMonth();
        int startMonth = startDate.getMonth();
        int startYear = startDate.getYear();
        int endDay = endDate.getDayOfMonth();
        int endMonth = endDate.getMonth();
        int endYear = endDate.getYear();

        Calendar startCal = Calendar.getInstance();
        startCal.set(startYear, startMonth, startDay);
        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear, endMonth, endDay);

        return !endCal.before(startCal); // End date must be on or after start date
    }

    public static boolean isValidExpense(String expense) {
        if (expense == null || expense.trim().isEmpty()) return true; // Optional field
        try {
            double value = Double.parseDouble(expense.trim());
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}