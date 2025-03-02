package com.github.ramonvermeulen.dbtToolkit.jinja

import com.intellij.openapi.project.DumbAware
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class JinjaReferenceContributor : PsiReferenceContributor(), DumbAware {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        println("Registered Jinja reference provider")
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(), // Match any PsiElement
            JinjaReferenceProvider(),
            PsiReferenceRegistrar.HIGHER_PRIORITY // Use higher priority
        )
    }
}