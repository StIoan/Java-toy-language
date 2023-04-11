package Model.Statement;

import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStmt implements StatementI {
    private String varName;
    private ExpresionI exp;

    public NewStmt(String varName, ExpresionI exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symTable = state.getSymTable();
        MyHeapI<Integer, Value> heap = state.getHeap();
        
        if (symTable.isDefined(this.varName)) {
            if (symTable.lookUp(this.varName).getType() instanceof RefType) {
                Value result = exp.eval(symTable, heap);
                Type locationType = ((RefValue) symTable.lookUp(varName)).getLocationType();
                if (locationType.equals(result.getType())){
                    int nextFreeLocation = heap.getNextFreeLocation();
                    heap.add(nextFreeLocation, result);
                    symTable.update(varName, new RefValue(nextFreeLocation, locationType));
                } else {
                    throw new Exceptions("type missmatch");
                }
            } else {
                throw new Exceptions("Value not ref type");
            }
        } else {
            throw new Exceptions("Variable not declared");
        }
        
        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions{
        Type typeVariableId = typeEnvironment.lookUp(this.varName);
        Type typeExpression = this.exp.typecheck(typeEnvironment);
        if(typeVariableId.equals(new RefType(typeExpression))){
            return typeEnvironment;
        }else{
            throw new Exceptions("Type mismatch!");
        }
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", this.varName, this.exp);
    }
}
