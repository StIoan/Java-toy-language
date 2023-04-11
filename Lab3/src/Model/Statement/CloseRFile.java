package Model.Statement;

import java.io.BufferedReader;
import java.io.IOException;

import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyFileTableI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

public class CloseRFile implements StatementI {
    ExpresionI expression;

    public CloseRFile(ExpresionI expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions{
        MyDictionaryI<String, Value> symbolTable = state.getSymTable();
        MyFileTableI<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value evaluatedExpression = expression.eval(symbolTable, state.getHeap());
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new Exceptions("non string type");
        }

        if (!fileTable.isDefined((StringValue) evaluatedExpression)){
            throw new Exceptions("filename not declared");
        }
        BufferedReader bufferedReader = fileTable.lookUp((StringValue) evaluatedExpression);

        try {
            bufferedReader.close();
            fileTable.remove((StringValue) evaluatedExpression);
        } catch (IOException e) {
            throw new RuntimeException("cannot close file");
        }

        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        expression.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
