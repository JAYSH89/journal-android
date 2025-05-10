plugins {
    alias(libs.plugins.journal.android.library)
    alias(libs.plugins.journal.android.junit5)
    alias(libs.plugins.journal.hilt)
    alias(libs.plugins.journal.arrow)
}

android {
    namespace = "nl.jaysh.journal.core.datastore"
}

dependencies {
    api(projects.core.common)
    api(projects.core.domain)

    implementation(libs.androidx.dataStore)
    implementation(libs.napier)
}
