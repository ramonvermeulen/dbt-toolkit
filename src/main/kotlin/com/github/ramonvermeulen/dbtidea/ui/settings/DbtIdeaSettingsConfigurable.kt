package com.github.ramonvermeulen.dbtidea.ui.settings

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextField
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class DbtIdeaSettingsConfigurable(project: Project) : Configurable {
    private var dbtIdeaSettingsService = project.service<DbtIdeaSettingsService>()
    private var dbtProjectDirField = JTextField(140)
    private var dbtTargetDirField = JBTextField(140)

    override fun getDisplayName(): String {
        return "dbtIdea Settings"
    }

    override fun createComponent(): JComponent {
        val settingsPanel = JPanel()
        dbtProjectDirField.text = dbtIdeaSettingsService.state.dbtProjectDir
        dbtTargetDirField.text = dbtIdeaSettingsService.state.dbtTargetDir
        settingsPanel.add(JLabel("dbt project root:"))
        settingsPanel.add(dbtProjectDirField)
        settingsPanel.add(JLabel("dbt target directory:"))
        settingsPanel.add(dbtTargetDirField)
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
