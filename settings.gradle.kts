pluginManagement {
    plugins {
        id("com.android.application") version "7.2.2" apply false
        id("org.jetbrains.kotlin.android") version "1.6.10" apply false
    }
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}

rootProject.name = "Playground"
include(":app")
