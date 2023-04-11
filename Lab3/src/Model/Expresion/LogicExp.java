package Model.Expresion;
import Model.Value.Value;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.BoolType;
import Model.Type.Type;
import Exceptions.Exceptions;
import Model.Value.BoolValue;
import java.util.Objects;

public class LogicExp implements ExpresionI {
    ExpresionI exp1;
    ExpresionI exp2;
    String operation;

    public LogicExp(String operation, ExpresionI exp1, ExpresionI exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public Value eval(MyDictionaryI<String, Value> symTable, MyHeapI<Integer, Value> heap) throws Exceptions {
        Value value1, value2;
        value1 = this.exp1.eval(symTable, heap);
        if (value1.getType().equals(new BoolType())) {
            value2 = this.exp2.eval(symTable, heap);
            if (value2.getType().equals(new BoolType())) {
                BoolValue bool1 = (BoolValue) value1;
                BoolValue bool2 = (BoolValue) value2;
                boolean b1, b2;
                b1 = bool1.getValue();
                b2 = bool2.getValue();
                if (Objects.equals(this.operation, "and")) {
                    return new BoolValue(b1 && b2);
                } else if (Objects.equals(this.operation, "or")) {
                    return new BoolValue(b1 || b2);
                }
            } else {
                throw new Exceptions("Second operand is not a boolean.");
            }
        } else {
            throw new Exceptions("First operand is not a boolean.");
        }
        return null;
    }

    @Override
    public Type typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = exp1.typecheck(typeEnvironment);
        typeSecondExpression = exp2.typecheck(typeEnvironment);

        if(typeFistExpression.equals(new BoolType())){
            if(typeSecondExpression.equals(new BoolType())){
                return new BoolType();
            }else{
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
