# Skin Changer — Android demo project

A small Android app source project with a black/red interface, built-in demo skin catalog,
persistent favorites, and a remembered selection.

## Important compatibility note
This app is a **catalog/selection demo only**. It does not modify StandChillow 2.06 F2,
its game files, inventory, or online account. Applying a skin inside the game requires a
documented and supported integration from the game; compatibility has not been confirmed.

## Open and build
1. Install Android Studio and a JDK 17.
2. Extract this ZIP.
3. In Android Studio, choose **Open** and select the `SkinChangerAndroid` folder.
4. Let Gradle sync. If Android SDK Platform 35 is missing, install it from SDK Manager.
5. Run the `app` configuration on an Android device or emulator.

The project uses Android Gradle Plugin 8.6.1 and Gradle 8.7. If Android Studio asks to
create/download the Gradle wrapper, allow it, or run the project with a compatible Gradle 8.7 installation.

## Features
- Built-in demo catalog (illustrative labels, not official game artwork)
- Tap a card to remember the selected demo skin
- Star button to toggle favorites
- Favorites-only filter
- Local persistence via SharedPreferences

Package: `com.example.skinchanger`
