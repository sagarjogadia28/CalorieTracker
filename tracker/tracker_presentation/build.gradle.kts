plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.sagarjogadia28.tracker_presentation"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.core_ui))
    implementation(project(Modules.trackerDomain))
    implementation(libs.coil.compose)
}