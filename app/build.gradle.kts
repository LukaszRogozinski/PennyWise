import BuildConstants.LauncherOverlay

plugins {
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.easylauncher)
}

android {
    namespace = BuildConstants.ApplicationId

    defaultConfig {
        applicationId = BuildConstants.ApplicationId
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildFeatures {
            buildConfig = true
        }
    }

    signingConfigs {
        create("prod") {
            keyAlias = findProperty("pennywise.keystore.alias").toString()
            keyPassword = findProperty("pennywise.keystore.keyPassword").toString()
            storeFile = file(findProperty("pennywise.keystore.path").toString())
            storePassword = findProperty("pennywise.keystore.password").toString()
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
            resValue("string", "app_name", "Penny Wise Dev")
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
    implementation(projects.coreUi)
    implementation(projects.features.dashboard)
    implementation(projects.navigation)
    implementation(projects.resources)

    implementation(libs.activity.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.timber)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
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
