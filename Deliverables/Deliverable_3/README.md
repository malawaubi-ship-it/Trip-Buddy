Deliverable 3 — Trip Planning & Budgeting

3.1 Trip planning form
- Implemented TripPlanningActivity with input controls for destination, dates, notes, and expense fields.
- Used EditText, DatePicker, and Spinners for intuitive data entry.

3.2 Predefined activities
- Provided a selectable list of activities (sightseeing, hiking, dining, etc.) each with fixed costs.
- Implemented with CheckBoxes and Spinner options to allow multiple selections.

3.3 Custom expenses
- Users can add extra expenses manually using EditText.
- Input validation ensures only positive numbers are accepted.

3.4 Real-time budget calculator
- Applied dynamic logic in TripPlanningActivity to sum up activity costs and custom expenses.
- Budget updates in real time as user inputs change, no manual submission required.

3.5 Loyalty discount
- Implemented logic to check persistent trip history count.
- If 3 or more trips recorded, applies 10% discount automatically to total cost.

3.6 Budget summary screen
- BudgetSummaryActivity displays subtotal, discount, and final total with clear formatting.
- Currency and percentage formatting used to improve clarity.
