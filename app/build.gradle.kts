plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Firebase plugin
}

android {
    namespace = "com.example.vw"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vw"
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

    // Update Java compatibility to version 17
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.play.services.tasks)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.mpandroidchart)
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-fitness:21.0.1")
    implementation("com.google.android.gms:play-services-auth:20.6.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
