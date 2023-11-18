
https://github.com/IbrahimEzzatSaad/Wally/assets/43017172/5238ffd2-c9eb-48f5-aa14-033c1e4b7ce8

# Wally 

Wallpaper finder and downloader app Demonstrate the Jetpack Compose UI
using [Unsplash](https://unsplash.com/developers) API *Made with 
by [Ibrahim Ezzat](https://github.com/IbrahimEzzatSaad)*



## Architecture 🗼

- Single Activity No Fragment
- MVVM Pattern

**View:** Renders UI and delegates user actions to ViewModel

**ViewModel:** Can have simple UI logic but most of the time just gets the data from UseCase.

**UseCase:** Contains all business rules and they written in the manner of single responsibility
principle.

**Repository:** Single source of data. Responsible for getting data from one or more data sources.

**For more information you can
check [Guide to app architecture](https://developer.android.com/jetpack/guide?gclid=CjwKCAiA_omPBhBBEiwAcg7smXcfbEYneoLKFD_4Tyw0OgVQkpZL_XIr5TPXT0mncuQhgDIBBvLhbBoCEx0QAvD_BwE&gclsrc=aw.ds#mobile-app-ux)**

![Architecture](https://miro.medium.com/v2/resize:fit:720/format:webp/1*Qby1SHSjmFEJT_ycbcpysQ.png)

## Libraries 📚

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Jetpack Compose is Android’s
  modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous
  and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) -
  Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Flows](https://developer.android.com/kotlin/flow) - Data objects that notify views when the
      underlying database changes.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - Database Library
- [Material Components for Android](https://github.com/material-components/material-components-android)
  - Modular and customizable Material Design UI components for Android.
- [Dagger - Hilt](https://dagger.dev/hilt/) - Dependency Injection Framework
- [Coil](https://coil-kt.github.io/coil/compose/) - Image loader library.

## Package Structure 🗂

    .
    .
    .
    ├── Data
    |    ├── API              # RetroFit
    |    ├── paging           # Paging3 - RemoteMediator - Pager
    |    ├── cache            # Room Database
    |    └── di               # Hilt Dependency Injection
    |
    |
    ├── Domain
    |    ├── Repository       # Repository interface
    |    |
    |    └── UseCases         # App UseCases
    | 
    ├── UI                    
    |    ├── theme            # Compose Theme
    |    |               
    |    ├── component        # UI components composables
    |    | 
    |    ├── navigation       # NavigationHost & Destinations
    |    |
    |    ├── model            # Model for ViewModel & Categories List
    |    |
    |    └── screens          # UI Screens
    |
    ├── utils                 # Useful classes
    |
    └── APP.kt          # @HiltAndroidApp

## Contribute 🤝

If you want to contribute to this app, you're always welcome!

## Design ❤️

*Design is inspired from [@anwargul0x - Walper](https://www.figma.com/community/file/1191612184517420607/walper-wallpaper-app-ui-kit-preview)*

