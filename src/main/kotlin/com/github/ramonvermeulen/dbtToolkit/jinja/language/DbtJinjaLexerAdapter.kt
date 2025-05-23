package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.lexer.FlexAdapter

class DbtJinjaLexerAdapter : FlexAdapter(DbtJinjaLexer(null))