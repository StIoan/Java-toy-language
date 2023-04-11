package Model.Statement;
import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.PrgState.PrgState;
import Model.Type.Type;

public interface StatementI {
    PrgState execute(PrgState state) throws Exceptions;
    MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions;
}
