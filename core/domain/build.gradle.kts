plugins {
    alias(libs.plugins.journal.jvm.library)
    alias(libs.plugins.journal.jvm.junit5)
    alias(libs.plugins.journal.arrow)
    alias(libs.plugins.journal.hilt)
}

dependencies {
    implementation(libs.napier)
}
