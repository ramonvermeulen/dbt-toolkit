package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VirtualFile
import org.yaml.snakeyaml.Yaml
import java.io.BufferedReader
import java.io.InputStreamReader

@State(
    name = "DbtIdeaSettings",
    storages = [Storage("DbtIdeaSettings.xml")],
)
@Service(Service.Level.PROJECT)
class DbtIdeaSettingsService(private var project: Project) : PersistentStateComponent<DbtIdeaSettingsService.State> {
    data class State(
        var dbtProjectDir: String = "",
        var dbtTargetDir: String = "",
        var dbtProject: Map<String, Any> = mapOf(),
    )
    companion object {
        const val DBT_DOCS_FILE = "index.html"
        const val DBT_MANIFEST_FILE = "manifest.json"
        const val DBT_CATALOG_FILE = "catalog.json"
    }
    var static = Companion
    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    fun parseDbtProjectFile(file: VirtualFile) {
        val inputStream = file.inputStream
        state.dbtProjectDir = file.parent.path
        state.dbtTargetDir = "${file.parent.path}/target"
        val reader = BufferedReader(InputStreamReader(inputStream))
        val yaml = Yaml()
        state.dbtProject = yaml.load(reader) as Map<String, Any>
        reader.close()
    }
}
