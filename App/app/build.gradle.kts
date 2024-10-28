plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.liveguard_app_010"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.liveguard_app_010"
        minSdk = 29
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    // 네이버 지도 SDK
    implementation("com.naver.maps:map-sdk:3.19.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.preference) // 최신 버전 사용 권장
    implementation("com.google.android.gms:play-services-location:21.0.1")  // 최신 버전 사용 권장
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}