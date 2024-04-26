package com.github.ramonvermeulen.dbtidea.services

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "DbtIdeaSettings",
    storages = [Storage("DbtIdeaSettings.xml")]
)
@Service(Service.Level.APP)
class DbtIdeaSettingsService() : PersistentStateComponent<DbtIdeaSettingsService.State> {
    data class State(
        var dbtProjectDir: String = "",
    )

    private var state = State(
        dbtProjectDir = ""
    )

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }
}