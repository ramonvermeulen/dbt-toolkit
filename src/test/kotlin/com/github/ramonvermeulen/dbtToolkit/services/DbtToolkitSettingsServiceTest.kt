package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.openapi.components.service
import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase

class DbtToolkitSettingsServiceTest : LightPlatformTestCase() {
    private lateinit var settingsService: DbtToolkitSettingsService

    public override fun setUp() {
        super.setUp()
        settingsService = project.service()
        settingsService.loadState(DbtToolkitSettingsService.State())
    }

    public override fun tearDown() {
        try {
            settingsService.loadState(DbtToolkitSettingsService.State())
        } finally {
            super.tearDown()
        }
    }

    fun `test default state`() {
        val state = settingsService.state
        TestCase.assertNotNull(state)
        TestCase.assertEquals(state.settingsDbtProjectDir, DbtToolkitSettingsService.State().settingsDbtTargetDir)
        TestCase.assertEquals(state.settingsDotEnvFilePath, DbtToolkitSettingsService.State().settingsDotEnvFilePath)
    }

    fun `test update state`() {
        val newState = DbtToolkitSettingsService.State().apply {
            settingsDbtProjectDir = "new project dir"
            settingsDotEnvFilePath = "new dotenv path"
        }
        settingsService.loadState(newState)

        val state = settingsService.state
        TestCase.assertEquals("new project dir", state.settingsDbtProjectDir)
        TestCase.assertEquals("new dotenv path", state.settingsDotEnvFilePath)
    }
}
