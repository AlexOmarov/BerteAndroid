@file:Suppress("DSL_SCOPE_VIOLATION", "UnstableApiUsage") // TODO: Remove after KTIJ-19369
plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "com.example.berteandroid"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "com.example.berteandroid"
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.compile.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Some library (room / koin) has this transitive dep. We already have all the classes in android annotations lib
    configurations {
        implementation.get().exclude(mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    annotationProcessor(libs.room.compiler)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.livedata)
    implementation(libs.compose.material)
    implementation(libs.room.runtime)
    implementation(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalar)
    implementation(libs.retrofit)

    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.koin.compose)
    implementation(libs.koin)
    implementation(libs.work.ktx)


    ksp(libs.room.compiler)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.manifest)

    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }
}