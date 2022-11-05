plugins {
    id("android-application-conventions")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.mmolosay.playground"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
}

dependencies {

    // AndroidX
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha02")

    // Design
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.compose.material3:material3:1.0.0-beta03")

    // Compose
    implementation("androidx.compose.ui:ui:1.2.1")
    implementation("androidx.compose.foundation:foundation:1.2.1")
    implementation("androidx.compose.material:material-icons-core:1.2.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
    implementation("androidx.activity:activity-compose:1.6.0")

    // Third-party
    implementation("com.github.mmolosay.stringannotations:compose:1.8.0")
//    implementation("com.github.mmolosay.resource:resource-context:1.1.3")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}