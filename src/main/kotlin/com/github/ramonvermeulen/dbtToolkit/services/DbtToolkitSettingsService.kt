package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.vfs.VirtualFile
import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

@State(
    name = "dbtToolkitSettings",
    storages = [Storage("dbtToolkitSettings.xml")],
)
@Service(Service.Level.PROJECT)
class DbtToolkitSettingsService : PersistentStateComponent<DbtToolkitSettingsService.State> {
    data class State(
        var initialSettingsAreSet: Boolean = false,
        var settingsDbtProjectDir: String = "",
        var settingsDbtTargetDir: String = "",
        var settingsDotEnvFilePath: String = "",
        var settingsEnvVars: Map<String, String> = mapOf(),
        var settingsDbtCommandTimeout: Int = 120,
        var dbtProjectsDir: String = "",
        var dbtTargetDir: String = "",
        var dbtProject: Map<String, Any> = mapOf(),
        var dbtModelPaths: List<String> = listOf(),
        var dbtSeedPaths: List<String> = listOf(),
        var dbtTestPaths: List<String> = listOf(),
        var dbtMacroPaths: List<String> = listOf(),
        var dbtProjectName: String = "",
        var dbtVersionMajor: Int? = null,
        var dbtVersionMinor: Int? = null,
    )

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    private fun setInitialSettings(dbtProjectDir: String) {
        state.settingsDbtProjectDir = dbtProjectDir
        state.settingsDbtTargetDir = "${dbtProjectDir}/target"
        if (File("${dbtProjectDir}/.env").exists()) {
            state.settingsDotEnvFilePath = "${dbtProjectDir}/.env"
        }
        state.initialSettingsAreSet = true
    }

    fun parseDbtProjectFile(file: VirtualFile) {
        val inputStream = file.inputStream
        state.dbtProjectsDir = file.parent.path

        if (!state.initialSettingsAreSet) {
            setInitialSettings(state.dbtProjectsDir)
        }

        state.dbtTargetDir = "${file.parent.path}/target"
        val reader = BufferedReader(InputStreamReader(inputStream))
        val yaml = Yaml()
        state.dbtProject = yaml.load(reader)
        state.dbtModelPaths = state.dbtProject["model-paths"].safeCastToList() ?: listOf()
        state.dbtTestPaths = state.dbtProject["test-paths"].safeCastToList() ?: listOf()
        state.dbtSeedPaths = state.dbtProject["seed-paths"].safeCastToList() ?: listOf()
        state.dbtMacroPaths = state.dbtProject["macro-paths"].safeCastToList() ?: listOf()
        state.dbtProjectName = (state.dbtProject["name"] as? String).toString()
        reader.close()
    }
}

private fun Any?.safeCastToList(): List<String>? {
    return (this as? List<*>)?.filterIsInstance<String>()
}
