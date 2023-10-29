import BuildConstants.LauncherOverlay

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.easylauncher)
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

android {
    namespace = BuildConstants.ApplicationId
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = BuildConstants.ApplicationId
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("prod") {
            keyAlias = findProperty("cashflow.keystore.alias").toString()
            keyPassword = findProperty("cashflow.keystore.keyPassword").toString()
            storeFile = file(findProperty("cashflow.keystore.path").toString())
            storePassword = findProperty("cashflow.keystore.password").toString()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["prod"]
        }
    }

    flavorDimensions += AppEnvironment.Dimension

    productFlavors {
        create(AppEnvironment.Dev()) {
            dimension = AppEnvironment.Dimension
            applicationIdSuffix = AppEnvironment.Dev.applicationIdSuffix
            resValue("string", "app_name", "Cash Flow Dev")
        }

        create(AppEnvironment.Prod()) {
            dimension = AppEnvironment.Dimension
            applicationIdSuffix = AppEnvironment.Prod.applicationIdSuffix
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    configureEasyLauncher()
}

dependencies {
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    testImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

/**
 * This plugin applies label overlay on top of launcher icon depending on flavor.
 */
fun configureEasyLauncher() {
    easylauncher {
        productFlavors {
            AppEnvironment.values().forEach { environment ->
                create(environment()) {
                    if (environment.label != null) {
                        filters(
                            chromeLike(
                                label = environment.label,
                                labelPadding = LauncherOverlay.LabelPadding
                            )
                        )
                    } else {
                        enabled.set(false)
                    }
                }
            }
        }
    }
}
