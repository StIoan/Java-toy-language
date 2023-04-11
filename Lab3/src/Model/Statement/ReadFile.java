package Model.Statement;

import java.io.BufferedReader;

import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyFileTableI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

public class ReadFile implements StatementI {
    ExpresionI expression;
    String variableName;

    public ReadFile(ExpresionI expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symbolTable = state.getSymTable();
        MyFileTableI<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value evaluatedExpression = expression.eval(symbolTable, state.getHeap());
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new Exceptions("Non string type");
        }
        if (!fileTable.isDefined((StringValue) evaluatedExpression)){
            throw new Exceptions("file name not declared");
        }
        BufferedReader bufferedReader = fileTable.lookUp((StringValue) evaluatedExpression);

        try {
            String valueAsString = bufferedReader.readLine();
            int value;
            if (valueAsString.equals("")){
                value = 0;
            } else {
                value = Integer.parseInt(valueAsString);
            }

            symbolTable.put(variableName,new IntValue(value));
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
