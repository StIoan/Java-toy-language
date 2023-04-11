package Model.PrgState;
import Model.Value.StringValue;
import Model.Value.Value;
import java.io.BufferedReader;
import Exceptions.Exceptions;
import Model.Adt.MyDictionary;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyFileTable;
import Model.Adt.MyFileTableI;
import Model.Adt.MyHeap;
import Model.Adt.MyListI;
import Model.Adt.MyStack;
import Model.Adt.MyStackI;
import Model.Statement.StatementI;
import Model.Adt.MyHeapI;
import Model.Adt.MyList;

public class PrgState {
    private int id = 0;
    private static int threadCount = 0;
    private final MyStackI<StatementI> stackOfStatements;
    private final MyDictionaryI<String, Value> symbolTable;
    private final MyListI<Value> outputList;
    private final MyFileTableI<StringValue, BufferedReader> fileTable;
    private final MyHeapI<Integer, Value> heap;

    public synchronized static int nextId () {
        threadCount++;
        return threadCount;
    }

    public void setId (int newId) {
        this.id = newId;
    }

    public PrgState(MyStackI<StatementI> stack, MyDictionaryI<String, Value> dict, MyListI<Value> list) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = new MyFileTable<>();
        heap = new MyHeap<>();
    }

    public PrgState(MyStackI<StatementI> stack, MyDictionaryI<String, Value> dict, MyListI<Value> list, MyFileTableI<StringValue, BufferedReader> fileDict) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = fileDict;
        heap = new MyHeap<>();
    }

    public PrgState(MyStackI<StatementI> stack, MyDictionaryI<String, Value> dict, MyListI<Value> list, MyFileTableI<StringValue, BufferedReader> fileDict, MyHeapI<Integer,Value> heap) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = fileDict;
        this.heap = heap;
    }

    public PrgState() {
        stackOfStatements = new MyStack<>();
        symbolTable = new MyDictionary<>();
        outputList = new MyList<>();
        fileTable = new MyFileTable<>();
        heap = new MyHeap<>();
    }

    public MyFileTableI<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyStackI<StatementI> getStack() { return stackOfStatements; }

    public MyDictionaryI<String, Value> getSymTable() {
        return symbolTable;
    }

    public MyListI<Value> getList() {
        return outputList;
    }

    public MyHeapI<Integer, Value> getHeap() {
        return heap;
    }

    public boolean isNotCompleted(){
        return !(stackOfStatements.isEmpty());
    }

    public PrgState oneStep() throws Exceptions {
        if(stackOfStatements.isEmpty()){
            throw new Exceptions("No statements in program state!");
        }
        StatementI currentStatement = stackOfStatements.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString(){
        String output = "--------------------------------------------------------";

        output += "\nid = ";
        output += id;

        output += "\n[stackOfStatements]\n";
        output += stackOfStatements.toString();

        output += "\n[symbolTable]\n";
        output += symbolTable.toString();

        output += "\n[outputList]\n";
        output += outputList.toString();

        output += "\n[heap]\n";
        output += heap.toString();

        output += "\n--------------------------------------------------------";
        return  output;
    }
}
