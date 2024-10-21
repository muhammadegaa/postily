plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.kapt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.postily"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.postily"
        minSdk = 24
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

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    // Jetpack Compose and Material3
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.play.services.auth.v2060)
    implementation(libs.android.gif.drawable)
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)

    testImplementation(libs.turbine.v0121)

    // Compose UI and UI Testing dependencies
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.v150)
    androidTestImplementation(libs.ui.test.junit4)

    // For InstantTaskExecutorRule
    testImplementation(libs.androidx.core.testing)

    // Coroutine test library
    testImplementation(libs.kotlinx.coroutines.test.v173)

    // Firebase Dependencies
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Dependency Injection: Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android.v164)

    // Image loading libraries
    implementation(libs.coil.compose)
    implementation(libs.glide)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // UI Testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    // UI Tools and Previews
    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling)

    // MockK for unit testing
    testImplementation(libs.mockk.v1120) // Mockk core for mocking ViewModels/Repositories
    androidTestImplementation(libs.mockk.android) // Mockk Android for instrumentation tests

    // Other Unit Testing Libraries
    testImplementation(libs.junit.jupiter) // JUnit for unit testing
    testImplementation(libs.turbine.v090) // StateFlow testing
    testImplementation(libs.mockwebserver.v491) // MockWebServer for API simulation

    // Firebase testing utilities
    testImplementation(libs.firebase.auth.ktx) // Firebase Authentication testing
    testImplementation(libs.firebase.firestore.ktx) // Firebase Firestore testing

    // AndroidX Test Libraries (for Espresso and Android Testing)
    androidTestImplementation(libs.androidx.espresso.core.v340) // Espresso for UI tests
    androidTestImplementation(libs.androidx.runner.v140) // Android test runner
    androidTestImplementation(libs.androidx.rules.v140) // Android test rules

    // AndroidX Test Ext JUnit
    androidTestImplementation(libs.androidx.junit.v113) // AndroidX JUnit extension for instrumentation tests

    // Optional UI Components
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material.v190)
    implementation(libs.circleimageview)
}
