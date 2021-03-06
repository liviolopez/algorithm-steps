import Dep.addDependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.parcelize")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Sdk.compile)
    buildToolsVersion = Sdk.buildTool
    ndkVersion = Sdk.ndk

    defaultConfig {
        applicationId = "dev.all4.algorithmSteps"
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)

        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        named("debug") {
            isDebuggable = true
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true

        // TEMPORARY
        //allWarningsAsErrors = true
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        freeCompilerArgs += "-Xopt-in=kotlin.Experimental"
        freeCompilerArgs += "-Xallow-jvm-ir-dependencies"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dep.compose_alpha
        kotlinCompilerVersion = Dep.kotlin
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    addDependencies()
}