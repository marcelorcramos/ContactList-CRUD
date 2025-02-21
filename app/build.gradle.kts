plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") version "1.9.24"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.dataviwerproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dataviwerproject"
        minSdk = 24
        targetSdk = 35  // Corrigido de targetSdkVersion para targetSdk
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation(libs.firebase.auth)
    implementation("com.google.firebase:firebase-auth:23.2.0")
    implementation(libs.activity)
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
