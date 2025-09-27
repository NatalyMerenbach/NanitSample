# Nanit Happy Birthday App

## Overview

A baby birthday celebration app built for Nanit as part of an interview assignment. The app communicates with a WebSocket server to receive birthday information and displays a themed, interactive birthday screen with photo capabilities.

## Assignment Requirements

This project implements all requirements from the **Happy WebSocket Birthday!** assignment document:

### Core Features 

- **WebSocket Communication**: OkHttp-based client connecting to server on `/nanit` path
- **Birthday Data Processing**: Handles JSON response `{"name":"Nanit","dob":1685826000000,"theme":"pelican"}`
- **Age Calculation Logic**: Shows months until 1 year, then years thereafter (up to age 9)
- **3 Animal Themes**: Pelican, Fox, and Elephant with unique visual designs
- **Jetpack Compose UI**: Modern, responsive Material 3 design following provided Figma specifications
- **Multi-line Name Support**: Handles long names by wrapping to two lines
- **Back Navigation**: Press back to return from birthday screen to connection screen
- **IP Discovery**: Automatic network scanning and IP suggestions for easy server connection
- **Enhanced Visual Design**: Theme-specific backgrounds, colors, and assets
- **Loading States**: Connection status indicators and smooth transitions


### Bonus Features 

- **Photo Selection**: Tap camera icon to select photos from gallery
- **Error Handling**: WebSocket connection failure handling with retry capability

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with StateFlow
- **WebSocket**: OkHttp WebSocket implementation
- **JSON Parsing**: Gson for data serialization
- **Testing**: JUnit 4, Mockito, Coroutines Test, MockWebServer

## Project Structure


app/src/main/java/
├── data/
│   ├── models/
│   │   └── BirthdayData.kt           # Data models & Theme enum
│   │   
│   └── websocket/
│       └── WebSocketClient.kt        # OkHttp WebSocket implementation
├── ui/
│   ├── BirthdayScreen.kt             # Main birthday display screen
│   ├── ConnectionScreen.kt           # Server connection interface
│   ├── MainScreen.kt                 # Navigation logic
│   ├── MainNavigation.kt             # App navigation wrapper
│   └── theme/
│       ├── BirthdayTheme.kt          # Theme system & colors
│       ├── Color.kt                  # Color definitions
│       ├── Theme.kt                  # Material theme setup
│       └── Type.kt                   # Typography settings
├── utils/
│   ├── AgeCalculator.kt              # Age calculation logic
│   └── IpDiscovery.kt                # Network IP discovery and scanning
├── viewmodel/
│   └── BabyBirthdayViewModel.kt      # State management & business logic
└── MainActivity.kt                   # Application entry point

app/src/test/java/                    # Unit Tests
├── data/
│   ├── models/BirthdayDataTest.kt    # Data model and theme enum tests
│   └── websocket/WebSocketClientTest.kt # WebSocket communication tests
└── utils/AgeCalculatorTest.kt        # Age calculation logic tests

### Responsive UI Design
- Follows provided Figma design specifications exactly
- Dynamic sizing for different age numbers (1-digit vs 2-digit)
- Decorative elements positioned 22dp from number edges
- Smooth loading states and error handling
- Clean Material 3 design language

## Development Practices

- **Clean Architecture**: Separation of concerns with clear data/domain/UI layers
- **State Management**: Reactive UI with StateFlow and Compose state
- **Error Handling**: Comprehensive WebSocket error states and user feedback
- **Unit Testing**: Comprehensive test coverage for core business logic
- **AgeCalculator**: 9 test cases covering months/years logic, edge cases, and singular/plural handling
- **BirthdayData & Theme**: 10 test cases for data models, theme validation, and edge cases
- **WebSocketClient**: 10 test cases for connection lifecycle, protocol, and error handling
- **Code Quality**: Kotlin best practices with coroutines and modern Android patterns

## Testing

Run unit tests with:
```bash
./gradlew test
```

### Test Coverage
- **Age Calculation**: Tests months vs years display, singular/plural forms, edge cases at 12 months
- **Theme System**: Validates all 3 themes (pelican, fox, elephant), handles invalid themes with defaults
- **WebSocket Protocol**: Tests connection lifecycle, message sending ("HappyBirthday"), JSON parsing
- **Data Models**: Validates JSON serialization, long names, edge case timestamps

---

*Built for Nanit Interview Assignment - Demonstrating Android development capabilities with WebSocket communication, Jetpack Compose UI, clean architecture practices, and comprehensive unit testing.*

