import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "id.synrgy6team2.bookingticket"
    compileSdk = 34

    defaultConfig {
        applicationId = "id.synrgy6team2.bookingticket"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        create("release") {
            storeFile = file("file:///C:/Users/User/OneDrive/Android%20Studio%20Keys/WingsOn/keystore-wingson.jks")
            keyAlias = properties.getProperty("SIGNING_KEY_ALIAS")
            storePassword = properties.getProperty("SIGNING_STORE_PASSWORD")
            keyPassword = properties.getProperty("SIGNING_KEY_PASSWORD")
        }
        getByName("debug") {
            storeFile = file("file:///C:/Users/User/.android/debug.keystore")
            keyAlias = properties.getProperty("SIGNING_KEY_ALIAS_DEBUG")
            storePassword = properties.getProperty("SIGNING_STORE_PASSWORD_DEBUG")
            keyPassword = properties.getProperty("SIGNING_KEY_PASSWORD_DEBUG")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // data binding ini hanya untuk library dari ImagePicker/CapturePhoto :(
    buildFeatures { dataBinding = true }
}

dependencies {

    api(project(":presentation"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:hilt-compiler:2.48.1")

    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}