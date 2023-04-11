package Model.Statement;
import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Value.Value;
import Model.Type.Type;

public class AssignStmt implements StatementI {
    String id;
    ExpresionI exp;

    public AssignStmt(String id, ExpresionI exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(id)){
            Value value = exp.eval(symTable, state.getHeap());
            Type type = (symTable.lookUp(id).getType());
            if (value.getType().equals(type)){
                symTable.update(id, value);
            } else {
                throw new Exceptions("The types don't match!");
            }
        } else {
            throw new Exceptions("The variable has not been declared!");
        }
        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeVariableId = typeEnvironment.lookUp(this.id);
        Type typeExpression = this.exp.typecheck(typeEnvironment);
        if(typeVariableId.equals(typeExpression)){
            return typeEnvironment;
        }else{
            throw new Exceptions("Type mismatch!");
        }
    }

    @Override
    public String toString(){
        return id + " = " + exp.toString();
    }
}
