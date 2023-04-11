package Model.Expresion;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.Type;
import Model.Value.Value;
import Exceptions.Exceptions;

public class VarExp implements ExpresionI {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyDictionaryI<String, Value> symTable, MyHeapI<Integer, Value> heap) throws Exceptions {
        return symTable.lookUp(id);
    }

    @Override
    public Type typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        return typeEnvironment.lookUp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
