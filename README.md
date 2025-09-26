 Completed Features:

   Core Requirements:
  - WebSocket Connection: OkHttp-based WebSocket client that connects to server on /nanit path
  - Birthday Data Model: Handles JSON response {"name":"Nanit","dob":1685826000000,"theme":"pelican"}
  - Age Calculation: Shows months until 1 year, then years thereafter
  - 3 Animal Themes: Pelican (blue), Fox (orange), Elephant (purple) with unique color schemes
  - Jetpack Compose UI: Modern, responsive Material 3 design

   UI Components:
  - Connection Screen: Server URL input with connection status feedback
  - Birthday Card: Displays name, age, birth date, and theme-specific styling
  - Theme Integration: Each animal theme has custom colors and information
  - Responsive Design: Handles different connection states gracefully

   Bonus Features:
  - Photo Selection: Tap the circular avatar to select photos from gallery
  - Enhanced Theming: Animal-specific fun facts and emojis
  - Loading States: Connection status indicators
  - Error Handling: WebSocket connection failure handling

  📁 Project Structure:

  app/src/main/java/
  ├── data/
  │   ├── models/BirthdayData.kt        # Data models & Theme enum
  │   └── websocket/WebSocketClient.kt   # OkHttp WebSocket implementation
  ├── ui/
  │   ├── BirthdayScreen.kt             # Main UI screen
  │   ├── MainNavigation.kt             # Navigation wrapper
  │   └── theme/BirthdayTheme.kt        # Theme system & animal info
  ├── utils/AgeCalculator.kt            # Age calculation logic
  ├── viewmodel/BirthdayViewModel.kt     # State management
  └── MainActivity.kt                   # Entry point

