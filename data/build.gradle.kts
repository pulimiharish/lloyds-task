plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt)
    kotlin("kapt")
    kotlin("plugin.serialization") version embeddedKotlinVersion
}

android {
    namespace = "com.lbg.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField("String", "API_KEY", "\"e4f9e61f6ffd66639d33d3dde7e3159b\"")
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    // Data Module dependencies
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp3.logging.interceptor)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.gson)
    implementation(libs.gsonConverter)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
}
