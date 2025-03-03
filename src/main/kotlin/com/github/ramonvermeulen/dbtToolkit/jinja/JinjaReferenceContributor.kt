package com.github.ramonvermeulen.dbtToolkit.jinja

import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.util.ProcessingContext

class JinjaReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement().with(object : PatternCondition<PsiElement>("jinjaRefPattern") {
                override fun accepts(element: PsiElement, context: ProcessingContext?): Boolean {
                    return JinjaPatterns.REF_PATTERN.containsMatchIn(element.text)
                }
            }),
            JinjaReferenceProvider(),
        )
    }
}
