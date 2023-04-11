package Model.Expresion;

import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyHeapI;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements ExpresionI {
    private ExpresionI exp;

    public ReadHeapExp (ExpresionI exp) {
        this.exp = exp;
    }

    @Override
    public Value eval (MyDictionaryI<String, Value> symTable, MyHeapI<Integer, Value> heap) throws Exceptions {
        Value result = exp.eval(symTable, heap);
        if (result instanceof RefValue){
            int address = ((RefValue) result).getAddress();
            if (heap.isDefined(address)){
                return heap.lookup(address);
            }else{
                throw new Exceptions("Value not declared!");
            }
        }else{
            throw new Exceptions("Type mismatch!");
        }
    }

    @Override
    public Type typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeExpression;
        typeExpression = exp.typecheck(typeEnvironment);
        if(typeExpression instanceof RefType){
            RefType refType = (RefType)typeExpression;
            return refType.getInner();
        }else{
            throw new Exceptions("Value not ref type!");
        }
    }

    @Override
    public String toString() {
        return String.format("ReadHeap(%s)", this.exp);
    }
}
