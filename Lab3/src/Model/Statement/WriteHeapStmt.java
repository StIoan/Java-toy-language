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

public class WriteHeapStmt implements StatementI {
    private String varName;
    private ExpresionI exp;

    public WriteHeapStmt(String varName, ExpresionI expression) {
        this.varName = varName;
        this.exp = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symTable = state.getSymTable();
        MyHeapI<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(varName) ) {
            if (symTable.lookUp(varName).getType() instanceof RefType) {
               if(symTable.lookUp(varName) instanceof RefValue){
                   int address = ((RefValue) symTable.lookUp(varName)).getAddress();
                   if(heap.isDefined(address)){
                        Value result = exp.eval(symTable,heap);
                        if (heap.lookup(address).getType().equals(result.getType())){
                            heap.update(address,result);
                        }else {
                            throw new Exceptions("Type mismatch!");
                        }
                   }else {
                       throw new Exceptions("Address not existent!");
                   }
               }
            } else {
                throw new Exceptions("Value not ref type!");
            }
        }else{
            throw new Exceptions("Variable not declared!");
        }
        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeVariableId = typeEnvironment.lookUp(varName);
        Type typeExpression = exp.typecheck(typeEnvironment);
        if(typeVariableId.equals(new RefType(typeExpression))){
            return typeEnvironment;
        }else{
            throw new Exceptions("Type mismatch!");
        }
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", this.varName, this.exp);
    }
}
