Deliverable 2 

2.1 Project structure & naming
- Root package: `com.example.tripbuddy` with subpackages: `ui/`, `data/model/`, `data/db/`, `utils/`.
- Clear separation of concerns improves readability and maintenance.

2.2 Navigation flow
- SplashActivity decides whether to route to LoginActivity or HomeActivity depending on session.
- From HomeActivity: buttons navigate to MemoriesActivity, GalleryActivity, TripPlanningActivity, and SettingsActivity.
- TripPlanningActivity transitions to BudgetSummaryActivity to display totals.

2.3 Responsive layouts
- Layout XML files use ConstraintLayout for adaptive positioning.
- Ensures app runs smoothly across multiple screen sizes and orientations.

2.4 Visual branding
- Custom app icon assets stored in res/mipmap directories.
- Themes and colors applied via res/values files for a consistent look and feel.
- Splash screen handled by SplashActivity with activity_splash.xml.

2.5 Welcoming home screen
- activity_home.xml contains a welcome message and navigation cards.
- Provides a friendly entry point and central hub for user interaction.
