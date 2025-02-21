plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.sagarjogadia28.tracker_domain"
}

dependencies {
    implementation(project(Modules.core))
}