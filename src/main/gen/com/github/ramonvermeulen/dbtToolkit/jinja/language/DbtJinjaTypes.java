// This is a generated file. Not intended for manual editing.
package com.github.ramonvermeulen.dbtToolkit.jinja.language;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.ramonvermeulen.dbtToolkit.jinja.language.impl.*;

public interface DbtJinjaTypes {

  IElementType COMMENT = new DbtJinjaElementType("COMMENT");
  IElementType REF_CALL = new DbtJinjaElementType("REF_CALL");
  IElementType STRING_LITERAL = new DbtJinjaElementType("STRING_LITERAL");

  IElementType IDENTIFIER = new DbtJinjaTokenType("identifier");
  IElementType LINE_COMMENT = new DbtJinjaTokenType("LINE_COMMENT");
  IElementType REF_END = new DbtJinjaTokenType("REF_END");
  IElementType REF_START = new DbtJinjaTokenType("REF_START");
  IElementType SINGLE_QUOTED_STRING = new DbtJinjaTokenType("SINGLE_QUOTED_STRING");
  IElementType STRING_LITERAL_TOKEN = new DbtJinjaTokenType("STRING_LITERAL_TOKEN");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMMENT) {
        return new DbtJinjaCommentImpl(node);
      }
      else if (type == REF_CALL) {
        return new DbtJinjaRefCallImpl(node);
      }
      else if (type == STRING_LITERAL) {
        return new DbtJinjaStringLiteralImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
