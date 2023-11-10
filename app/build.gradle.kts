plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // For using Kotlin Symbol Processing (KSP) for room dependency
    id("com.google.devtools.ksp")
    // id("kotlin-kapt")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.chaimaerazzouki.quizgame"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.chaimaerazzouki.quizgame"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // For using CardView
    implementation ("androidx.cardview:cardview:1.0.0")

    // For the RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    // To work with single activity architecture we need to add navigation component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.5")

    // To implement MVVM architecture we need to add ViewModel dependency
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")

    // The required dependencies to work with Room
    implementation ("androidx.room:room-runtime:2.5.0")
    //annotationProcessor ("androidx.room:room-compiler:2.6.0")
    implementation ("androidx.room:room-ktx:2.5.0")
    // To use Kotlin annotation processing tool (kapt)
    // kapt ("androidx.room:room-compiler:2.6.0")
    // To use Kotlin Symbol Processing (KSP)
    ksp ("androidx.room:room-compiler:2.5.0")

    // Add Coroutine dependency
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // To work with JSON files
    implementation ("com.google.code.gson:gson:2.8.8")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation ("com.google.firebase:firebase-messaging")
    // implementation ("com.google.android.gms:play-services")

}