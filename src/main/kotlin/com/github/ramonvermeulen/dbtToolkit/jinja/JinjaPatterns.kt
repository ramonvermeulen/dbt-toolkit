package com.github.ramonvermeulen.dbtToolkit.jinja

object JinjaPatterns {
    val REF_PATTERN = Regex("""\{\{.*?ref\s*\(\s*['"](.*?)['"]\s*\).*?\}\}""")
}
