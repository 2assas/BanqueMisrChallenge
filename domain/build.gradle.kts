plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
}