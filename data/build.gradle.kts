plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply<LibraryGradlePlugin>()

android {
    namespace = "com.example.data"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://www.themealdb.com/\"")
    }
}

dependencies {
    domainModule()
    hilt()
    network()
}