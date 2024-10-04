package com.github.ramonvermeulen.dbtToolkit.ui.settings

import com.github.ramonvermeulen.dbtToolkit.services.DbtToolkitSettingsService
import com.intellij.openapi.components.service
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase
import javax.swing.JComponent
import javax.swing.JPanel

// TODO(ramon) for UI tests, take a lookt at https://github.com/JetBrains/intellij-ui-test-robot
class DbtToolkitSettingsConfigurableTest : LightPlatformTestCase() {
    private lateinit var mainSwingComponent: JComponent
    private lateinit var configurable: DbtToolkitSettingsConfigurable
    private lateinit var settingsService: DbtToolkitSettingsService

    public override fun setUp() {
        super.setUp()
        configurable = DbtToolkitSettingsConfigurable(project)
        // see component lifecycle: https://plugins.jetbrains.com/docs/intellij/settings-guide.html#intellij-platform-interactions-with-configurable
        mainSwingComponent = configurable.createComponent()
        configurable.reset()
        settingsService = project.service()
        settingsService.loadState(DbtToolkitSettingsService.State())
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
        val dbtProjectsDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(1) as TextFieldWithBrowseButton
        dbtProjectsDirField.text = "some text"
        val dbtDotEnvDirField = (mainSwingComponent.getComponent(0) as JPanel).getComponent(5) as TextFieldWithBrowseButton
        dbtDotEnvDirField.text = "/foo/bar"
        configurable.apply()
        TestCase.assertEquals("some text", settingsService.state.settingsDbtProjectDir)
        TestCase.assertEquals("/foo/bar", settingsService.state.settingsDotEnvFilePath)
    }
}
