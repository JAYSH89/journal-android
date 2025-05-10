plugins {
    alias(libs.plugins.journal.android.application.compose)
    alias(libs.plugins.journal.android.junit5)
    alias(libs.plugins.journal.hilt)
    alias(libs.plugins.journal.jvm.ktor)
    alias(libs.plugins.journal.arrow)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "nl.jaysh.journal"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.feature.authentication)
    implementation(projects.feature.dashboard)
    implementation(projects.feature.dailyintake)
    implementation(projects.feature.food)
    implementation(projects.feature.settings)
    implementation(projects.feature.weightmanagement)

    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.ui)

    // Androidx
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.bundles.lifecycle)

    // Hilt [https://dagger.dev/hilt/]
    implementation(libs.hilt.navigation)

    // Kotlinx Coroutines [https://github.com/Kotlin/kotlinx.coroutines]
    implementation(libs.kotlinx.coroutines.core)

    // Kotlinx Serialization [https://github.com/Kotlin/kotlinx.serialization]
    implementation(libs.kotlinx.serialization.json)

    // Logging Napier [https://github.com/AAkira/Napier]
    implementation(libs.napier)

    debugImplementation(libs.androidx.ui.tooling)
}