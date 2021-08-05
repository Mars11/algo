package cn.cqsztech.javac;

/**
 * Each frame (§2.6) contains an array of variables known as its local variables. The
 * length of the local variable array of a frame is determined at compile-time and
 * supplied in the binary representation of a class or interface along with the code for
 * the method associated with the frame (§4.7.3).
 * A single local variable can hold a value of type boolean, byte, char, short, int,
 * float, reference, or returnAddress. A pair of local variables can hold a value
 * of type long or double.
 * Local variables are addressed by indexing. The index of the first local variable is
 * zero. An integer is considered to be an index into the local variable array if and only
 * if that integer is between zero and one less than the size of the local variable array.
 * A value of type long or type double occupies two consecutive local variables.
 * Such a value may only be addressed using the lesser index. For example, a value of
 * type double stored in the local variable array at index n actually occupies the local
 * variables with indices n and n+1; however, the local variable at index n+1 cannot
 * be loaded from. It can be stored into. However, doing so invalidates the contents
 * of local variable n.
 * The Java Virtual Machine does not require n to be even. In intuitive terms, values
 * of types long and double need not be 64-bit aligned in the local variables array.
 * Implementors are free to decide the appropriate way to represent such values using
 * the two local variables reserved for the value.
 * The Java Virtual Machine uses local variables to pass parameters on method
 * invocation. On class method invocation, any parameters are passed in consecutive
 * local variables starting from local variable 0. On instance method invocation,
 * local variable 0 is always used to pass a reference to the object on which the
 * instance method is being invoked (this in the Java programming language). Any
 * parameters are subsequently passed in consecutive local variables starting from
 * local variable 1.
 *
 * Each frame (§2.6) contains a last-in-first-out (LIFO) stack known as its operand
 * stack. The maximum depth of the operand stack of a frame is determined at
 * compile-time and is supplied along with the code for the method associated with
 * the frame (§4.7.3).
 * Where it is clear by context, we will sometimes refer to the operand stack of the
 * current frame as simply the operand stack.
 * The operand stack is empty when the frame that contains it is created. The
 * Java Virtual Machine supplies instructions to load constants or values from local
 * variables or fields onto the operand stack. Other Java Virtual Machine instructions
 * take operands from the operand stack, operate on them, and push the result back
 * onto the operand stack. The operand stack is also used to prepare parameters to be
 * passed to methods and to receive method results.
 * For example, the iadd instruction (§iadd) adds two int values together. It requires
 * that the int values to be added be the top two values of the operand stack, pushed
 * there by previous instructions. Both of the int values are popped from the operand
 * stack. They are added, and their sum is pushed back onto the operand stack.
 * Subcomputations may be nested on the operand stack, resulting in values that can
 * be used by the encompassing computation.
 *
 *
 * Each entry on the operand stack can hold a value of any Java Virtual Machine type,
 * including a value of type long or type double.
 * Values from the operand stack must be operated upon in ways appropriate to their
 * types. It is not possible, for example, to push two int values and subsequently treat
 * them as a long or to push two float values and subsequently add them with an
 * iadd instruction. A small number of Java Virtual Machine instructions (the dup
 * instructions (§dup) and swap (§swap)) operate on run-time data areas as raw values
 * without regard to their specific types; these instructions are defined in such a way
 * that they cannot be used to modify or break up individual values. These restrictions
 * on operand stack manipulation are enforced through class file verification (§4.10).
 * At any point in time, an operand stack has an associated depth, where a value of
 * type long or double contributes two units to the depth and a value of any other
 * Each frame (§2.6) contains a reference to the run-time constant pool (§2.5.5) for
 * the type of the current method to support dynamic linking of the method code.
 * The class file code for a method refers to methods to be invoked and variables
 * to be accessed via symbolic references. Dynamic linking translates these symbolic
 * method references into concrete method references, loading classes as necessary to
 * resolve as-yet-undefined symbols, and translates variable accesses into appropriate
 * offsets in storage structures associated with the run-time location of these variables.
 * This late binding of the methods and variables makes changes in other classes that
 * a method uses less likely to break this code.
 *
 * Abrupt Method Invocation Completion
 *
 *then it is stored in big-endian order - high-order byte
 * first
 * The Java Virtual Machine gives special treatment to signature polymorphic
 * methods in the invokevirtual instruction (§invokevirtual), in order to effect
 * invocation of a method handle. A method handle is a strongly typed, directly
 * executable reference to an underlying method, constructor, field, or similar
 * low-level operation (§5.4.3.5), with optional transformations of arguments or
 * return values. These transformations are quite general, and include such patterns
 * as conversion, insertion, deletion, and substitution. See the java.lang.invoke
 * package in the Java SE platform API for more information.
 */
public class SimpleCompiler {
     static int i =0;
    public static void main(String[] args) {
        add();
        dec();
        System.out.println(i);
    }
    static void add(){
        i++;
    }
    static void dec(){
        i--;
    }
}
