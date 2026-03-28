Deliverable 5 — Persistence & Preferences

5.1 Login & session management
- LoginActivity and SharedPreferences used to persist login credentials and status.
- User stays logged in across sessions until explicitly logging out.

5.2 Persisting memories
- MemoriesActivity stores text, images, and metadata in SQLite database via MemoryDao.
- Entries retrievable and editable for long-term access.

5.3 Trip activity storage
- TripDao saves selected trip activities and expenses.
- Past trips can be revisited and preloaded to save user effort.

5.4 Loyalty tracking
- SharedPreferences tracks total number of trips recorded.
- Trip count automatically increments and drives loyalty discount logic.

5.5 Settings/preferences screen
- SettingsActivity provides toggles for dark mode, language, and background music.
- Preferences saved with SharedPreferences and applied on next launch.

5.6 Proper use of storage systems
- SQLite chosen for relational data like trips, activities, and memories.
- SharedPreferences used for lightweight key-value storage such as sessions and preferences.
