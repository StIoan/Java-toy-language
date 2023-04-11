package Model.Expresion;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.Type;
import Model.Value.Value;
import Exceptions.Exceptions;

public interface ExpresionI {
    Value eval(MyDictionaryI<String, Value> tbl, MyHeapI<Integer, Value> heap) throws Exceptions; 
    Type typecheck(MyDictionaryI<String, Type> type) throws Exceptions;
}
