package Model.Expresion;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements ExpresionI {
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Value eval(MyDictionaryI<String, Value> symTable, MyHeapI<Integer, Value> heap) {
        return this.value;
    }

    @Override
    public Type typecheck(MyDictionaryI<String, Type> typeEnvironment) {
        return value.getType();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
