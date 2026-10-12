# Weather Widget Android App

This project creates a simple Android weather widget app designed for a Pixel-style home screen.

## Features
- Home screen widget with temperature, location, and weather summary
- Device location permission support with manual city fallback
- Refresh button and periodic widget updates
- Placeholder OpenWeatherMap configuration so the project compiles without a real API key
- GitHub Actions workflow to build a debug APK

## Prerequisites
- Android Studio Hedgehog or newer
- JDK 17
- Android SDK with API 34 installed

## Setup
1. Open the project in Android Studio.
2. Create a local `local.properties` file if needed with your SDK path.
3. Add your OpenWeatherMap API key in `WeatherRepository.kt` or replace the placeholder logic with a real retrofit call.
4. Build the app from Android Studio or run:
   - `./gradlew assembleDebug`

## GitHub Actions
The workflow is in `.github/workflows/android-debug.yml` and builds a debug APK automatically.

## App icon and symbol
This project includes a launcher icon and widget icon resource in:
- `app/src/main/res/drawable/ic_launcher_foreground.xml`
- `app/src/main/res/drawable/ic_weather_symbol.xml`
- `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml`
