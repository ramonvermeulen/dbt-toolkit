import com.github.gradle.node.npm.task.NpmTask
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML

// import org.jsonschema2pojo.Jsonschema2Pojo

fun properties(key: String) = providers.gradleProperty(key)

fun environment(key: String) = providers.environmentVariable(key)
val lineagePanelBuildPath = "$projectDir/src/main/resources/lineage-panel-dist"

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.gradleIntelliJPlugin) // Gradle IntelliJ Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.qodana) // Gradle Qodana Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
    id("com.github.node-gradle.node") version "7.0.2"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    id("org.jsonschema2pojo") version "1.2.1" // for dbt manifest
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

node {
    download = true
    version = "22.8.0"
    nodeProjectDir = file("$projectDir/lineage-panel")
}

tasks.register("npmBuild", NpmTask::class) {
    dependsOn("npmInstall")
    args.set(listOf("run", "build"))
    workingDir = file("$projectDir/lineage-panel")
    environment.put("VITE_OUTPUT_DIR", lineagePanelBuildPath)
}

tasks.named("buildPlugin") {
    dependsOn("npmBuild")
}

tasks.all {
    // trick to ensure that the react application is always built before the plugin
    if (name == "processResources") {
        mustRunAfter("npmBuild")
    }
}

// Configure project's dependencies
repositories {
    mavenCentral()
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog
// different gradle version than example, config might vary?
jsonSchema2Pojo {
    setSource(
        listOf(
            File("${sourceSets.main.get().output.resourcesDir}/schema"),
        ),
    )
    setAnnotationStyle("gson")
    serializable = true
    targetVersion = "22"
    targetDirectory = File("$projectDir/src/main/kotlin/com/github/ramonvermeulen/dbtToolkit/generated")

}

dependencies {
//    implementation(libs.annotations)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
}

// configuration of the json schema to pojo code generation, e.g. for dbt manifest files

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(17)
}

// Configure Gradle IntelliJ Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    pluginName = properties("pluginName")
    version = properties("platformVersion")
    type = properties("platformType")

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins = properties("platformPlugins").map { it.split(',').map(String::trim).filter(String::isNotEmpty) }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = properties("pluginRepositoryUrl")
}

// Configure Gradle Kover Plugin - read more: https://github.com/Kotlin/kotlinx-kover#configuration
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }

    patchPluginXml {
        version = properties("pluginVersion")
        sinceBuild = properties("pluginSinceBuild")
        untilBuild = properties("pluginUntilBuild")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription =
            providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                with(it.lines()) {
                    if (!containsAll(listOf(start, end))) {
                        throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                    }
                    subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
                }
            }

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes =
            properties("pluginVersion").map { pluginVersion ->
                with(changelog) {
                    renderItem(
                        (getOrNull(pluginVersion) ?: getUnreleased())
                            .withHeader(false)
                            .withEmptySections(false),
                        Changelog.OutputType.HTML,
                    )
                }
            }
    }

    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    runIdeForUiTests {
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

    signPlugin {
        certificateChain = environment("CERTIFICATE_CHAIN")
        privateKey = environment("PRIVATE_KEY")
        password = environment("PRIVATE_KEY_PASSWORD")
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token = environment("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = properties("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }
}
