package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class DbtJinjaFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, DbtJinjaLanguage.INSTANCE) {
    override fun getFileType() = DbtJinjaFileType.INSTANCE

    override fun toString(): String {
        return "DbtJinjaFile:${name}"
    }
}
