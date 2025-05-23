// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.github.ramonvermeulen.dbtToolkit.jinja.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.github.ramonvermeulen.dbtToolkit.jinja.language.DbtJinjaTypes;
import com.intellij.psi.TokenType;

%%

%class DbtJinjaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

STRING = \"[^\"]*\"
SINGLE_QUOTED_STRING = \'[^\']*\'
WHITE_SPACE=[\ \n\t\f]
LINE_COMMENT = "--"[^\r\n]*

%%

"{{ ref("                                  { yybegin(YYINITIAL); return DbtJinjaTypes.REF_START; }
"{{ref("                                  { yybegin(YYINITIAL); return DbtJinjaTypes.REF_START; }
{STRING}                                    { yybegin(YYINITIAL); return DbtJinjaTypes.STRING_LITERAL_TOKEN; }
{SINGLE_QUOTED_STRING}                      { yybegin(YYINITIAL); return DbtJinjaTypes.SINGLE_QUOTED_STRING; }
") }}"                                  { yybegin(YYINITIAL); return DbtJinjaTypes.REF_END; }
")}}"                                  { yybegin(YYINITIAL); return DbtJinjaTypes.REF_END; }
{LINE_COMMENT}                              { yybegin(YYINITIAL); return DbtJinjaTypes.LINE_COMMENT; }

{WHITE_SPACE}+                              { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                                         { return TokenType.BAD_CHARACTER; }