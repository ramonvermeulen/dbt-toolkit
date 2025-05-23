// This is a generated file. Not intended for manual editing.
package com.github.ramonvermeulen.dbtToolkit.jinja.language.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.ramonvermeulen.dbtToolkit.jinja.language.DbtJinjaTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.ramonvermeulen.dbtToolkit.jinja.language.*;

public class DbtJinjaRefCallImpl extends ASTWrapperPsiElement implements DbtJinjaRefCall {

  public DbtJinjaRefCallImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DbtJinjaVisitor visitor) {
    visitor.visitRefCall(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DbtJinjaVisitor) accept((DbtJinjaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DbtJinjaStringLiteral getStringLiteral() {
    return findNotNullChildByClass(DbtJinjaStringLiteral.class);
  }

}
