package panda.ast;

import panda.types.PandaTypeSystem;
import panda.types.PandaClassType;
import panda.types.ModeTypeVariable;
import panda.types.PandaParsedClassType;
import panda.types.ModeType;
import panda.types.ModeSubstType;
import panda.types.PandaContext; 

import polyglot.ast.Call;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.TypeChecker;

public class PandaCallExt extends PandaExt {

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    if (tc.context().inStaticContext()) {
      // Kick up to super lang for now
      return superLang().typeCheck(this.node(), tc);
    }

    Call c = (Call) this.node();

    Type t = c.target().type();
    if (!(t instanceof ModeSubstType)) {
      System.out.println("WARNING: call on target " + c.target() + " on type " + t);
      return superLang().typeCheck(this.node(), tc);
    }

    PandaContext ctx = (PandaContext) tc.context();
    PandaClassType ct = (PandaClassType) ctx.currentClassScope();

    ModeSubstType mt = (ModeSubstType) t;

    // Call is valid if the first mode type variable upper bound is greater
    // than the recievers mode type.
    ModeTypeVariable mtThis = ct.modeTypeVars().get(0);

    // Disallow dynamic type seperately for better diagnostics
    PandaTypeSystem ts = (PandaTypeSystem) tc.typeSystem();
    if (mt.modeType() == ts.DynamicModeType()) {
      throw new SemanticException("Dynamic mode type cannot receive messages. Resolve using snapshot.");
    }

    if (!ts.isSubtype(mt.modeType(), mtThis)) {
      throw new SemanticException("Cannot send message to " + t + " from mode " + mtThis.upperBound() + ".");
    }

    return superLang().typeCheck(this.node(), tc);
  }

}
