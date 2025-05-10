plugins {
    alias(libs.plugins.journal.android.library)
    alias(libs.plugins.journal.jvm.junit5)
    alias(libs.plugins.journal.arrow)
    alias(libs.plugins.journal.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "nl.jaysh.journal.core.data"
}

dependencies {
    api(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.core.domain)
    implementation(libs.napier)
}
