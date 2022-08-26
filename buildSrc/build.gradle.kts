repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    // <1> resolve plugins
    implementation("com.android.tools.build:gradle:7.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
}

//tasks.create("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}