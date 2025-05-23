package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.jetbrains.annotations.NotNull

class DbtJinjaParserDefinition : ParserDefinition {

    companion object {
        val FILE: IFileElementType = IFileElementType(DbtJinjaLanguage.INSTANCE)
    }

    @NotNull
    override fun createLexer(project: Project): Lexer {
        return DbtJinjaLexerAdapter()
    }

    @NotNull
    override fun getCommentTokens(): TokenSet {
        return DbtJinjaTokenSets.COMMENTS
    }

    @NotNull
    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.create(DbtJinjaTypes.STRING_LITERAL_TOKEN, DbtJinjaTypes.SINGLE_QUOTED_STRING)
    }

    @NotNull
    override fun createParser(project: Project): PsiParser {
        return DbtJinjaParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return DbtJinjaFile(viewProvider)
    }

    override fun createElement(node: ASTNode): PsiElement {
        return DbtJinjaTypes.Factory.createElement(node)
    }
}