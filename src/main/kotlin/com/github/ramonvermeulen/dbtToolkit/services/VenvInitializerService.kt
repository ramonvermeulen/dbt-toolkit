package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ProjectRootManager
import com.jetbrains.python.sdk.PythonSdkUtil
import java.io.File

@Service(Service.Level.PROJECT)
class VenvInitializerService(private var project: Project) {
    private val loggingService = project.service<LoggingService>()
    private var venvDbtPath: String? = null

    fun initializeEnvironment() {
        val interpreterPath = getPythonInterpreterPath(project)
        if (interpreterPath != null) {
            loggingService.log("Python venv found at: $interpreterPath\n", ConsoleViewContentType.NORMAL_OUTPUT)
            val interpreterFile = File(interpreterPath)
            if (File("${interpreterFile.parent}/dbt").exists()) {
                venvDbtPath = "${interpreterFile.parent}/dbt"
                loggingService.log("dbt installation found within venv at: $venvDbtPath\n", ConsoleViewContentType.NORMAL_OUTPUT)
            } else {
                loggingService.log("No dbt installation found within venv.. please install dbt and restart your IDE", ConsoleViewContentType.NORMAL_OUTPUT)
            }
        } else {
            loggingService.log("No Python venv found, trying to use a global dbt installation", ConsoleViewContentType.NORMAL_OUTPUT)
        }
        loggingService.log("\n\n", ConsoleViewContentType.NORMAL_OUTPUT)
    }

    private fun getPythonInterpreterPath(project: Project): String? {
        val projectSdk: Sdk? = ProjectRootManager.getInstance(project).projectSdk
        if (projectSdk != null && PythonSdkUtil.isPythonSdk(projectSdk)) {
            return projectSdk.homePath?.let { PythonSdkUtil.getPythonExecutable(it) }
        }
        return null
    }

    fun getDbtPath(): String? {
        return venvDbtPath
    }
}