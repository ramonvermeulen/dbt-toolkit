package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.psi.tree.IElementType

class DbtJinjaTokenType(debugName: String) : IElementType(debugName, DbtJinjaLanguage.INSTANCE) {
    /* todo change this token, work with more specific tokens */
    override fun toString(): String {
        return "DbtJinjaTokenType.${super.toString()}"
    }
}