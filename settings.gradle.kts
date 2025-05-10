pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "journal"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core")
include(":core:common")
include(":core:data")
include(":core:datastore")
include(":core:network")
include(":core:testing")
include(":core:ui")
include(":core:domain")
include(":core:designsystem")
include(":feature")
include(":feature:authentication")
include(":feature:dashboard")
include(":feature:weightmanagement")
include(":feature:food")
include(":feature:dailyintake")
include(":feature:settings")
