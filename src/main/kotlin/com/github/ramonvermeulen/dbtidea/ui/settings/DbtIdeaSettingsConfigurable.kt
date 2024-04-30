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

    override fun getDisplayName(): String {
        return "dbtIdea Settings"
    }

    override fun createComponent(): JComponent {
        val settingsPanel = JPanel(BorderLayout(5, 5))

        val fieldsPanel = createFieldsPanel()
        val envVarsPanel = createEnvVarsPanel()

        settingsPanel.add(fieldsPanel, BorderLayout.NORTH)
        settingsPanel.add(envVarsPanel, BorderLayout.CENTER)

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

        val buttonsPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(addButton)
            add(Box.createRigidArea(Dimension(5, 0)))
            add(removeButton)
        }

        return JPanel(BorderLayout(5, 5)).apply {
            add(JLabel("Environment Variables:"), BorderLayout.NORTH)
            add(JScrollPane(envVarsTable), BorderLayout.CENTER)
            add(buttonsPanel, BorderLayout.SOUTH)
        }
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
        // apply settings
        return
    }
}