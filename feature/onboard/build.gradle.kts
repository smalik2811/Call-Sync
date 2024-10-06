plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    namespace = "com.yangian.callsync.feature.onboard"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    secrets {
        // This production secrets file and going to contains real secrets
        propertiesFileName = "local.properties"
    }
}

dependencies {

    // Normal Dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Compose Dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.window.size)

    // Hilt
    implementation(libs.androidx.hilt.navigation)

    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.cloud.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)

    // Play Services
    implementation(libs.play.services.ads)

    // QRGenerator
    implementation(libs.qrcode.kotlin)

    // Google Play Services
    implementation(libs.mlkit.barcode.scanning)

    // Work Manager
    implementation(libs.androidx.work.runtime)

    // Compose Markdown
    implementation(libs.compose.markdown)

    // Test Dependencies
    testImplementation(libs.junit)

    // Android Test Dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Project Modules
    implementation(project(":core:designsystem"))
    implementation(project(":core:datastore"))
    implementation(project(":core:firebase"))
    implementation(project(":core:workmanager"))
    implementation(project(":core:network"))
    implementation(project(":core:firebase"))
}