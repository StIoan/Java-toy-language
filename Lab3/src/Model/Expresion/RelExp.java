package Model.Expresion;

import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

public class RelExp implements ExpresionI {
    private ExpresionI firstExpression;
    private ExpresionI secondExpression;
    StringValue operation;

    public RelExp(ExpresionI firstExpression, ExpresionI secondExpression, StringValue operation) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public String toString(){
        return switch (operation.getValue()) {
            case "<" -> "( " + firstExpression.toString() + "<" + secondExpression.toString() + " )";
            case "<=" -> "( " + firstExpression.toString() + "<=" + secondExpression.toString() + " )";
            case "==" -> "( " + firstExpression.toString() + "==" + secondExpression.toString() + " )";
            case "!=" -> "( " + firstExpression.toString() + "!=" + secondExpression.toString() + " )";
            case ">" -> "( " + firstExpression.toString() + ">" + secondExpression.toString() + " )";
            case ">=" -> "( " + firstExpression.toString() + ">=" + secondExpression.toString() + " )";
            default -> "";
        };
    }

    @Override
    public Value eval(MyDictionaryI<String, Value> symTable, MyHeapI<Integer, Value> heap) throws Exceptions {
        Value value1, value2;
        value1 = firstExpression.eval(symTable, heap);
        if(value1.getType().equals(new IntType() ) ){
            value2 = secondExpression.eval(symTable, heap);
            if(value2.getType().equals(new IntType() ) ){
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int number1 = intValue1.getValue();
                int number2 = intValue2.getValue();
                switch (operation.getValue()){
                    case "<":
                        if(number1 < number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "<=":
                        if(number1 <= number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "==":
                        if(number1 == number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "!=":
                        if(number1 != number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case ">":
                        if(number1 > number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case ">=":
                        if(number1 >= number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    default:
                        throw new Exceptions("operation non existing");
                }
            }
            else {
                throw new Exceptions("missmatch type");
            }
        }
        else{
            throw new Exceptions("missmatch type");
        }
    }

    @Override
    public Type typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = firstExpression.typecheck(typeEnvironment);
        typeSecondExpression = secondExpression.typecheck(typeEnvironment);

        if(typeFistExpression.equals(new IntType())){
            if(typeSecondExpression.equals(new IntType())){
                return new BoolType();
            }else {
                throw new Exceptions("Type mismatch!");
            }
        }else {
            throw new Exceptions("Type mismatch!");
        }
    }
}
