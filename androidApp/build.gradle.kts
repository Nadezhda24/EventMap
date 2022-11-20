plugins {
    id("com.android.application")
    kotlin("android")
    id ("kotlin-android-extensions")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.example.map.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
    }

    androidExtensions {
        var experimental = true
    }


}
val ktorVersion = "1.4.0"
val coroutinesVersion = "1.3.9-native-mt"


dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("org.osmdroid:osmdroid-android:6.1.11")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation ("com.squareup.picasso:picasso:2.5.2")

}