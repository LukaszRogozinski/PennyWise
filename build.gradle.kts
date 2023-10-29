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
}
