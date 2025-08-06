# Color Tapper - Android Game

A simple and addictive Android game where players must tap tiles of a specific color as quickly as possible within a time limit.

## 🎮 Game Features

- **Minimalistic UI**: Clean, colorful interface with a 4x4 grid of colored tiles
- **Color Recognition**: Players must tap only tiles matching the target color
- **Time Pressure**: 30-second rounds with countdown timer
- **Dynamic Target**: Target color changes every 5 seconds to increase difficulty
- **Scoring System**: 
  - Correct tap: +10 points
  - Wrong tap: -5 points
- **Visual Feedback**: Animations for correct/incorrect taps
- **Statistics**: Track correct/wrong taps, accuracy, and rounds played

## 🏗️ Architecture

The game follows modern Android development best practices:

### MVVM Architecture
- **ViewModel**: `GameViewModel` handles game logic and state management
- **Fragments**: UI components for different game screens
- **LiveData**: Reactive UI updates based on game state
- **Navigation**: Safe navigation between screens using Navigation Component

### Key Components

#### Data Layer
- `GameConstants.kt`: Game configuration constants
- `GameState.kt`: Game state data class with business logic

#### ViewModel
- `GameViewModel.kt`: Manages game state, timers, and scoring

#### UI Layer
- `StartFragment.kt`: Welcome screen with instructions
- `GameFragment.kt`: Main game screen with tile grid
- `GameOverFragment.kt`: Results screen with statistics
- `MainActivity.kt`: Main activity handling navigation

## 📱 Screens

### 1. Start Screen
- Game title and icon
- "Start Game" button
- "How to Play" button with instructions

### 2. Game Screen
- Score display
- Countdown timer (30 seconds)
- Current round indicator
- Target color display with color sample
- 4x4 grid of colored tiles
- Visual feedback for taps

### 3. Game Over Screen
- Final score
- Performance message based on score
- Detailed statistics:
  - Correct taps
  - Wrong taps
  - Accuracy percentage
  - Rounds played
- "Play Again" button
- "Back to Menu" button

## 🎯 How to Play

1. **Start**: Tap "Start Game" from the welcome screen
2. **Objective**: Tap only tiles that match the target color
3. **Scoring**: 
   - Correct tap: +10 points
   - Wrong tap: -5 points
4. **Time Limit**: You have 30 seconds per round
5. **Challenge**: Target color changes every 5 seconds
6. **Goal**: Score as many points as possible before time runs out

## 🛠️ Setup and Installation

### Prerequisites
- Android Studio (latest version)
- Android SDK (API level 24+)
- Kotlin plugin for Android Studio

### Build Instructions

1. **Clone/Download** the project
2. **Open Android Studio**
3. **Import** the project:
   - File → Open → Select the `color-tapper` folder
4. **Sync** Gradle dependencies
5. **Build** the project:
   - Build → Build Bundle(s) / APK(s) → Build Bundle(s) / APK(s)
6. **Run** on emulator or device:
   - Run → Run 'app' or Shift + F10

### Testing in Android Emulator

1. **Create Android Virtual Device (AVD)**:
   - Tools → AVD Manager → Create Virtual Device
   - Select a phone (e.g., Pixel 4)
   - Choose system image (API 30+ recommended)
   - Finish and wait for AVD to load

2. **Run the App**:
   - Select the emulator in the toolbar
   - Click the green "Run" button (▶)
   - Wait for the app to install and launch

3. **Test the Game**:
   - **Start Screen**: Verify buttons work and instructions show
   - **Game Screen**: Test tile tapping, score updates, timer countdown
   - **Color Changes**: Verify target color changes every 5 seconds
   - **Game Over**: Check statistics display and navigation

## 📁 Project Structure

```
color-tapper/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/colortapper/
│   │   │   ├── data/              # Game data classes
│   │   │   │   ├── GameConstants.kt
│   │   │   │   └── GameState.kt
│   │   │   ├── ui/                # UI Fragments
│   │   │   │   ├── StartFragment.kt
│   │   │   │   ├── GameFragment.kt
│   │   │   │   └── GameOverFragment.kt
│   │   │   ├── MainActivity.kt    # Main activity
│   │   │   └── GameViewModel.kt   # Game logic
│   │   ├── res/
│   │   │   ├── layout/            # XML layouts
│   │   │   ├── values/            # Strings, colors, themes
│   │   │   ├── navigation/        # Navigation graph
│   │   │   └── drawable/          # App icons
│   │   └── AndroidManifest.xml    # App manifest
├── build.gradle.kts               # App-level build config
└── README.md                     # This file
```

## 🔧 Customization

### Game Configuration
Edit `GameConstants.kt` to modify:
- Grid size (currently 4x4)
- Time limit (currently 30 seconds)
- Scoring (currently +10/-5)
- Target color change interval (currently 5 seconds)
- Available colors

### Visual Design
Modify XML layouts and color resources in `res/values/`:
- Colors: `colors.xml`
- Text: `strings.xml`
- Layouts: `layout/` folder
- Theme: `themes.xml`

## 🧪 Testing

### Unit Tests
The project includes test files for:
- Game logic validation
- State management
- Scoring calculations

### UI Testing
Test the following scenarios:
1. **Navigation**: Verify all screens can be reached
2. **Game Logic**: Test scoring and timer functionality
3. **User Input**: Verify tile tapping and button clicks
4. **Edge Cases**: Test rapid tapping and time expiration

## 🚀 Performance Considerations

- **Efficient State Management**: Using LiveData for reactive updates
- **Memory Management**: Proper cleanup in Fragment lifecycle methods
- **Performance**: Lightweight animations and minimal resource usage
- **Battery**: Efficient timers and background operations

## 📱 Device Compatibility

- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Supported Devices**: Phones and tablets with touch input

## 🔮 Future Enhancements

Potential features to add:
- **Sound Effects**: Audio feedback for correct/incorrect taps
- **High Scores**: Persistent score tracking
- **Difficulty Levels**: Easy, Medium, Hard modes
- **Power-ups**: Temporary advantages or bonuses
- **Multiplayer**: Competitive or cooperative modes
- **Themes**: Different color schemes and visual styles
- **Achievements**: Unlockable achievements and milestones

## 📄 License

This project is open source and available under the MIT License.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit issues and enhancement requests.

---

**Happy Gaming! 🎮**
