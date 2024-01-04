import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val ANDROIDX_CORE = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE}"
    const val ANDROIDX_LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"
    const val ANDROIDX_ACTIVITY = "androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY}"

    const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:${Versions.COMPOSE_ACTIVITY}"
    const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val COMPOSE_CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout-compose:${Versions.COMPOSE_CONSTRAINTLAYOUT}"

    const val ACCOMPANIST_SYSTEMUICONTROLLER = "com.google.accompanist:accompanist-systemuicontroller:${Versions.ACCOMPANIST}"
    const val ACCOMPANIST_FLOWLAYOUT = "com.google.accompanist:accompanist-flowlayout:${Versions.ACCOMPANIST}"
    const val ACCOMPANIST_PAGER = "com.google.accompanist:accompanist-pager:${Versions.ACCOMPANIST}"
    const val ACCOMPANIST_PAGER_INDICATOR = "com.google.accompanist:accompanist-pager-indicators:${Versions.ACCOMPANIST}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OKHTTP_LOGGING_INTERCEPTER = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_LOGGING_INTERCEPTOR}"
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"

    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val EXT_JUNIT = "androidx.test.ext:junit:${Versions.EXT_JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val COMPOSE_UI_TEST = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"

    const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"
    const val HILT_NAVIGATION = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION}"

    const val LANDSCAPIST_GLIDE = "com.github.skydoves:landscapist-glide:${Versions.LANDSCAPIST_GLIDE}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"

    const val HAWK = "com.orhanobut:hawk:${Versions.HAWK}"

    const val BROWSER = "androidx.browser:browser:${Versions.BROWSER}"
}

fun DependencyHandler.core(){
    implementation(Dependencies.ANDROIDX_CORE)
    implementation(Dependencies.ANDROIDX_LIFECYCLE)
    implementation(Dependencies.ANDROIDX_ACTIVITY)
}

fun DependencyHandler.compose(){
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_PREVIEW)
    implementation(Dependencies.COMPOSE_ACTIVITY)
    implementation(Dependencies.COMPOSE_NAVIGATION)
    debugImplementation(Dependencies.COMPOSE_UI_TOOLING)
    implementation(Dependencies.COMPOSE_CONSTRAINTLAYOUT)
}

fun DependencyHandler.accompanist(){
    implementation(Dependencies.ACCOMPANIST_SYSTEMUICONTROLLER)
    implementation(Dependencies.ACCOMPANIST_FLOWLAYOUT)
    implementation(Dependencies.ACCOMPANIST_PAGER)
    implementation(Dependencies.ACCOMPANIST_PAGER_INDICATOR)
}

fun DependencyHandler.network(){
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER)
    implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTER)
}

fun DependencyHandler.testing(){
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.EXT_JUNIT)
    androidTestImplementation(Dependencies.ESPRESSO_CORE)
    androidTestImplementation(Dependencies.COMPOSE_UI_TEST)
}

fun DependencyHandler.hilt(){
    implementation(Dependencies.HILT)
    kapt(Dependencies.HILT_COMPILER)
    implementation(Dependencies.HILT_NAVIGATION)
}

fun DependencyHandler.glide(){
    implementation(Dependencies.LANDSCAPIST_GLIDE)
    implementation(Dependencies.GLIDE)
}

fun DependencyHandler.hawk(){
    implementation(Dependencies.HAWK)
}

fun DependencyHandler.browser(){
    implementation(Dependencies.BROWSER)
}

fun DependencyHandler.dataModule(){
    implementation(project(":data"))
}

fun DependencyHandler.domainModule(){
    implementation(project(":domain"))
}