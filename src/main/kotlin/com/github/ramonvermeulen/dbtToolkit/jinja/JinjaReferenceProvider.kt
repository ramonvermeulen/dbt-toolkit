package com.github.ramonvermeulen.dbtToolkit.jinja

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext

class JinjaReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val fileContent = element.containingFile.text
        val matches = JinjaPatterns.REF_PATTERN.findAll(fileContent)

        return matches.map { match ->
            val refValue = match.groupValues[1]
            JinjaReference(element, TextRange(match.range.first, match.range.last + 1), refValue)
        }.toList().toTypedArray()
    }
}
