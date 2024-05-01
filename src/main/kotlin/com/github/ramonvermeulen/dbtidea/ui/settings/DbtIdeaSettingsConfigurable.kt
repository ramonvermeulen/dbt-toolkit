package com.github.ramonvermeulen.dbtidea.ui.settings

import com.github.ramonvermeulen.dbtidea.services.DbtIdeaSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextField
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.Dimension
import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

class DbtIdeaSettingsConfigurable(project: Project) : Configurable {
    private var dbtIdeaSettingsService = project.service<DbtIdeaSettingsService>()
    private var dbtProjectDirField = JBTextField()
    private var dbtTargetDirField = JBTextField()
    private var envVarsTable = JBTable()
    private var settingsPanel = JPanel(BorderLayout(5, 5))

    override fun getDisplayName(): String {
        return "dbtIdea Settings"
    }

    override fun createComponent(): JComponent {
        SwingUtilities.invokeLater {
            val fieldsPanel = createFieldsPanel()
            val envVarsPanel = createEnvVarsPanel()

            settingsPanel.add(fieldsPanel, BorderLayout.NORTH)
            settingsPanel.add(envVarsPanel, BorderLayout.CENTER)
        }
        return settingsPanel
    }

    private fun createFieldsPanel(): JPanel {
        return JPanel(GridBagLayout()).apply {
            val gbc = GridBagConstraints().apply {
                anchor = GridBagConstraints.WEST
                weightx = 1.0
                gridwidth = GridBagConstraints.REMAINDER
                fill = GridBagConstraints.HORIZONTAL
                insets = JBUI.insets(5, 0)
            }
            add(JLabel("dbt project root:"), gbc)
            add(dbtProjectDirField, gbc)
            add(JLabel("dbt target directory:"), gbc)
            add(dbtTargetDirField, gbc)
        }
    }

    private fun createEnvVarsPanel(): JPanel {
        val model = DefaultTableModel(arrayOf("Name", "Value"), 0)
        envVarsTable.model = model

        // Add "+" button to add a row to the table
        val addButton = JButton("+").apply {
            addActionListener {
                model.addRow(arrayOf("", ""))
            }
        }

        val removeButton = JButton("-").apply {
            addActionListener {
                val selectedRow = envVarsTable.selectedRow
                if (selectedRow != -1) {
                    model.removeRow(selectedRow)
                }
            }
        }

        val buttonsPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(addButton)
            add(Box.createRigidArea(Dimension(5, 0)))
            add(removeButton)
        }

        dbtIdeaSettingsService.state.settingsEnvVars.forEach { (name, value) ->
            (envVarsTable.model as DefaultTableModel).addRow(arrayOf(name, value))
        }

        return JPanel(BorderLayout(5, 5)).apply {
            add(JLabel("Environment Variables:"), BorderLayout.NORTH)
            add(JScrollPane(envVarsTable), BorderLayout.CENTER)
            add(buttonsPanel, BorderLayout.SOUTH)
        }
    }


    override fun reset() {
        dbtProjectDirField.text = dbtIdeaSettingsService.state.dbtProjectsDir
        dbtTargetDirField.text = dbtIdeaSettingsService.state.dbtTargetDir

        while (envVarsTable.model.rowCount > 0) {
            (envVarsTable.model as DefaultTableModel).removeRow(0)
        }
    }

    override fun isModified(): Boolean {
        val envVars = mutableMapOf<String, String>()
        for (i in 0 until envVarsTable.model.rowCount) {
            val name = envVarsTable.model.getValueAt(i, 0) as String
            val value = envVarsTable.model.getValueAt(i, 1) as String
            envVars[name] = value
        }

        return dbtProjectDirField.text != dbtIdeaSettingsService.state.settingsDbtProjectDir ||
                dbtTargetDirField.text != dbtIdeaSettingsService.state.settingsDbtTargetDir ||
                envVars != dbtIdeaSettingsService.state.settingsEnvVars
    }

    override fun apply() {
        val envVars = mutableMapOf<String, String>()
        for (i in 0 until envVarsTable.model.rowCount) {
            val name = envVarsTable.model.getValueAt(i, 0) as String
            val value = envVarsTable.model.getValueAt(i, 1) as String
            envVars[name] = value
        }

        dbtIdeaSettingsService.state.settingsDbtProjectDir = dbtProjectDirField.text
        dbtIdeaSettingsService.state.settingsDbtTargetDir = dbtTargetDirField.text
        dbtIdeaSettingsService.state.settingsEnvVars = envVars
    }
}