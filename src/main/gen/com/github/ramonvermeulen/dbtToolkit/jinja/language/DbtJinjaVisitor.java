// This is a generated file. Not intended for manual editing.
package com.github.ramonvermeulen.dbtToolkit.jinja.language;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class DbtJinjaVisitor extends PsiElementVisitor {

  public void visitComment(@NotNull DbtJinjaComment o) {
    visitPsiElement(o);
  }

  public void visitRefCall(@NotNull DbtJinjaRefCall o) {
    visitPsiElement(o);
  }

  public void visitStringLiteral(@NotNull DbtJinjaStringLiteral o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
