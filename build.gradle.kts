// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
}


buildscript{
    repositories {
        google()
        mavenCentral()
    }
    dependencies{
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    }
}

