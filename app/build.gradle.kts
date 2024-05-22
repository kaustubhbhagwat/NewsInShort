plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.newsinshort"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newsinshort"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeBom)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)
    implementation(Dependencies.lifecycleViewModelKtx)
    implementation(Dependencies.hiltAndroid)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.firebase.auth)
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation ("androidx.compose.material:material:1.6.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //
    implementation(libs.androidx.appcompat)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.firebase.firestore.ktx)
    kapt(Dependencies.hiltAndroidCompiler)
    kapt(Dependencies.hiltCompiler)
    testImplementation(libs.androidx.junit)

    implementation(project(Modules.utilities))
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
    implementation(Dependencies.hiltNavigationCompose)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.gsonConverter)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.moshi)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.splashScreen)
    implementation(Dependencies.coil)
}

kapt{
    correctErrorTypes = true
}