package com.github.ramonvermeulen.dbtToolkit.jinja

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class JinjaReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        println("Registered Jinja reference provider")
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement()
               .withText("ref"),
            JinjaReferenceProvider()
        )
    }
}