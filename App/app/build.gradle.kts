plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

// 모든 구성(configuration)에 대해 Kotlin stdlib 버전을 강제 적용
configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
        force("org.jetbrains.kotlin:kotlin-stdlib-common:1.8.22")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    }
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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Google Play Services 및 위치 서비스
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")

    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // 네이버 지도 SDK
    implementation("com.naver.maps:map-sdk:3.19.1")

    // Retrofit 및 Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // RecyclerView 및 CardView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.gridlayout:gridlayout:1.0.0")

    // XML 처리
    implementation("com.squareup.retrofit2:converter-simplexml:2.9.0")

    // 보안 및 인증 관련 라이브러리
    implementation("androidx.security:security-identity-credential:1.0.0-alpha03")

    // Google Play Services 추가 (필요할 경우)
    implementation("com.google.android.gms:play-services-auth:20.7.0") // Google 로그인 API
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation(libs.play.services.oss.licenses)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore) // Google 지도 API

    // 테스트 라이브러리
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 네이버 로그인 테스트
    implementation("com.navercorp.nid:oauth:5.4.0")
    implementation("androidx.annotation:annotation:1.7.0")

    // Firebase BoM & Analytics
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-analytics")
}