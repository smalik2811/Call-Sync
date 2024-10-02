pluginManagement {
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
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }

}

rootProject.name = "Call Sync"
include(":app")
include(":core:designsystem")
include(":core:model")
include(":core:firebase")
include(":core:constant")
include(":core:ui")
include(":core:datastore")
include(":core:database")
include(":core:data")
include(":feature:home")
include(":feature:onboard")
include(":core:workmanager")
include(":core:analytics")
include(":core:network")
include(":core:common")
