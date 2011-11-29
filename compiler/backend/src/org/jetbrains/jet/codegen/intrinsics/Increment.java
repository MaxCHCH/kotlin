package org.jetbrains.jet.codegen.intrinsics;

import com.intellij.psi.PsiElement;
import org.jetbrains.jet.codegen.ExpressionCodegen;
import org.jetbrains.jet.codegen.JetTypeMapper;
import org.jetbrains.jet.codegen.StackValue;
import org.jetbrains.jet.lang.psi.JetExpression;
import org.jetbrains.jet.lang.psi.JetParenthesizedExpression;
import org.jetbrains.jet.lang.psi.JetReferenceExpression;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.InstructionAdapter;

import java.util.List;

/**
 * @author yole
 */
public class Increment implements IntrinsicMethod {
    private final int myDelta;

    public Increment(int delta) {
        myDelta = delta;
    }

    @Override
    public StackValue generate(ExpressionCodegen codegen, InstructionAdapter v, Type expectedType, PsiElement element, List<JetExpression> arguments, StackValue receiver) {
        JetExpression operand = arguments.get(0);
        while(operand instanceof JetParenthesizedExpression) {
            operand = ((JetParenthesizedExpression)operand).getExpression();
        }
        if (operand instanceof JetReferenceExpression) {
            final int index = codegen.indexOfLocal((JetReferenceExpression) operand);
            if (index >= 0 && JetTypeMapper.isIntPrimitive(expectedType)) {
                return StackValue.preIncrement(index, myDelta);
            }
        }
        StackValue value = codegen.genQualified(receiver, operand);
        value. dupReceiver(v);
        value. dupReceiver(v);
        value.put(expectedType, v);
        if (expectedType == Type.LONG_TYPE) {
            v.lconst(myDelta);
        }
        else if (expectedType == Type.FLOAT_TYPE) {
            v.fconst(myDelta);
        }
        else if (expectedType == Type.DOUBLE_TYPE) {
            v.dconst(myDelta);
        }
        else {
            v.iconst(myDelta);
        }
        v.add(expectedType);
        value.store(v);
        value.put(expectedType, v);
        return StackValue.onStack(expectedType);
    }
}
