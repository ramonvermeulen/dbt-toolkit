package com.github.ramonvermeulen.dbtToolkit.ui.settings

import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase
import javax.swing.JComponent
import javax.swing.JPanel

class DbtToolkitSettingsConfigurableTest : LightPlatformTestCase() {
    private lateinit var mainSwingComponent: JComponent
    private lateinit var configurable: DbtToolkitSettingsConfigurable

    public override fun setUp() {
        super.setUp()
        configurable = DbtToolkitSettingsConfigurable(project)
        mainSwingComponent = configurable.createComponent()
        configurable.reset()
    }

    public override fun tearDown() {
        try {
            project.service<DbtToolkitSettingsService>().loadState(DbtToolkitSettingsService.State())
        } finally {
            super.tearDown()
        }
    }

    fun `test isModified no modifications`() {
        TestCase.assertFalse(configurable.isModified())
    }

    fun `test isModified with some modifications`() {
        val dbtProjectsDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(1) as TextFieldWithBrowseButton
        dbtProjectsDirField.text = "some text"
        val dbtDotEnvDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(5) as TextFieldWithBrowseButton
        dbtDotEnvDirField.text = "/foo/bar"
        TestCase.assertTrue(configurable.isModified())
    }

    fun `test apply`() {
        val settingsService = project.service<DbtToolkitSettingsService>()

        val dbtProjectsDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(1) as TextFieldWithBrowseButton
        dbtProjectsDirField.text = "some text"
        val dbtDotEnvDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(5) as TextFieldWithBrowseButton
        dbtDotEnvDirField.text = "/foo/bar"
        configurable.apply()

        TestCase.assertEquals("some text", settingsService.state.settingsDbtProjectDir)
        TestCase.assertEquals("/foo/bar", settingsService.state.settingsDotEnvFilePath)
    }

    fun `test reset`() {
        val settingsService = project.service<DbtToolkitSettingsService>()

        settingsService.state.settingsDbtProjectDir = "initial project dir"
        settingsService.state.settingsDotEnvFilePath = "initial dotenv path"
        configurable.reset()

        val dbtProjectsDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(1) as TextFieldWithBrowseButton
        dbtProjectsDirField.text = "modified project dir"
        val dbtDotEnvDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(5) as TextFieldWithBrowseButton
        dbtDotEnvDirField.text = "modified dotenv path"

        configurable.reset()
        TestCase.assertEquals("initial project dir", dbtProjectsDirField.text)
        TestCase.assertEquals("initial dotenv path", dbtDotEnvDirField.text)
    }
}