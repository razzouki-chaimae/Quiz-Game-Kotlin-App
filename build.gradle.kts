buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
    // For using KSP in order to use Room dependency
    id ("com.google.devtools.ksp") version "1.9.20-1.0.13" apply false
    // Add the dependency for the Google services Gradle plugin (Firebase)
    id("com.google.gms.google-services") version "4.4.0" apply false
}