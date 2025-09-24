# 📽️ Android Slideshow App

A simple Android application for playing a **slideshow (images + videos)** retrieved from the **API**.  
The player works both online and offline (with local caching), featuring smooth **fade in/out transitions** between media items.

---

## 🚀 Features

- Fetch **playlist** from backend (REST API).
- Support for **images and videos** in one sequence.
- **Caching** media files locally (Room + local storage).
- **Offline mode**: plays last downloaded media when no internet connection.
- **Crossfade animations** between slides.
- Update playlist from backend **without restarting the player** (Bonus).
- Clean Architecture with **MVI** pattern.

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Media**:
  - [Coil](https://coil-kt.github.io/coil/compose/)
  - [ExoPlayer (Media3)](https://developer.android.com/guide/topics/media/exoplayer) for videos
- **Networking**: Retrofit + Gson
- **Persistence**: Room Database
- **DI**: Hilt
- **Async**: Coroutines + Flow
- **Testing**: JUnit4, Coroutines Test, MockK/Mockito

## 🧩 Bonus (Implementation Idea)

To support **dynamic playlist updates without restarting the app**:

- **Observe Playlist Changes**  
  Use a `Flow` from the Room database to observe playlist updates reactively.  
  The UI layer (`collectAsState()`) will automatically recompose when the data changes.

- **Background Refresh**  
  Run a `refreshPlaylist()` job every **10 seconds** (e.g., using `CoroutineScope` + `delay`).  
  This job fetches the latest playlist data from the backend.

- **Database Sync**  
  Insert or update the new playlist items into the local Room database.  
  Since the UI is collecting from the DB, it will update seamlessly without any manual intervention.

✅ This approach ensures that slides are always fresh and up to date, while keeping the slideshow running smoothly.

### Extra Logic Implemented
When a new playlist update is fetched:
- Items are shown **immediately by URL** (no waiting for cache).  
- Meanwhile, files are being cached in the background.  
- Once caching is finished, items start showing from **local storage (URI)**.  

## 🧪 Unit Testing (Note)

I have recently started learning about **unit testing** in Android.  
The current tests in this project are more of an **improvisation** rather than production-level test coverage.  

- I experimented with writing simple test cases for Repository logic.  
- The structure is not yet fully aligned with best practices, but it’s a **first step** toward building confidence with testing.  

⚠️ Please note: These tests may not reflect real-world production standards — they are more of a **learning exercise** than a fully mature testing suite.
