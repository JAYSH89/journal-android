plugins {
    alias(libs.plugins.journal.android.library.compose)
    alias(libs.plugins.journal.android.junit5)
}

android {
    namespace = "nl.jaysh.journal.core.designsystem"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.napier)

    debugImplementation(libs.androidx.ui.tooling)

    api(libs.androidx.material3)
}