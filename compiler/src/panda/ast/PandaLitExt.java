package panda.ast;

import panda.types.*;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;

import java.util.ArrayList;
import java.util.List;

public class PandaLitExt extends PandaExt {

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    // TODO : This is one big place holder for infering mode types
    PandaTypeSystem ts = (PandaTypeSystem) tc.typeSystem();

    Lit n = (Lit) superLang().typeCheck(this.node(), tc);
    if (n.type() instanceof ModeSubstType) {
      return n;
    }

    List<Type> mtArgs = new ArrayList<Type>();
    mtArgs.add(ts.WildcardModeType());
    Type st = ts.createModeSubst(n.type(), mtArgs);

    return n.type(st);
  }

}
