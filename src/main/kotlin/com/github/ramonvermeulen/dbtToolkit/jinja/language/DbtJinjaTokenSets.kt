package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.psi.tree.TokenSet

interface DbtJinjaTokenSets {
    companion object {
        val REF: TokenSet = TokenSet.create(
            DbtJinjaTypes.REF_START,
            DbtJinjaTypes.REF_CALL,
            DbtJinjaTypes.REF_END,
        )
        val COMMENTS: TokenSet = TokenSet.create()
    }
}
