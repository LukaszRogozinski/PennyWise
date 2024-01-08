@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.lrogozinski.pennywise.resources"
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
}
