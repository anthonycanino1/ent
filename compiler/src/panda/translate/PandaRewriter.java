package panda.translate;

import panda.types.*;
import panda.PandaOptions;

import polyglot.ast.*;
import polyglot.frontend.*;
import polyglot.translate.*;
import polyglot.types.*;
import polyglot.util.*;

import polyglot.ext.jl5.ast.*;
import polyglot.ext.jl5.types.*;
import polyglot.ext.jl5.types.inference.*;
import polyglot.ext.jl7.types.*;

import java.util.ArrayList;
import java.util.List;

public class PandaRewriter extends ExtensionRewriter {
  protected boolean translatePanda;
  protected boolean rewriteModeValue;
  
  public PandaRewriter(Job job, 
                       ExtensionInfo from_ext, 
                       ExtensionInfo to_ext) {
    super(job, from_ext, to_ext);
    PandaOptions options = (PandaOptions) from_ext.getOptions();
    this.translatePanda = options.translatePanda;
  }

  public boolean translatePanda() {
    return this.translatePanda;
  }

  public boolean rewriteModeValue() {
    return this.rewriteModeValue;
  }

  public PandaRewriter rewriteModeValue(boolean rewrite) {
    PandaRewriter prw = Copy.Util.copy(this);
    prw.rewriteModeValue = rewrite;
    return prw;
  }

  public TypeNode j5TypeToJava(Type t, Position pos) throws SemanticException {
      JL5NodeFactory to_nf = (JL5NodeFactory) to_nf();
      // JL5ToJL5Rewriter.typeToJava
      if (t instanceof IntersectionType) {
          IntersectionType ct = (IntersectionType) t.toClass();
          List<TypeNode> bounds = new ArrayList<>(ct.bounds().size());
          for (ReferenceType rt : ct.bounds())
              bounds.add(typeToJava(rt, pos));
          return to_nf.ParamTypeNode(pos, to_nf.Id(pos, ct.name()), bounds);

      }
      else if (t instanceof LubType) {
          throw new InternalCompilerError("I don't understand what to do here");

      }
      else if (t instanceof WildCardType) {
          WildCardType wc = (WildCardType) t;
          if (wc.isSuperConstraint()) {
              TypeNode superNode = typeToJava(wc.lowerBound(), pos);
              return to_nf.AmbWildCardSuper(pos, superNode);
          }
          else if (wc.isExtendsConstraint()) {
              TypeNode extendsNode = typeToJava(wc.upperBound(), pos);
              return to_nf.AmbWildCardExtends(pos, extendsNode);
          }
          else {
              return to_nf.AmbWildCard(pos);
          }
      }
      else if (t instanceof TypeVariable) {
          TypeVariable tv = (TypeVariable) t;
          return to_nf.AmbTypeNode(pos, to_nf.Id(pos, tv.name()));
      }

      if (t.isClass()) {
          if (t instanceof JL5ParsedClassType) {
              JL5ParsedClassType ct = (JL5ParsedClassType) t.toClass();
              List<TypeNode> tvs = new ArrayList<>(ct.typeVariables().size());
              for (ReferenceType rt : ct.typeVariables())
                  tvs.add(typeToJava(rt, pos));
              return to_nf.TypeNodeFromQualifiedName(pos, ct.fullName(), tvs);
          }
          else if (t instanceof JL5SubstClassType) {
              JL5SubstClassType ct = (JL5SubstClassType) t.toClass();
              List<TypeNode> actuals = new ArrayList<>(ct.actuals().size());
              for (ReferenceType rt : ct.actuals())
                  actuals.add(typeToJava(rt, pos));

              return to_nf.TypeNodeFromQualifiedName(pos,
                                                     ct.fullName(),
                                                     actuals);
          }
          else if (t instanceof RawClass) {
              return to_nf.TypeNodeFromQualifiedName(pos, t.toClass()
                                                           .fullName());

          }
          else {
              throw new InternalCompilerError("Unknown class type: "
                      + t.getClass());
          }
      }

      return super.typeToJava(t, pos);
  }


  @Override
  public TypeNode typeToJava(Type t, Position pos) throws SemanticException {
    if (t instanceof DiamondType) {
      return this.typeToJava(((DiamondType) t).inferred(), pos);
    } 
    return this.j5TypeToJava(t, pos);
  }


}
