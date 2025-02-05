plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.gradle.plugin)
}

android {
    namespace = "banquemisr.challenge05.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField(
                "String",
                "IMAGE_BASE_URL",
                "\"https://image.tmdb.org/t/p/w500\""
            )
            buildConfigField(
                "String",
                "TOKEN",
                "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNTcwNzM2NTE0YTM2Y2E4OTk5NzNkNjY1ZmEwOTE2YyIsIm5iZiI6MTczMjUyOTc3MS4xOTMxMTUsInN1YiI6IjY3NDQ0Y2MxZGFlMmU2YTkzODI1ZTE5NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DrEs_tp7PjCa1fibRq8vhs_C-WEdeCCCbF8vngiyKWQ\""
            )


            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField(
                "String",
                "IMAGE_BASE_URL",
                "\"https://image.tmdb.org/t/p/w500\""
            )
            buildConfigField(
                "String",
                "TOKEN",
                "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNTcwNzM2NTE0YTM2Y2E4OTk5NzNkNjY1ZmEwOTE2YyIsIm5iZiI6MTczMjUyOTc3MS4xOTMxMTUsInN1YiI6IjY3NDQ0Y2MxZGFlMmU2YTkzODI1ZTE5NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DrEs_tp7PjCa1fibRq8vhs_C-WEdeCCCbF8vngiyKWQ\""
            )

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":domain"))
    implementation(libs.core.ktx)
    // Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)

    // Retrofit and OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.room.paging)
}
