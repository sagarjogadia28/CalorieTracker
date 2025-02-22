plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.sagarjogadia28.core"
}