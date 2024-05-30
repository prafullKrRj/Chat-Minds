buildscript {
    extra.apply {
        set("room_version", "2.5.2")
    }

}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}