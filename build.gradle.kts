buildscript {
    extra.apply {
        set("room_version", "2.5.2")
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}