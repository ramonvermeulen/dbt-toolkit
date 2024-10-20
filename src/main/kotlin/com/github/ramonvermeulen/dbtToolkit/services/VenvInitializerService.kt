package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ProjectRootManager
import com.jetbrains.python.sdk.PythonSdkUtil
import org.apache.commons.lang3.SystemUtils
import java.nio.file.Files
import java.nio.file.Path

@Service(Service.Level.PROJECT)
class VenvInitializerService(private var project: Project) {
    private val loggingService = project.service<LoggingService>()
    private var venvDbtExecutablePath: String? = null

    private fun getDbtPath(venvPath: Path): Path {
        return if (SystemUtils.IS_OS_WINDOWS) {
            venvPath.resolve("dbt.exe")
        } else {
            venvPath.resolve("dbt")
        }
    }

    fun initializeEnvironment() {
        val venvExecutablePath = getPythonVenvExecutablePath(project)
        if (venvExecutablePath == null) {
            loggingService.log(
                "Python virtual environment not detected. Attempting to use a global dbt installation.\n\n",
                ConsoleViewContentType.ERROR_OUTPUT,
            )
            return
        }

        val dbtPath = getDbtPath(venvExecutablePath.parent)

        if (!Files.exists(dbtPath)) {
            loggingService.log(
                "dbt installation not found within the virtual environment. Please install dbt and restart your IDE.\n\n",
                ConsoleViewContentType.ERROR_OUTPUT,
            )
            return
        }

        venvDbtExecutablePath = dbtPath.toString()
        loggingService.log(
            "Located dbt installation within the virtual environment at: $venvDbtExecutablePath\n\n",
            ConsoleViewContentType.NORMAL_OUTPUT,
        )
    }

    private fun getPythonVenvExecutablePath(project: Project): Path? {
        val projectSdk: Sdk? = ProjectRootManager.getInstance(project).projectSdk
        if (projectSdk != null && PythonSdkUtil.isVirtualEnv(projectSdk)) {
            return Path.of(projectSdk.homePath!!)
        }
        return null
    }

    fun getDbtPath(): String? {
        return venvDbtExecutablePath
    }
}
