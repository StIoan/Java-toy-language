package Model.Statement;

import java.io.BufferedReader;
import java.io.FileReader;

import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyFileTableI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

public class OpenRFile implements StatementI {
    ExpresionI exp;

    public OpenRFile(ExpresionI expression) {
        this.exp = expression;
    }
    
    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symbolTable = state.getSymTable();
        MyFileTableI<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value evaluatedExpression = exp.eval(symbolTable, state.getHeap());
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new Exceptions("Not string type");
        }
        if (fileTable.isDefined((StringValue) evaluatedExpression)) {
            throw new Exceptions("Filename already declared");
        }


        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(((StringValue) evaluatedExpression).getValue()));
            fileTable.put((StringValue) evaluatedExpression,bufferedReader);
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        exp.typecheck(typeEnvironment);
        return typeEnvironment;
    }
}
