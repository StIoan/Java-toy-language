package Model.Expresion;
import Model.Value.Value;
import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;

public class ArithExp implements ExpresionI {
    ExpresionI exp1;
    ExpresionI exp2;
    char operation;

    public ArithExp(char operation, ExpresionI exp1, ExpresionI exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value eval(MyDictionaryI<String, Value> symTable, MyHeapI<Integer, Value> heap) throws Exceptions {
        Value value1, value2;
        value1 = this.exp1.eval(symTable, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = this.exp2.eval(symTable, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) value1;
                IntValue int2 = (IntValue) value2;
                int n1, n2;
                n1 = int1.getValue();
                n2 = int2.getValue();
                if (this.operation == '+')
                    return new IntValue(n1 + n2);
                else if (this.operation == '-')
                    return new IntValue(n1 - n2);
                else if (this.operation == '*')
                    return new IntValue(n1 * n2);
                else if (this.operation == '/')
                    if (n2 == 0)
                        throw new Exceptions("Division by zero.");
                    else
                        return new IntValue(n1 / n2);
            } else
                throw new Exceptions("Second operand is not an integer.");
        } else
            throw new Exceptions("First operand is not an integer.");
        return null;
    }

    @Override
    public Type typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = exp1.typecheck(typeEnvironment);
        typeSecondExpression = exp2.typecheck(typeEnvironment);

        if(typeFistExpression.equals(new IntType())){
            if(typeSecondExpression.equals(new IntType())){
                return new IntType();
            }else {
                throw new Exceptions("Type mismatch!");
            }
        }else {
            throw new Exceptions("Type mismatch!");
        }
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operation + " " + exp2.toString();
    }
}
