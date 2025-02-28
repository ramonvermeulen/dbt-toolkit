package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.mock.MockVirtualFile
import com.intellij.openapi.components.service
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.testFramework.LightPlatformTestCase
import junit.framework.TestCase
import org.mockito.Mockito
import java.io.ByteArrayInputStream

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

    private fun createMockVirtualFile(targetPath: String): MockVirtualFile {
        val mockFile = Mockito.mock(MockVirtualFile::class.java)
        val mockParent = Mockito.mock(VirtualFile::class.java)
        val yamlContent = """
        name: test_project
        model-paths: [models]
        test-paths: [tests]
        seed-paths: [seeds]
        macro-paths: [macros]
        """.trimIndent()
        val inputStream = ByteArrayInputStream(yamlContent.toByteArray())

        Mockito.`when`(mockFile.inputStream).thenReturn(inputStream)
        Mockito.`when`(mockFile.parent).thenReturn(mockParent)
        Mockito.`when`(mockParent.path).thenReturn(targetPath)
        Mockito.`when`(mockFile.name).thenReturn("dbt_project.yml")

        return mockFile
    }

    fun `test initial settings do not reset on subsequent parses`() {
        val mockFile1 = createMockVirtualFile("mock/path")

        settingsService.parseDbtProjectFile(mockFile1)
        val initialState = settingsService.state

        TestCase.assertTrue(initialState.initialSettingsAreSet)
        TestCase.assertEquals("test_project", initialState.dbtProjectName)
        TestCase.assertEquals("models", initialState.dbtModelPaths.firstOrNull())
        TestCase.assertEquals("mock/path", initialState.settingsDbtProjectDir)
        TestCase.assertEquals("mock/path/target", initialState.settingsDbtTargetDir)

        // modify state to ensure it doesn't get reset
        initialState.settingsDbtProjectDir = "modified_dir"
        initialState.settingsDbtTargetDir = "modified_path"
        settingsService.loadState(initialState)

        // second call to parseDbtProjectFile with a new mock instance
        val mockFile2 = createMockVirtualFile("mock/path2")
        settingsService.parseDbtProjectFile(mockFile2)
        val secondState = settingsService.state

        // verify initial settings are not reset
        TestCase.assertEquals("modified_dir", secondState.settingsDbtProjectDir)
        TestCase.assertEquals("modified_path", secondState.settingsDbtTargetDir)
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
