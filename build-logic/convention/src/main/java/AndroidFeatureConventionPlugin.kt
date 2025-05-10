import com.android.build.gradle.LibraryExtension
import nl.jaysh.convention.addFeatureUiDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("journal.android.library.compose")
                apply("journal.hilt")
                apply("journal.arrow")
            }

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
            }

            dependencies {
                addFeatureUiDependencies(target)
            }
        }
    }
}