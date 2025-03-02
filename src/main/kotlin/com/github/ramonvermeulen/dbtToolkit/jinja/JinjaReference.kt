package com.github.ramonvermeulen.dbtToolkit.jinja

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.FilenameIndex

class JinjaReference(
    element: PsiElement,
    range: TextRange,
    private val refValue: String
) : PsiReferenceBase<PsiElement>(element, range) {
    override fun resolve(): PsiElement? {
        println("Resolving Jinja reference: $refValue")
        return findSqlFile(refValue)
    }

    private fun findSqlFile(refValue: String): PsiElement? {
        val project = element.project
        val sqlFileName = "$refValue.sql"

        // Search for the SQL file in the project
        val files = FilenameIndex.getFilesByName(project, sqlFileName, GlobalSearchScope.allScope(project))
        if (files.isNotEmpty()) {
            println("Found SQL file: ${files[0].name}")
            return files[0]
        }
        println("SQL file not found: $sqlFileName")
        return null
    }
}