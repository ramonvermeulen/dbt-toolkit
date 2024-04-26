package com.github.ramonvermeulen.dbtidea.ui

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class DbtIdeaSettingsConfigurable : Configurable {
    private var dbtIdeaSettingsService = service<DbtIdeaSettingsService>()
    private var dbtProjectDirField = JTextField(140)
    override fun getDisplayName(): String {
        return "dbtIdea Settings"
    }

    override fun createComponent(): JComponent {
        val settingsPanel = JPanel()
        dbtProjectDirField.text = dbtIdeaSettingsService.state.dbtProjectDir
        settingsPanel.add(JLabel("dbt project root:"))
        settingsPanel.add(dbtProjectDirField)
        return settingsPanel
    }

    override fun isModified(): Boolean {
        // check if settings have been modified
        return true
    }

    override fun apply() {
        TODO("Not yet implemented")
    }
}