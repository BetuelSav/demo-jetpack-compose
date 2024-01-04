plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.example.domain"
}

dependencies {
    hilt()
    implementation(Dependencies.GSON)
}