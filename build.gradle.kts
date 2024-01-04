buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}