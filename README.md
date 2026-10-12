# Weather Widget Android App (Live Open-Meteo)

This project now integrates live weather using the free Open-Meteo APIs (no API key required) and Open-Meteo geocoding for city -> coordinates.

What changed
- Uses Open-Meteo geocoding API to resolve city names
- Uses Open-Meteo forecast API to fetch current weather (current_weather)
- Retrofit + Gson + Coroutines networking
- Worker and UI updated to fetch and display live weather
- No API key required — the app works out-of-the-box using public Open-Meteo endpoints

Notes
- Periodic updates are still driven by WorkManager (hourly)
- Location-based fetch requires FINE_LOCATION permission; Worker uses the saved city preference
- You can change the default city in app UI

How to build
1. Open in Android Studio
2. ./gradlew assembleDebug
3. Run the GitHub Actions workflow to produce an APK artifact
