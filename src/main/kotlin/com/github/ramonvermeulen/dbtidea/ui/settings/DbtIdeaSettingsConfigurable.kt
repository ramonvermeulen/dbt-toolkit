package com.github.ramonvermeulen.dbtidea.ui.settings

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.labels.LinkLabel
import com.intellij.ui.table.JBTable
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.table.DefaultTableModel

class DbtIdeaSettingsConfigurable(project: Project) : Configurable {
    private var dbtIdeaSettingsService = project.service<DbtIdeaSettingsService>()
    private var dbtProjectDirField = JBTextField()
    private var dbtTargetDirField = JBTextField()
    private var envVarsTable = JBTable()

    override fun getDisplayName(): String {
        return "dbtIdea Settings"
    }

    override fun createComponent(): JComponent {
        val settingsPanel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints()

        gbc.fill = GridBagConstraints.HORIZONTAL
        gbc.insets = Insets(2, 2, 2, 2)

        // Create table for environment variables
        val model = DefaultTableModel(arrayOf("Name", "Value"), 0)
        envVarsTable.model = model

        // Add "+" button to add a row to the table
        val addButton = JButton("+").apply {
            addActionListener {
                model.addRow(arrayOf("", ""))
            }
        }

        // Add "-" button to remove a row from the table
        val removeButton = JButton("-").apply {
            addActionListener {
                val selectedRow = envVarsTable.selectedRow
                if (selectedRow != -1) {
                    model.removeRow(selectedRow)
                }
            }
        }

        // dbt project root
        gbc.gridx = 0
        gbc.gridy = 0
        settingsPanel.add(JLabel("dbt project root:"), gbc)

        gbc.gridx = 1
        dbtProjectDirField.preferredSize = Dimension(200, 20)
        settingsPanel.add(dbtProjectDirField, gbc)

        // dbt target directory
        gbc.gridx = 0
        gbc.gridy = 1
        settingsPanel.add(JLabel("dbt target directory:"), gbc)

        gbc.gridx = 1
        dbtTargetDirField.preferredSize = Dimension(200, 20)
        settingsPanel.add(dbtTargetDirField, gbc)

        // Environment Variables
        gbc.gridx = 0
        gbc.gridy = 2
        settingsPanel.add(JLabel("Environment Variables:"), gbc)

        gbc.gridx = 1
        settingsPanel.add(envVarsTable, gbc)

        gbc.gridx = 1
        settingsPanel.add(addButton, gbc)

        gbc.gridx = 1
        settingsPanel.add(removeButton, gbc)

        return settingsPanel
    }

    override fun reset() {
        dbtProjectDirField.text = dbtIdeaSettingsService.state.dbtProjectDir
        dbtTargetDirField.text = dbtIdeaSettingsService.state.dbtTargetDir
        // Reset the environment variables table
    }

    override fun isModified(): Boolean {
        // check if settings have been modified
        return true
    }

    override fun apply() {
        TODO("Not yet implemented")
    }
}