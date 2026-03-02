plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.0.21"
    jacoco
}

android {
    namespace = "com.alcalist.tecmeli"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.alcalist.tecmeli"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLIENT_ID", "\"${project.findProperty("MELI_CLIENT_ID") ?: ""}\"")
        buildConfigField("String", "CLIENT_SECRET", "\"${project.findProperty("MELI_CLIENT_SECRET") ?: ""}\"")
        buildConfigField("String", "REFRESH_TOKEN", "\"${project.findProperty("MELI_REFRESH_TOKEN") ?: ""}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.foundation)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Icons & Image
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.io.coil.compose)

    testImplementation(libs.junit)
    // Test dependencies
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.mockwebserver)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

afterEvaluate {
    tasks.register("jacocoTestReport") {
        dependsOn("testDebugUnitTest")
        doLast {
            val jacocoExec = file("build/jacoco/testDebugUnitTest.exec")
            println("\n📊 COVERAGE REPORT")
            println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")

            if (jacocoExec.exists()) {
                println("✓ Coverage data found:")
                println("  Path: ${jacocoExec.absolutePath}")
                println("  Size: ${jacocoExec.length()} bytes")
                println("\n✓ Test execution completed successfully")
                println("\n📁 Coverage files:")
                println("  - exec file: app/build/jacoco/testDebugUnitTest.exec")
                println("  - test results: app/build/test-results/testDebugUnitTest/")
                println("  - test reports: app/build/reports/tests/testDebugUnitTest/")
            } else {
                println("✗ Coverage data not found")
            }
            println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
        }
    }
}
