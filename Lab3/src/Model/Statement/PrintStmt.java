package Model.Statement;
import Exceptions.Exceptions;
import Model.PrgState.PrgState;
import Model.Type.Type;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Adt.MyListI;
import Model.Expresion.ExpresionI;
import Model.Value.Value;

public class PrintStmt implements StatementI {
    ExpresionI exp;
    
    public PrintStmt(ExpresionI exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String,Value> symbolTable = state.getSymTable();
        MyListI<Value> out = state.getList();
        MyHeapI<Integer, Value> heap = state.getHeap();

        out.add(exp.eval(symbolTable, heap));
        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        exp.typecheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return String.format("Print(%s)", exp.toString());
    }
}
