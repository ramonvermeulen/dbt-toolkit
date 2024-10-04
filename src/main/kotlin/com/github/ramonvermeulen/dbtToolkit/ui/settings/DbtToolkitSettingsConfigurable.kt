package com.github.ramonvermeulen.dbtToolkit.ui.settings

import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.table.DefaultTableModel

class DbtToolkitSettingsConfigurable(project: Project) : Configurable {
    private var dbtToolkitSettingsService = project.service<DbtToolkitSettingsService>()
    private var dbtProjectDirField = TextFieldWithBrowseButton()
    private var dbtTargetDirField = TextFieldWithBrowseButton()
    private var dotEnvFilePathField = TextFieldWithBrowseButton()
    private var dbtCommandTimeoutField = JBIntSpinner(0, 0, 3600)
    private var envVarsTable = JBTable()
    private var settingsPanel = JPanel()


    init {
        dbtProjectDirField.addBrowseFolderListener(
            "Select dbt Project Directory",
            null,
            project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        )
        dbtTargetDirField.addBrowseFolderListener(
            "Select dbt Target Directory",
            null,
            project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        )
        dotEnvFilePathField.addBrowseFolderListener(
            "Select .env File",
            null,
            project,
            FileChooserDescriptorFactory.createSingleFileDescriptor()
        )
    }

    override fun getDisplayName(): String {
        return "dbtToolkit Settings"
    }

    override fun createComponent(): JComponent {
        val fieldsPanel = createFieldsPanel()
        val envVarsPanel = createEnvVarsPanel()

        settingsPanel.layout = BorderLayout(5, 5)
        settingsPanel.add(fieldsPanel, BorderLayout.NORTH)
        settingsPanel.add(envVarsPanel, BorderLayout.CENTER)
        return settingsPanel
    }

    private fun createFieldsPanel(): JPanel {
        return JPanel(GridBagLayout()).apply {
            val gbc =
                GridBagConstraints().apply {
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
            add(
                JLabel("dot env file path:").apply {
                    toolTipText = "Path to a .env file that will be loaded before running dbt commands, " +
                        "will not override environment variables defined below"
                },
                gbc,
            )
            add(dotEnvFilePathField, gbc)
            add(JLabel("dbt command timeout (seconds):"), gbc)
            add(dbtCommandTimeoutField, gbc)
        }
    }

    private fun createEnvVarsPanel(): JPanel {
        val model = DefaultTableModel(arrayOf("Name", "Value"), 0)
        envVarsTable.model = model

        // Add "+" button to add a row to the table
        val addButton =
            JButton("+").apply {
                addActionListener {
                    model.addRow(arrayOf("", ""))
                }
            }

        val removeButton =
            JButton("-").apply {
                addActionListener {
                    val selectedRow = envVarsTable.selectedRow
                    if (selectedRow != -1) {
                        model.removeRow(selectedRow)
                    }
                }
            }

        val buttonsPanel =
            JPanel().apply {
                layout = BoxLayout(this, BoxLayout.X_AXIS)
                add(addButton)
                add(Box.createRigidArea(Dimension(5, 0)))
                add(removeButton)
            }

        dbtToolkitSettingsService.state.settingsEnvVars.forEach { (name, value) ->
            (envVarsTable.model as DefaultTableModel).addRow(arrayOf(name, value))
        }

        return JPanel(BorderLayout(5, 5)).apply {
            add(JLabel("Environment Variables:"), BorderLayout.NORTH)
            add(JScrollPane(envVarsTable), BorderLayout.CENTER)
            add(buttonsPanel, BorderLayout.SOUTH)
        }
    }

    override fun reset() {
        dbtProjectDirField.text = dbtToolkitSettingsService.state.dbtProjectsDir
        dbtTargetDirField.text = dbtToolkitSettingsService.state.dbtTargetDir
        dbtCommandTimeoutField.value = dbtToolkitSettingsService.state.settingsDbtCommandTimeout
        dotEnvFilePathField.text = dbtToolkitSettingsService.state.settingsDotEnvFilePath

        while (envVarsTable.model.rowCount > 0) {
            (envVarsTable.model as DefaultTableModel).removeRow(0)
        }
    }

    private fun getConfiguredEnvVars(): MutableMap<String, String> {
        val envVars = mutableMapOf<String, String>()
        for (i in 0 until envVarsTable.model.rowCount) {
            val name = envVarsTable.model.getValueAt(i, 0) as String
            val value = envVarsTable.model.getValueAt(i, 1) as String
            envVars[name] = value
        }
        return envVars
    }

    override fun isModified(): Boolean {
        val envVars = getConfiguredEnvVars()

        return dbtProjectDirField.text != dbtToolkitSettingsService.state.settingsDbtProjectDir ||
            dbtTargetDirField.text != dbtToolkitSettingsService.state.settingsDbtTargetDir ||
            dbtCommandTimeoutField.value != dbtToolkitSettingsService.state.settingsDbtCommandTimeout ||
            envVars != dbtToolkitSettingsService.state.settingsEnvVars ||
            dotEnvFilePathField.text != dbtToolkitSettingsService.state.settingsDotEnvFilePath
    }

    override fun apply() {
        val envVars = getConfiguredEnvVars()

        dbtToolkitSettingsService.state.settingsDbtProjectDir = dbtProjectDirField.text
        dbtToolkitSettingsService.state.settingsDbtTargetDir = dbtTargetDirField.text
        dbtToolkitSettingsService.state.settingsDbtCommandTimeout = dbtCommandTimeoutField.value as Long
        dbtToolkitSettingsService.state.settingsEnvVars = envVars
        dbtToolkitSettingsService.state.settingsDotEnvFilePath = dotEnvFilePathField.text
    }

    override fun disposeUIResources() {
        settingsPanel.removeAll()
    }
}
