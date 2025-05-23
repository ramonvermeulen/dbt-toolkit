package com.github.ramonvermeulen.dbtToolkit.jinja.language

import com.intellij.lang.Language

class DbtJinjaLanguage private constructor() : Language("DbtJinja") {
    companion object {
        val INSTANCE = DbtJinjaLanguage()
    }
}