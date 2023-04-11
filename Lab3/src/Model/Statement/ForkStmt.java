package Model.Statement;
import java.io.BufferedReader;
import Exceptions.Exceptions;
import Model.Adt.MyDictionary;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyFileTableI;
import Model.Adt.MyHeapI;
import Model.Adt.MyListI;
import Model.Adt.MyStack;
import Model.Adt.MyStackI;
import Model.PrgState.PrgState;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

public class ForkStmt implements StatementI {
    StatementI statement;

    public ForkStmt(StatementI statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyDictionaryI<String, Value> symbolTable = state.getSymTable();
        MyListI<Value> outputList = state.getList();
        MyFileTableI<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyHeapI<Integer, Value> heap = state.getHeap();
        MyStackI<StatementI> newStack = new MyStack<>();

        newStack.push(statement);

        if(symbolTable instanceof MyDictionary){
            try {
                MyDictionaryI<String, Value> newSymbolTable = new MyDictionary<> ((MyDictionary<String, Value>) symbolTable);
                PrgState newProgramState = new PrgState(newStack,newSymbolTable,outputList,fileTable,heap);
                newProgramState.setId(PrgState.nextId());

                return newProgramState;
            } catch (RuntimeException e) {
                throw new RuntimeException("error in copy constructor MyDict\n");
            }
        }else {
            throw new Exceptions("Type mismatch!");
        }
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        statement.typecheck(new MyDictionary<>((MyDictionary<String, Type>) typeEnvironment));
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")  ";
    }
}
