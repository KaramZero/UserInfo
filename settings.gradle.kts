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
    }
}

rootProject.name = "User Info"
include(":app")
include(":core")
include(":data")
include(":features")
include(":featuresv2")
include(":features:userinput")
include(":features:useroutput")
include(":featuresv2:userinputv2")
include(":featuresv2:useroutputv2")
