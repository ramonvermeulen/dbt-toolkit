package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class DbtJinjaFileType private constructor() : LanguageFileType(DbtJinjaLanguage.INSTANCE) {
    companion object {
        val INSTANCE = DbtJinjaFileType()
    }
    override fun getName() = "DbtJinja File"
    override fun getDescription() = "DbtJinja language file"
    override fun getDefaultExtension() = "dbt-jinja"
    override fun getIcon(): Icon? = null // TODO(add icon https://plugins.jetbrains.com/docs/intellij/language-and-filetype.html#define-an-icon)
}