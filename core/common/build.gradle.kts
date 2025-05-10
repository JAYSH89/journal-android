plugins {
    alias(libs.plugins.journal.jvm.library)
    alias(libs.plugins.journal.jvm.junit5)
    alias(libs.plugins.journal.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.napier)
}
