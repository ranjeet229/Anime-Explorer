# Anime Explorer

Anime Explorer is a modern Android app built with Jetpack Compose, MVVM Clean Architecture, and Koin DI, designed to fetch and display top anime series from the API. It features a polished UI with offline support, Room database caching, robust error handling, and seamless YouTube trailer playback, ensuring a smooth user experience even in challenging network conditions. The app gracefully handles legal constraints on images, provides comprehensive state management with StateFlow, and maintains a clean, well-documented codebase for easy maintenance and scalability.


## Objective Implementation Status

### 1. Anime List Page

**API Integration**: Uses Jikan API endpoint `https://api.jikan.moe/v4/top/anime` via `ApiService.getTopAnime()`

**UI Implementation**: `app/src/main/java/com/animeExplorer/ui/screens/animeList/`
- **AnimeListScreen.kt**: Main home screen with LazyColumn displaying anime list
- **AnimeCard.kt**: Individual anime card component showing all required fields:
  - **Title**: Large, bold text with ellipsis for long titles
  - **Number of Episodes**: Displayed with play icon and proper singular/plural handling
  - **Rating**: MyAnimeList score with star icon and gold color
  - **Poster Image**: High-quality anime poster with fallback to large image URL

**Data Flow**: `AnimeListViewModel` → `AnimeRepository` → `ApiService` → Jikan API

### 2. Anime Detail Page

**API Integration**: Uses `https://api.jikan.moe/v4/anime/{anime_id}` via `ApiService.getAnimeDetails()`

**UI Implementation**: `app/src/main/java/com/animeExplorer/ui/screens/animeDetails/`
- **AnimeDetailsScreen.kt**: Main detail screen with comprehensive layout
- **AnimeDetailsContent.kt**: Content component displaying all required fields:
  - **Video Player**: `TrailerPlayer.kt` with YouTube integration using android-youtube-player library
  - **Poster Image Fallback**: When no trailer available, shows poster image with overlay text
  - **Title**: Large, prominent title display
  - **Plot/Synopsis**: Full synopsis with proper text formatting and line height
  - **Genre(s)**: Horizontal scrollable filter chips with Material 3 design
  - **Main Cast**: Character list with bullet points (top 10 characters)
  - **Number of Episodes**: Displayed with play icon and proper text
  - **Rating**: MyAnimeList score with star icon and gold color

**Navigation**: Deep linking with `{animeId}` parameter for seamless navigation

### 3. Local Database with Room (Bonus)

**Implementation**: `app/src/main/java/com/animeExplorer/data/local/`
- **AnimeDatabase.kt**: Room database configuration with proper schema
- **AnimeEntity.kt**: Anime list data entity for local storage
- **AnimeDetailsEntity.kt**: Detailed anime information entity
- **AnimeDao.kt & AnimeDetailsDao.kt**: Database access objects for CRUD operations

**Features**:
- **Store fetched anime data locally**: Complete anime list and details cached
- **Use Room for database operations**: Full Room implementation with KSP
- **Data syncs when online**: Automatic sync mechanism in repository

### 4. Offline Mode & Syncing

**Implementation**: `app/src/main/java/com/animeExplorer/data/repository/AnimeRepository.kt`

**Features**:
- **App functions without internet**: Full offline functionality with cached data
- **Sync data with server when online**: Automatic background sync with conflict resolution

**UI Behavior**: 
- Loading states show cached data immediately
- Background sync updates content seamlessly
- No network dependency for viewing stored anime

### 5. Error Handling

**Comprehensive error handling across all layers**:

**API Error Handling**:
- Network timeouts (30 seconds) configured in `NetworkModule.kt`
- HTTP error responses handled gracefully in repository
- Graceful fallback to cached data when API fails

**Database Error Handling**:
- Room operation exceptions caught and handled
- Data corruption protection with proper error states
- Graceful degradation when database operations fail

**Network Error Handling**:
- Connection state monitoring
- Retry mechanisms with exponential backoff
- Error messages with retry options

**UI Error States**:
- Loading, success, and error states managed in ViewModels
- `ErrorContent.kt` component for consistent error display
- Retry buttons for failed operations
- Proper error messages in user's language

### 6. Design Patterns & Architecture

**Clean MVVM Architecture with Koin DI**:

**MVVM Pattern**:
- `AnimeListViewModel.kt` & `AnimeDetailsViewModel.kt`: ViewModels with StateFlow
- `AnimeListUiState` & `AnimeDetailsUiState`: UI state management
- Reactive data handling with StateFlow

**Repository Pattern**: `AnimeRepository.kt` - Single source of truth for data

**Dependency Injection**: Koin modules in `app/src/main/java/com/animeExplorer/di/`
- `NetworkModule.kt`: Retrofit, OkHttp, API service
- `DatabaseModule.kt`: Room database and DAOs
- `RepositoryModule.kt`: Repository implementations
- `ViewModelModule.kt`: ViewModels

**Best-in-Class Libraries**:
- **Retrofit**: API calls with OkHttp logging interceptor
- **Coil**: Modern image loading (alternative to Glide/Picasso)
- **Room**: Database with KSP for compile-time code generation
- **StateFlow**: Reactive data handling (alternative to LiveData)
- **Navigation Compose**: Screen navigation
- **Material 3**: Modern design system

### 7. Design Constraint Handling

**Legal Constraint Implementation for Anime Images**:

**Placeholder System**: 
- `ic_anime_placeholder.xml`: Fallback image for unavailable anime posters
- Graceful degradation when images become unavailable due to legal changes

**Error Handling**: 
- `AsyncImage` with `error` and `placeholder` parameters in `AnimeCard.kt`
- Same implementation in `TrailerPlayer.kt`
- Layout remains intact with consistent placeholder images

**Legal Compliance**: 
- `SHOW_ANIME_IMAGES` constant in `AppConstants.kt` for easy toggle
- When `false`, shows placeholder instead of actual images
- No broken UI when images are legally restricted

### 8. Problem-Solving & Personal Input

**Edge Cases Handled**:
- Null image URLs with fallback to large image URLs
- Missing trailer data with poster image fallback
- Empty character lists with proper UI handling
- Network timeouts and retry logic
- Database corruption recovery
- Long titles with ellipsis truncation
- Missing episode counts with proper null handling

**Robustness Features**:
- **Network Resilience**: Automatic retry with exponential backoff
- **Offline Support**: Full offline functionality with cached data
- **Error Management**: Comprehensive error states and user feedback

**UI/UX Intuitiveness**:
- Material 3 design with consistent theming
- Smooth animations and transitions (`FadeInAnimation`, `ScaleAnimation`, `SlideInAnimation`)
- Loading states and progress indicators
- Clear error messages with retry options
- Responsive design for different screen sizes
- Proper content descriptions for accessibility

**Code Organization**:
- Well-structured package hierarchy
- Clear separation of concerns
- Extensive code documentation with author attribution
- Consistent naming conventions
- Reusable components and utilities

### Splash Screen (Bonus Feature)

**Implementation**: `app/src/main/java/com/animeExplorer/ui/screens/splash/`

- **SplashScreen.kt**: Beautiful YouTube-like splash screen with animations
- **Features**:
  - Custom anime-themed logo with sparkle animations
  - Smooth fade-in and scale animations
  - Background gradient with animated sparkles
  - 2.5-second duration with loading indicator
  - Automatic transition to main app
- **Animations**: Alpha, scale, rotation, and infinite sparkle effects
- **Design**: Dark gradient background with white text and colorful sparkles

## Architecture

```
app/
├── data/
│   ├── local/          # Room database entities and DAOs
│   │   ├── AnimeEntity.kt
│   │   ├── AnimeDetailsEntity.kt
│   │   ├── AnimeDao.kt
│   │   ├── AnimeDetailsDao.kt
│   │   └── AnimeDatabase.kt
│   ├── remote/         # API service and DTOs
│   │   ├── ApiService.kt
│   │   └── dto.kt
│   └── repository/     # Repository pattern implementation
│       └── AnimeRepository.kt
├── di/                 # Dependency injection modules
│   ├── NetworkModule.kt
│   ├── DatabaseModule.kt
│   ├── RepositoryModule.kt
│   └── ViewModelModule.kt
├── ui/
│   ├── actions/        # UI actions and events
│   │   ├── animeList/
│   │   └── animeDetails/
│   ├── screens/        # Compose UI screens
│   │   ├── animeList/
│   │   ├── animeDetails/
│   │   └── splash/
│   ├── components/     # Reusable UI components
│   │   ├── TrailerPlayer.kt
│   │   ├── YouTubePlayer.kt
│   │   └── SharedComponents.kt
│   ├── theme/          # Material 3 theming
│   └── viewmodel/      # ViewModels with StateFlow
├── util/               # App constants and utilities
│   └── AppConstants.kt
└── MainActivity.kt     # Main activity with navigation setup
```

## API Endpoints Used

- `GET /v4/top/anime` - Fetch top anime list
- `GET /v4/anime/{id}` - Fetch anime details
- `GET /v4/anime/{id}/characters` - Fetch anime characters

## Libraries Used

- **Jetpack Compose** - Modern UI toolkit
- **Retrofit + OkHttp** - Network requests with logging interceptor
- **Coil** - Image loading and caching
- **Room + KSP** - Local database with compile-time code generation
- **Navigation Compose** - Screen navigation
- **ViewModel + StateFlow** - State management
- **Coroutines** - Asynchronous programming
- **Koin** - Dependency injection
- **android-youtube-player** - YouTube video playback
- **Material 3** - Modern design system

## Dependency Injection Setup

The app uses Koin for dependency injection with the following modules:

- **NetworkModule**: Provides Retrofit, OkHttp, and API service
- **DatabaseModule**: Provides Room database and DAOs
- **RepositoryModule**: Provides repository implementations
- **ViewModelModule**: Provides ViewModels

## Video Player Implementation

The app includes a sophisticated video player system:

- **YouTubePlayer**: Uses `android-youtube-player` library for YouTube video playback
- **TrailerPlayer**: Wrapper component that handles both YouTube videos and poster fallbacks
- **Error Handling**: Graceful fallback to poster images when videos are unavailable
- **Loading States**: Proper loading indicators and error messages

## Assumptions Made

1. **Image Fallback**: If profile images become unavailable due to legal changes, the app gracefully falls back to placeholder images
2. **Network Resilience**: App handles network failures and provides offline functionality
3. **Data Caching**: Anime data is cached locally for offline access
4. **Error Recovery**: Users can retry failed operations
5. **Responsive Design**: UI adapts to different screen sizes
6. **Video Fallback**: When trailers are unavailable, poster images are displayed with overlay text

## Known Limitations

1. **API Rate Limiting**: Jikan API has rate limits that may affect performance
2. **Trailer Support**: Only YouTube videos are supported; direct video URLs are not handled
3. **Character Images**: Character profile images are not displayed due to potential legal constraints
4. **Search Functionality**: No search feature implemented (focus on top anime list)
5. **Pagination**: Only first page of anime results is loaded
6. **Orientation**: App is locked to portrait mode for consistent video player experience

## How to Run

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

## Network Permissions

The app requires:
- `INTERNET` - For API calls
- `ACCESS_NETWORK_STATE` - For network status monitoring

## Offline Support

The app provides full offline functionality:
- Cached anime list available without internet
- Detailed anime information stored locally
- Automatic sync when connection is restored
- Graceful error handling for network failures

## Video Player Features

- **YouTube Integration**: Seamless YouTube video playback
- **Loading States**: Proper loading indicators during video initialization
- **Error Handling**: Graceful error display with retry options
- **Fallback System**: Poster images displayed when videos are unavailable
- **Lifecycle Management**: Proper cleanup of video resources

## Development Notes

- **Koin Integration**: Clean dependency injection setup with proper module organization
- **State Management**: Uses StateFlow for reactive UI updates
- **Error Handling**: Comprehensive error handling at all layers
- **Code Organization**: Well-structured with clear separation of concerns
- **Documentation**: Extensive code documentation with author attribution
