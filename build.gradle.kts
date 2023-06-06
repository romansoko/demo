buildscript {
    apply(from = "./properties.gradle.kts")
    repositories {
        val repositoryConfig: (RepositoryHandler) -> Unit by extra
        repositoryConfig(this)
        mavenLocal()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
        classpath(libs.plugin.fidelity.android)
        classpath(libs.plugin.android.compose)
        classpath("org.jacoco:org.jacoco.core:0.8.7")
        if (project.findProperty("download-specific-dependencies.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-download-specific-dependencies-plugin:0.1.6")
        if (project.findProperty("create-custom-module.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-create-module-plugin:0.1.8")
        if (project.findProperty("download-specific-dependencies.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-feature-statistical-test-coverage:0.2.10")
        if (project.findProperty("adb-util.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-feature-adb-util:0.2.11")
        if (project.findProperty("optimize-setting.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-feature-optimize-setting:0.1.4")
        if (project.findProperty("sonar-test-module.enable") == "true") {
            classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:4.0.0.2929")
        }
        if (project.findProperty("module-diff.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-feature-diff-test-coverage:0.1.29")
        if (project.findProperty("toggle-request.enable") == "true")
            classpath("com.fmr.PR100660:ap011663-toggle-request-plugin:0.1.96")
    }
}



if (project.findProperty("toggle-request.enable") == "true")
    apply(plugin = "toggle-request")
if (project.findProperty("download-specific-dependencies.enable") == "true")
    apply(plugin = "download-specific-dependencies")
if (project.findProperty("create-custom-module.enable") == "true")
    apply(plugin = "create-module-plugin")
if (file("$rootDir/use-locals").exists())
    apply(from = "./build-support/local-module.gradle")
if (project.findProperty("download-abt.enable") == "true")
    apply(from = "./download_abt.gradle.kts")
if (project.findProperty("download-specific-dependencies.enable") == "true")
    apply(plugin = "statistical-test-coverage")
if (project.findProperty("adb-util.enable") == "true")
    apply(plugin = "adb-util")
if (project.findProperty("optimize-setting.enable") == "true")
    apply(plugin = "copy-settings-configuration")
if (project.findProperty("sonar-test-module.enable") == "true") {
    apply(plugin = "org.sonarqube")
    apply(plugin = "jacoco")
    apply(from = "./build-support/sonar-conf.gradle")
}
if (project.findProperty("domain-test.enable") == "true") {
    apply(from = "./build-support/domain-test.gradle.kts")
}
if (project.findProperty("module-diff.enable") == "true") {
    apply(from = "./build-support/jacoco-conf.gradle")
}
apply(from = "./properties.gradle.kts")
apply(from = "./build-support/common-publish.gradle.kts")
apply(from = "./build-support/kover-conf.gradle")
apply(from = "./build-support/team-project.gradle")
apply(from = "./build-support/create-custom-module.gradle.kts")
allprojects {
    repositories {
        mavenLocal()
    }
    repositories {
        val repositoryConfig: (RepositoryHandler) -> Unit by rootProject.extra
        repositoryConfig(this)
    }
}
plugins {
    id("org.jetbrains.kotlin.android") version libs.versions.kotlin apply false
    `maven-publish`
    id("com.google.devtools.ksp").version("1.7.10-1.0.6").apply(false)
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover
}
subprojects {
    afterEvaluate {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = libs.versions.jdk.get()
                freeCompilerArgs = listOf(
                    "-Xopt-in=kotlin.RequiresOptIn"
                )
            }
        }

        configurations.configureEach {
            attributes {
                attribute(
                    TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE,
                    libs.versions.jdk.get().toInt()
                )
            }
        }
    }
}

