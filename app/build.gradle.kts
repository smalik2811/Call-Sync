plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleServices)
    kotlin("kapt")
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.yangian.callsync"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yangian.callsync"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    flavorDimensions += listOf("paidMode")
    productFlavors {
        create("free") {
            dimension = "paidMode"
        }
        create("paid") {
            dimension = "paidMode"
        }
    }
}

dependencies {

    // Normal Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose Dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window.size)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(project(":core:firebase"))
    kapt(libs.dagger.hilt.compiler)

    // Hilt
    implementation(libs.androidx.hilt.navigation)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.cloud.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Play Services
//    implementation(libs.play.services.ads)

    // Work Manager
    implementation(libs.androidx.work.runtime)

    // Test Dependencies
    testImplementation(libs.junit)

    // Android-Test Dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug Dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Project Modules
    implementation(project(":core:designsystem"))
    implementation(project(":feature:home"))
    implementation(project(":feature:onboard"))
    implementation(project(":core:datastore"))
    implementation(project(":core:workmanager"))
    implementation(project(":core:data"))
    implementation(project(":core:analytics"))
}