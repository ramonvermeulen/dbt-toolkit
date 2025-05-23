// This is a generated file. Not intended for manual editing.
package com.github.ramonvermeulen.dbtToolkit.jinja.language;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.ramonvermeulen.dbtToolkit.jinja.language.DbtJinjaTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class DbtJinjaParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return dbtJinjaFile(b, l + 1);
  }

  /* ********************************************************** */
  // LINE_COMMENT
  public static boolean comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment")) return false;
    if (!nextTokenIs(b, LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LINE_COMMENT);
    exit_section_(b, m, COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean dbtJinjaFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "dbtJinjaFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "dbtJinjaFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // refCall | stringLiteral | identifier | comment
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = refCall(b, l + 1);
    if (!r) r = stringLiteral(b, l + 1);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = comment(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // REF_START stringLiteral REF_END
  public static boolean refCall(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "refCall")) return false;
    if (!nextTokenIs(b, REF_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REF_START);
    r = r && stringLiteral(b, l + 1);
    r = r && consumeToken(b, REF_END);
    exit_section_(b, m, REF_CALL, r);
    return r;
  }

  /* ********************************************************** */
  // STRING_LITERAL_TOKEN | SINGLE_QUOTED_STRING
  public static boolean stringLiteral(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stringLiteral")) return false;
    if (!nextTokenIs(b, "<string literal>", SINGLE_QUOTED_STRING, STRING_LITERAL_TOKEN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRING_LITERAL, "<string literal>");
    r = consumeToken(b, STRING_LITERAL_TOKEN);
    if (!r) r = consumeToken(b, SINGLE_QUOTED_STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
