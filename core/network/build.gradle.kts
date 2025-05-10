plugins {
    alias(libs.plugins.journal.android.library)
    alias(libs.plugins.journal.jvm.junit5)
    alias(libs.plugins.journal.jvm.ktor)
    alias(libs.plugins.journal.hilt)
    alias(libs.plugins.journal.arrow)
}

android {
    namespace = "nl.jaysh.journal.core.network"
}

dependencies {
    api(projects.core.common)
    api(projects.core.domain)

    implementation(libs.androidx.encrypted.shared.preferences)
    implementation(libs.napier)

    testImplementation(libs.ktor.client.mock)
}
