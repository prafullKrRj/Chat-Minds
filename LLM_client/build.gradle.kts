plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}
dependencies {
    implementation(libs.openai.client.v372)
    // coroutines dependency
    implementation(libs.kotlinx.coroutines.core)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}