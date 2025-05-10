plugins {
    alias(libs.plugins.journal.android.library)
    alias(libs.plugins.journal.jvm.junit5)
    alias(libs.plugins.journal.hilt)
    alias(libs.plugins.journal.arrow)
}

android {
    namespace = "nl.jaysh.journal.core.testing"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.network)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)

    implementation(libs.junit5.api)
}
