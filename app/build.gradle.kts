plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.builtin.kotlin)
    alias(libs.plugins.devtools.ksp)
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.rentcar"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.rentcar"
        minSdk = 24
        targetSdk = 36
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


    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.dagger:hilt-android:2.59.2")
    ksp("com.google.dagger:hilt-android-compiler:2.59.2")


//    implementation("com.google.dagger:hilt-android:2.57.1")
//    ksp("com.google.dagger:hilt-compiler:2.57.1")

    // ==================== LIFECYCLE ====================
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.lifecycle.service)

    // ==================== NAVIGATION ====================
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ==================== IMAGE LOADING ====================
    // Glide
    implementation(libs.glide)
    ksp(libs.compiler)

    // Picasso
    implementation(libs.picasso)

    // Coil (Kotlin-first)
    implementation(libs.coil)


    // ==================== NETWORKING ====================
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.moshi)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    // ==================== JSON PARSING ====================
    implementation(libs.gson)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // ==================== COROUTINES ====================
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // ==================== ROOM DATABASE ====================
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // ==================== DATASTORE ====================
    implementation(libs.androidx.datastore.preferences)

    // ==================== HILT (DI) ====================
//    implementation("com.google.dagger:hilt-android:2.51")
//    ksp("com.google.dagger:hilt-compiler:2.51")

    // ==================== GOOGLE SERVICES ====================
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.play.services.auth)

    // ==================== WORK MANAGER ====================
    implementation(libs.androidx.work.runtime.ktx)

    // ==================== UI LIBRARIES ====================
    // CircleImageView
    implementation(libs.circleimageview)

    // Lottie Animations
    implementation(libs.lottie)

    // ViewPager2
    implementation(libs.androidx.viewpager2)

    // SwipeRefreshLayout
    implementation(libs.androidx.swiperefreshlayout)

    // SplashScreen
    implementation(libs.androidx.core.splashscreen)


    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
}