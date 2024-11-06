plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
}

android {
    namespace = "com.yangian.callsync.core.datastore"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

dependencies {

    // Androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Dagger-Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    // Datastore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)

    // Test
    testImplementation(libs.junit)

    // Android test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Project Modules
    implementation(project(":core:constant"))
}