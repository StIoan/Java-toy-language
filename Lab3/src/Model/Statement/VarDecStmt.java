package Model.Statement;
import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.PrgState.PrgState;
import Model.Type.Type;
import Model.Value.Value;

public class VarDecStmt implements StatementI {
    private String name;
    private Type type;

    public VarDecStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symTable = state.getSymTable();

        if (symTable.isDefined(name) ){
            throw new Exceptions("Variable already declared!");
        }
        else{
            symTable.put(name, type.defaultValue());
        }

        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) {
        typeEnvironment.put(name, type);
        return typeEnvironment;
    }

    
    @Override
    public String toString(){
        return type + " " + name + "; ";
    }
}
