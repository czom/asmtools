/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.asmtools.jasm;

import static org.openjdk.asmtools.jasm.RuntimeConstants.*;
import static org.openjdk.asmtools.jasm.JasmTokens.*;
import static org.openjdk.asmtools.jasm.Tables.CF_Context;

/**
 *
 *
 */
public class Modifiers {

    private static Modifiers ref;

    /*
     * Modifier masks
     */
    public static final int MM_ATTR        = SYNTHETIC_ATTRIBUTE | DEPRECATED_ATTRIBUTE;

    public static final int MM_INTRF       = ACC_PUBLIC | ACC_ABSTRACT | ACC_INTERFACE | MM_ATTR ; // | ACC_MODULE  ;
    public static final int MM_CLASS       = ACC_PUBLIC | ACC_FINAL|  ACC_SUPER | ACC_ABSTRACT | ACC_SYNTHETIC  | ACC_ANNOTATION | ACC_ENUM | MM_ATTR ; // |  ACC_MODULE ;
    public static final int MM_ACCESS      = ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED; // | ACC_MODULE;
    public static final int MM_FIELD       = MM_ACCESS | ACC_STATIC | ACC_FINAL |  ACC_VOLATILE | ACC_TRANSIENT |  ACC_SYNTHETIC | ACC_ENUM | MM_ATTR ; // |  ACC_MODULE ;
    public static final int MM_I_METHOD    = ACC_ABSTRACT | ACC_PUBLIC | ACC_VARARGS | ACC_BRIDGE | ACC_SYNTHETIC ; // interface method
    public static final int MM_A_METHOD    = MM_ACCESS | ACC_ABSTRACT | MM_ATTR;
    public static final int MM_N_METHOD    = MM_ACCESS | ACC_STRICT | ACC_VARARGS | ACC_SYNTHETIC | MM_ATTR;  // <init>
    public static final int MM_METHOD      = MM_ACCESS | ACC_STATIC | ACC_FINAL | ACC_SYNCHRONIZED |  ACC_BRIDGE | ACC_VARARGS | ACC_NATIVE | ACC_ABSTRACT |  ACC_STRICT | ACC_SYNTHETIC | MM_ATTR ; // |  ACC_MODULE ;
    public static final int MM_INNERCLASS  = MM_ACCESS | ACC_STATIC | ACC_FINAL | ACC_SUPER | ACC_INTERFACE | ACC_ABSTRACT | ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM | MM_ATTR ; // |  ACC_MODULE ;

    private Modifiers() {
    }

    public static Modifiers ModifiersObject() {
        if (ref == null) {
            ref = new Modifiers();
        }
        return ref;
    }

    public static boolean validClass(int mod) {
        return (mod & ~MM_CLASS) == 0;
    }

    public static boolean validInnerClass(int mod) {
        return (mod & ~MM_INNERCLASS) == 0;
    }

    public static boolean validField(int mod) {
        return (mod & ~MM_FIELD) == 0;
    }

    public static boolean validMethod(int mod) {
        return (mod & ~MM_METHOD) == 0;
    }

    public static boolean validInterface(int mod) {
        return (mod & ~MM_INTRF) == 0;
    }

    public static boolean validAbstractMethod(int mod) {
        return (mod & ~MM_A_METHOD) == 0;
    }

    public static boolean validInitMethod(int mod) {
        return (mod & ~MM_N_METHOD) == 0;
    }

    public static boolean validInterfaceMethod(int mod) {
//        return (mod & ~MM_ATTR) == MM_I_METHOD;
        return ((mod & ~MM_I_METHOD) == 0) && isPublic(mod) && isAbstract(mod);
    }

    public static boolean validInterfaceField(int mod) {
        return mod == (ACC_STATIC | ACC_PUBLIC | ACC_FINAL);
    }

    public static boolean isPublic(int mod) {
        return (mod & ACC_PUBLIC) != 0;
    }

    public static boolean isPrivate(int mod) {
        return (mod & ACC_PRIVATE) != 0;
    }

    public static boolean isProtected(int mod) {
        return (mod & ACC_PROTECTED) != 0;
    }

    public static boolean isInterface(int mod) {
        return (mod & ACC_INTERFACE) != 0;
    }

    public static boolean isAbstract(int mod) {
        return (mod & ACC_ABSTRACT) != 0;
    }

    public static boolean isFinal(int mod) {
        return (mod & ACC_FINAL) != 0;
    }

    public static boolean isStatic(int mod) {
        return (mod & ACC_STATIC) != 0;
    }

    public static boolean isSynthetic(int mod) {
        return (mod & ACC_SYNTHETIC) != 0;
    }

    public static boolean isDeprecated(int mod) {
        return (mod & DEPRECATED_ATTRIBUTE) != 0;
    }

    public static boolean isTransient(int mod) {
        return (mod & ACC_TRANSIENT) != 0;
    }

    public static boolean isAnnotation(int mod) {
        return (mod & ACC_ANNOTATION) != 0;
    }

    public static boolean isNative(int mod) {
        return (mod & ACC_NATIVE) != 0;
    }

    public static boolean isStrict(int mod) {
        return (mod & ACC_STRICT) != 0;
    }

    public static boolean isEnum(int mod) {
        return (mod & ACC_ENUM) != 0;
    }

    public static boolean isSuper(int mod) {
        return (mod & ACC_SUPER) != 0;
    }
    /*
     public static boolean isModule(int mod) {
     return (mod & ACC_MODULE)!=0;
     }
     * */

    public static boolean isMandated(int mod) {
        return (mod & ACC_MANDATED) != 0;
    }

    public static boolean isSynchronized(int mod) {
        return (mod & ACC_SYNCHRONIZED) != 0;
    }

    public static boolean isBridge(int mod) {
        return (mod & ACC_BRIDGE) != 0;
    }

    public static boolean isVolatile(int mod) {
        return (mod & ACC_VOLATILE) != 0;
    }

    public static boolean isVarArgs(int mod) {
        return (mod & ACC_VARARGS) != 0;
    }

    public static boolean isSyntheticPseudoMod(int mod) {
        return (mod & SYNTHETIC_ATTRIBUTE) != 0;
    }

    public static boolean isDeprecatedPseudoMod(int mod) {
        return (mod & DEPRECATED_ATTRIBUTE) != 0;
    }

    public static boolean hasPseudoMod(int mod) {
        return isSyntheticPseudoMod(mod) || isDeprecatedPseudoMod(mod);
    }

    /*
     * Checks that only one (or none) of the Access flags are set.
     */
    public static boolean validAccess(int mod) {
        boolean retval = true;
        switch (mod & MM_ACCESS) {
            case 0:
            //        case ACC_MODULE:
            case ACC_PUBLIC:
            case ACC_PRIVATE:
            case ACC_PROTECTED:
                break;
            default:
                retval = false;
        }

        return retval;

    }

    /*
     * Are both flags set
     *
     */
    public static boolean both(int mod, int flagA, int flagB) {
        return (mod & (flagA | flagB)) == (flagA | flagB);
    }

    /**
     * Check the modifier flags for the class
     *
     * @param env The error reporting environment.
     * @param mod The modifier flags being checked
     * @param pos the position of the parser in the file
     */
    public static void checkClassModifiers(Environment env, int mod, int pos) {
        if (isInterface(mod)) {
            if (!validInterface(mod)) {
                env.error(pos, "warn.invalid.modifier.int");
            }
            if (!isAbstract(mod)) {
                env.error(pos, "warn.invalid.modifier.int.abs");
            }
        } else {
            if (!validClass(mod)) {
                env.error(pos, "warn.invalid.modifier.class");
            }
            if (isAbstract(mod) && Modifiers.isFinal(mod)) {
                env.error(pos, "warn.invalid.modifier.class.finabs");
            }
        }
    }

    /**
     * Check the modifier flags for the field
     *
     * @param cd The ClassData for the current class
     * @param mod The modifier flags being checked
     * @param pos the position of the parser in the file
     */
    public static void checkFieldModifiers(ClassData cd, int mod, int pos) {
        Environment env = cd.env;
        if (cd.isInterface()) {
            // For interfaces
            if (!validInterfaceField(mod)) {
                env.error(pos, "warn.invalid.modifier.intfield");
            }
        } else {
            // For non-interfaces
            if (!validField(mod)) {
                env.error(pos, "warn.invalid.modifier.field");
            }
            if (both(mod, ACC_FINAL, ACC_VOLATILE)) {
                env.error(pos, "warn.invalid.modifier.fiva");
            }
            if (!validAccess(mod)) {
                env.error(pos, "warn.invalid.modifier.acc");
            }
        }

    }

    /**
     * Check the modifier flags for the method
     *
     * @param cd The ClassData for the current class
     * @param mod The modifier flags being checked
     * @param pos the position of the parser in the file
     */
    public static void checkMethodModifiers(ClassData cd, int mod, int pos, boolean is_init, boolean is_clinit) {
        Environment env = cd.env;
        if (!is_clinit) {
            if (cd.isInterface()) {
                if (is_init) {
                    env.error(pos, "warn.init.in_int");
                } else if (!validInterfaceMethod(mod)) {
                    int badflags = (mod & ~MM_I_METHOD);
                    env.error(pos, "warn.invalid.modifier.intmth", toString(badflags, CF_Context.CTX_METHOD)
                            + "   *****" + toString(mod, CF_Context.CTX_METHOD) + "*****");
                }
            } else {
                if (is_init && !validInitMethod(mod)) {
                    int badflags = (mod & ~MM_N_METHOD);
                    env.error(pos, "warn.invalid.modifier.init", toString(badflags, CF_Context.CTX_METHOD)
                            + "   *****" + toString(mod, CF_Context.CTX_METHOD) + "*****");
                } else if (isAbstract(mod)) {
                    if (!validAbstractMethod(mod)) {
                        int badflags = (mod & ~MM_A_METHOD);
                        env.error(pos, "warn.invalid.modifier.abst", toString(badflags, CF_Context.CTX_METHOD)
                                + "   *****" + toString(mod, CF_Context.CTX_METHOD) + "*****");
                    }
                } else {
                    if (!validMethod(mod)) {
                        env.error(pos, "warn.invalid.modifier.mth");
                    }
                }
                if (!validAccess(mod)) {
                    env.error(pos, "warn.invalid.modifier.acc");
                }
            }
        }

    }

    /**
     * Check the modifier flags for the inner-class
     *
     * @param cd The ClassData for the current class
     * @param mod The modifier flags being checked
     * @param pos the position of the parser in the file
     */
    public static void checkInnerClassModifiers(ClassData cd, int mod, int pos) {
        Environment env = cd.env;

        if (!validInnerClass(mod)) {
            int badflags = (mod & ~MM_INNERCLASS);
            env.error(pos, "warn.invalid.modifier.innerclass",
                    toString(badflags, CF_Context.CTX_INNERCLASS)
                    + "   *****" + toString(mod, CF_Context.CTX_INNERCLASS) + "*****");
        }

    }

    private static StringBuffer _accessString(int mod, CF_Context context) {
        StringBuffer sb = new StringBuffer();
 //       if (isModule(mod))
        //           sb.append(Tables.keywordName(Tables.Module) + " ");
        if (isPublic(mod)) {
            sb.append(Token.PUBLIC.parsekey() + " ");
        }
        if (isPrivate(mod)) {
            sb.append(Token.PRIVATE.parsekey() + " ");
        }
        if (isProtected(mod)) {
            sb.append(Token.PROTECTED.parsekey() + " ");
        }
        if (isStatic(mod)) {
            sb.append(Token.STATIC.parsekey() + " ");
        }
        if (context == CF_Context.CTX_METHOD && isFinal(mod)) {
            sb.append(Token.FINAL.parsekey() + " ");
        }
        if (context == CF_Context.CTX_FIELD && isTransient(mod)) {
            sb.append(Token.TRANSIENT.parsekey() + " ");
        }
        if (context == CF_Context.CTX_CLASS && isSuper(mod)) {
            sb.append(Token.SUPER.parsekey() + " ");
        }
        if (context == CF_Context.CTX_METHOD && isSynchronized(mod)) {
            sb.append(Token.SYNCHRONIZED.parsekey() + " ");
        }
        if (context == CF_Context.CTX_METHOD) {
            if (isBridge(mod)) {
                sb.append(Token.BRIDGE.parsekey() + " ");
            }
            if (isVarArgs(mod)) {
                sb.append(Token.VARARGS.parsekey() + " ");
            }
            if (isNative(mod)) {
                sb.append(Token.NATIVE.parsekey() + " ");
            }
        }
        if (isAbstract(mod)) {
            if ((context != CF_Context.CTX_CLASS) || !isInterface(mod)) {
                sb.append(Token.ABSTRACT.parsekey() + " ");
            }
        }
        if ((context == CF_Context.CTX_CLASS || context == CF_Context.CTX_INNERCLASS || context == CF_Context.CTX_FIELD) && isFinal(mod)) {
            sb.append(Token.FINAL.parsekey() + " ");
        }
        if ((context == CF_Context.CTX_CLASS || context == CF_Context.CTX_INNERCLASS) && isInterface(mod)) {
            sb.append(Token.INTERFACE.parsekey() + " ");
        }
        if (isStrict(mod)) {
            sb.append(Token.STRICT.parsekey() + " ");
        }
        if (isSynthetic(mod)) {
            sb.append(Token.SYNTHETIC.parsekey() + " ");
        }
        if (context == CF_Context.CTX_FIELD && isVolatile(mod)) {
            sb.append(Token.VOLATILE.parsekey() + " ");
        }
        if (isEnum(mod)) {
            sb.append(Token.ENUM.parsekey() + " ");
        }
        if (isMandated(mod)) {
            sb.append(Token.MANDATED.parsekey() + " ");
        }
//      We don't have print identifiers for annotation flags
//      if (isAnnotation(mod))
//          sb.append(Tables.keywordName(Tables.ANNOTATION) + " ");

        return sb;
    }

    public static String toString(int mod, CF_Context context) {
        StringBuffer sb = _accessString(mod, context);

        if (isSyntheticPseudoMod(mod)) {
            sb.append("Synthetic(Pseudo) ");
        }
        if (isDeprecatedPseudoMod(mod)) {
            sb.append("Deprecated(Pseudo) ");
        }

        return sb.toString();
    }

    public static String accessString(int mod, CF_Context context) {
        StringBuffer sb = _accessString(mod, context);
        return sb.toString();
    }

}
