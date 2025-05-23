package com.github.ramonvermeulen.dbtToolkit.jinja

import com.github.ramonvermeulen.dbtToolkit.jinja.language.DbtJinjaLanguage
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.sql.psi.SqlElement

class JinjaReferenceMultiHostInjector : MultiHostInjector {
    override fun getLanguagesToInject(registrar: MultiHostRegistrar, psiElement: PsiElement) {
        if (psiElement is PsiLanguageInjectionHost) {
            // unfortunately SqlElement is not an injectable host it seems
            println("[RAMON][DEBUG]:injectable host found!: ${psiElement.text}")
        }
        if (psiElement is SqlElement && shouldInject(psiElement)) {
            val text = psiElement.text
            JinjaPatterns.REF_PATTERN.findAll(text).forEach { match ->
                println("[RAMON][DEBUG]: JinjaReferenceMultiHostInjector: found match: ${match.value}")
                val groups = match.groups
                val refGroup = groups["ref"] ?: return@forEach

                val prefix = text.substring(match.range.first, refGroup.range.first - 1)
                val suffix = text.substring(refGroup.range.last + 1, match.range.last + 1)

                val textRange = TextRange(
                    match.range.first,
                    match.range.last + 1
                )
                // safecast to PsiLanguageInjectionHost
                val host = psiElement as? PsiLanguageInjectionHost ?: return@forEach

                registrar
                    .startInjecting(DbtJinjaLanguage.INSTANCE)
                    .addPlace(prefix, suffix, host, textRange)
                    .doneInjecting()
            }
        }
    }

    override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
        return mutableListOf(SqlElement::class.java)
    }

    private fun shouldInject(statement: SqlElement): Boolean {
        return JinjaPatterns.REF_PATTERN.containsMatchIn(statement.text)
    }
}