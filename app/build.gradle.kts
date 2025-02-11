plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "ua.wied"
    compileSdk = 35

    defaultConfig {
        applicationId = "ua.wied"
        minSdk = 29
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // MARK: - Dependency Injection (Hilt)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    // MARK: - AndroidX Core & Lifecycle
    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.androidx.lifecycle)

    // MARK: - Jetpack Compose
    implementation(libs.bundles.androidx.compose)
    implementation(platform(libs.androidx.compose.bom))

    // MARK: - Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // MARK: - Debugging
    debugImplementation(libs.bundles.androidx.debug)
}
