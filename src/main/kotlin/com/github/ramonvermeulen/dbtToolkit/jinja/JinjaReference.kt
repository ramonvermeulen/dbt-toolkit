package com.github.ramonvermeulen.dbtToolkit.jinja

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope

class JinjaReference(
    element: PsiElement,
    range: TextRange,
    private val refValue: String,
) : PsiReferenceBase<PsiElement>(element, range) {
    override fun resolve(): PsiElement? {
        return findSqlFile(refValue)
    }

    private fun findSqlFile(refValue: String): PsiElement? {
        val project = element.project
        val sqlFileName = "$refValue.sql"

        val files = FilenameIndex.getVirtualFilesByName(sqlFileName, GlobalSearchScope.allScope(project))
        val psiFile = PsiManager.getInstance(project).findFile(files.first())

        return psiFile
    }
}
