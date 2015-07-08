package panda.types;

import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.*;

import java.util.List;

public abstract class ModeSubstType_c extends Type_c implements ModeSubstType {

  protected Type baseType;
  protected List<Type> modeTypeArgs;

  public ModeSubstType_c(Type baseType, List<Type> modeTypeArgs) {
    super(baseType.typeSystem(), Position.COMPILER_GENERATED);
    this.baseType = baseType;
    this.modeTypeArgs = modeTypeArgs;
  }

  // ModeSubstType Methods
  public Type baseType() {
    return (Type) this.baseType;
  }

  public void baseType(Type baseType) {
    this.baseType = baseType;
  }

  public List<Type> modeTypeArgs() {
    return this.modeTypeArgs;
  }

  public void modeTypeArgs(List<Type> modeTypeArgs) {
    this.modeTypeArgs = modeTypeArgs;
  } 

  public Type modeType() {
    return this.modeTypeArgs().get(0);
  }

  public void modeType(Type modeType) {
    this.modeTypeArgs().set(0, modeType);
  } 

  public abstract ModeSubstType deepCopy();
  
  // Type Methods
  @Override
  public String toString() {
    return this.baseType() + "@mode<" + this.modeType() + ">";
  }

  @Override
  public String translate(Resolver c) {
    return this.baseType.translate(c);
  }

  @Override
  public boolean isType() {
    return this.baseType.isType();
  }

  @Override
  public boolean isPackage() {
    return this.baseType.isPackage();
  }

  @Override
  public Type toType() {
    return this.baseType.toType();
  }

  @Override
  public Package toPackage() {
    return this.baseType.toPackage();
  }

  @Override
  public boolean isCanonical() {
    return this.baseType.isCanonical();
  }

  @Override
  public boolean isNumeric() {
    return this.baseType.isNumeric();
  }

  @Override
  public boolean isIntOrLess() {
    return this.baseType.isIntOrLess();
  }

  @Override
  public boolean isLongOrLess() {
    return this.baseType.isLongOrLess();
  }

  @Override
  public boolean isVoid() {
    return this.baseType.isVoid();
  }

  @Override
  public boolean isBoolean() {
    return this.baseType.isBoolean();
  }

  @Override
  public boolean isChar() {
    return this.baseType.isChar();
  }

  @Override
  public boolean isByte() {
    return this.baseType.isByte();
  }

  @Override
  public boolean isShort() {
    return this.baseType.isShort();
  }

  @Override
  public boolean isInt() {
    return this.baseType.isInt();
  }

  @Override
  public boolean isLong() {
    return this.baseType.isLong();
  }

  @Override
  public boolean isFloat() {
    return this.baseType.isFloat();
  }

  @Override
  public boolean isDouble() {
    return this.baseType.isDouble();
  }

  @Override
  public boolean isReference() {
    return this.baseType.isReference();
  }

  @Override
  public boolean isNull() {
    return this.baseType.isNull();
  }

  @Override
  public boolean isClass() {
    return this.baseType.isClass();
  }

  @Override
  public boolean isArray() {
    return this.baseType.isArray();
  }

  @Override
  public boolean isThrowable() {
    return this.baseType.isThrowable();
  }

  @Override
  public boolean isUncheckedException() {
    return this.baseType.isUncheckedException();
  }

  @Override
  public ClassType toClass() {
    return null;
  }

  @Override
  public NullType toNull() {
    return this.baseType.toNull();
  }

  @Override
  public ReferenceType toReference() {
    return this.baseType.toReference();
  }

  @Override
  public PrimitiveType toPrimitive() {
    return null;
  }

  @Override
  public ArrayType toArray() {
    return this.baseType.toArray();
  }

  @Override
  public ArrayType arrayOf(int dims) {
    return this.baseType.arrayOf(dims);
  }

  @Override 
  public boolean equalsImpl(TypeObject o) {
    if (!(o instanceof ModeSubstType)) {
      return false;
    }
    ModeSubstType p = (ModeSubstType) o;
    return (this.baseType().equals(p.baseType()) && this.modeType().equals(p.modeType()));
  } 

  @Override
  public boolean typeEqualsImpl(Type t) {
    if (!(t instanceof ModeSubstType)) {
      throw new InternalCompilerError(
          "mode subst did not occur - comparing " + this + " -- " + t);
    } 

    ModeSubstType p = (ModeSubstType) t;
    return this.ts.typeEquals(this.baseType(), p.baseType()) &&
           this.ts.typeEquals(this.modeType(), p.modeType());
  }

  // NOTE : This are all methods that may need to be intercepted, checked
  // for mode type, and then dispatched.
  @Override
  public boolean isSubtypeImpl(Type ancestor) {
    return this.ts.typeEquals(this, ancestor) ||
           this.ts.descendsFrom(this, ancestor);
  }

  @Override
  public boolean descendsFromImpl(Type ancestor) {
    return false;
  }

  @Override
  public boolean isCastValidImpl(Type toType) {
    return false;
  }

  @Override
  public boolean isImplicitCastValidImpl(Type toType) {
    return false;
  }

  /*
  @Deprecated
  @Override
  public final boolean numericConversionValid(long value) {
    return ts.numericConversionValid(this, value);
  }

  @Deprecated
  @Override
  public boolean numericConversionValidImpl(long value) {
    return numericConversionValidImpl(new Long(value));
  }

  @Override
  public final boolean numericConversionValid(Object value) {
    return ts.numericConversionValid(this, value);
  }

  @Override
  public boolean numericConversionValidImpl(Object value) {
    return false;
  }

  @Override
  public boolean isComparable(Type t) {
    return t.typeSystem() == ts;
  }
  */

  @Override
  public String argsAsString() {
    String args = "@mode<";
    for (Type t : this.modeTypeArgs()) {
      args += t.toString() + ",";
    }
    args = args.substring(0, args.length()-1);
    args += ">";
    return args;
  }





}


