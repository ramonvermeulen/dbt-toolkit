package com.github.ramonvermeulen.dbtToolkit.ui

import com.intellij.openapi.Disposable
import javax.swing.JComponent

interface IdeaPanel : Disposable {
    fun getContent(): JComponent

    override fun dispose() {
        TODO("Not yet implemented")
    }
}
