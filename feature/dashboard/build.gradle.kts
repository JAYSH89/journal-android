plugins {
    alias(libs.plugins.journal.android.feature)
    alias(libs.plugins.journal.android.junit5)
}

android {
    namespace = "nl.jaysh.journal.feature.dashboard"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(libs.bundles.coil)
    implementation(libs.napier)
}
