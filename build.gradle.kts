import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidBasePlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.applicatiion) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ktlint) apply false
}

subprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
    }

    plugins.withType<AndroidBasePlugin>().configureEach {
        extensions.configure<BaseExtension> {
            compileSdkVersion(rootProject.libs.versions.compileSdk.get().toInt())

            defaultConfig {
                minSdk = rootProject.libs.versions.minSdk.get().toInt()
                targetSdk = rootProject.libs.versions.targetSdk.get().toInt()
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }
}
