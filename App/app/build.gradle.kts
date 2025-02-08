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

        buildConfigField("String", "SEOUL_APP_KEY", "\"686553535565686437394450516f68\"")
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
        buildConfig = true
    }
}

dependencies {

    // AndroidX 및 기본 라이브러리
    implementation(libs.appcompat)
    implementation(libs.material) // 또는 "com.google.android.material:material:1.9.0" 중 하나만 사용
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.preference)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    // 네이버 지도 SDK
    implementation("com.naver.maps:map-sdk:3.19.1")

    // Retrofit 및 Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.8.8")

    // RecyclerView 및 CardView
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.cardview:cardview:1.0.0")

    //xml
    implementation("com.squareup.retrofit2:converter-simplexml:2.9.0")
    implementation(libs.security.identity.credential)
    implementation(libs.play.services.maps)

    // 테스트 라이브러리
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}