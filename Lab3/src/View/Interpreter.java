package View;

import Controler.Controler;
import Exceptions.Exceptions;
import Model.Adt.MyDictionary;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyList;
import Model.Adt.MyListI;
import Model.Adt.MyStack;
import Model.Adt.MyStackI;
import Model.Expresion.ArithExp;
import Model.Expresion.ReadHeapExp;
import Model.Expresion.RelExp;
import Model.Expresion.ValueExp;
import Model.Expresion.VarExp;
import Model.PrgState.PrgState;
import Model.Statement.AssignStmt;
import Model.Statement.CompStmt;
import Model.Statement.ForkStmt;
import Model.Statement.IfStmt;
import Model.Statement.NewStmt;
import Model.Statement.PrintStmt;
import Model.Statement.StatementI;
import Model.Statement.VarDecStmt;
import Model.Statement.WhileStmt;
import Model.Statement.WriteHeapStmt;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.Repo;

public class Interpreter {

    //int v; v=2; Print(v)
    private static Controler example1() throws Exceptions {
        Repo repository = new Repo();
        Controler Controler = new Controler(repository);
        Controler.setMuteLogProgramStateExecution(true);
        MyDictionaryI<String, Type> typeEnvironment = new MyDictionary<>();
        StatementI program1 =
                new CompStmt(
                    new VarDecStmt("v", new IntType()),
                    new CompStmt(
                            new AssignStmt("v", new ValueExp(new IntValue(2))),
                            new PrintStmt(new VarExp("v"))
                    )
                );

        program1.typecheck(typeEnvironment);

        MyStack<StatementI> executionStack = new MyStack<>();
        executionStack.push(program1);

        MyDictionaryI<String, Value> symbolTable = new MyDictionary<>();
        MyListI<Value> out = new MyList<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        Controler.addProgram(myProgramState);

        return Controler;
    }

    //int a; int b; a=2+3*5; b=a+1; Print(b)
    private static Controler example2() throws Exceptions {
        Repo repository = new Repo();
        Controler Controler = new Controler(repository);
        Controler.setMuteLogProgramStateExecution(true);
        MyDictionaryI<String, Type> typeEnvironment = new MyDictionary<>();
        StatementI program1 = new CompStmt(new VarDecStmt("a",new IntType()),
        new CompStmt(new VarDecStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new
                        ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                        new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new
                                IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        program1.typecheck(typeEnvironment);

        MyStackI<StatementI> executionStack = new MyStack<>();
        executionStack.push(program1);

        MyDictionaryI<String, Value> symbolTable = new MyDictionary<>();
        MyListI<Value> out = new MyList<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        Controler.addProgram(myProgramState);
        return Controler;
    }

    //bool a; int v; a=true; If(a)Then(v=2)Else(v=3); Print(v)
    private static Controler example3() throws Exceptions {
        Repo repository = new Repo();
        Controler Controler = new Controler(repository);
        Controler.setMuteLogProgramStateExecution(true);
        MyDictionaryI<String, Type> typeEnvironment = new MyDictionary<>();
        StatementI program1 = new CompStmt(new VarDecStmt("a", new BoolType()),
                new CompStmt(new VarDecStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        program1.typecheck(typeEnvironment);

        MyStackI<StatementI> executionStack = new MyStack<>();
        executionStack.push(program1);

        MyDictionaryI<String, Value> symbolTable = new MyDictionary<>();
        MyListI<Value> out = new MyList<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        Controler.addProgram(myProgramState);
        return Controler;
    }

    //int v; v=4; while(v>0) print(v);v=v-1;
    private static Controler example4() throws Exceptions {
        Repo repository = new Repo();
        Controler Controler = new Controler(repository);
        Controler.setMuteLogProgramStateExecution(true);
        MyDictionaryI<String, Type> typeEnvironment = new MyDictionary<>();
        StatementI program1 =
                new CompStmt(
                        new VarDecStmt("v",new IntType()),
                        new CompStmt(
                                new AssignStmt("v",new ValueExp(new IntValue(4))),
                                new WhileStmt(
                                        new RelExp(
                                                new VarExp("v"),
                                                new ValueExp(new IntValue(0)),
                                                new StringValue(">")
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt(
                                                        "v",
                                                        new ArithExp(
                                                                '-',
                                                                new VarExp("v"),
                                                                new ValueExp(new IntValue(1))
                                                        )
                                                )
                                        )
                                )
                        )
                );

        program1.typecheck(typeEnvironment);

        MyStackI<StatementI> executionStack = new MyStack<>();
        executionStack.push(program1);

        MyDictionaryI<String, Value> symbolTable = new MyDictionary<>();
        MyListI<Value> out = new MyList<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        Controler.addProgram(myProgramState);
        return Controler;
    }

    //Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); printf(rH(rH(a)))
    private static Controler example5() throws Exceptions {
        Repo repository = new Repo();
        Controler Controler = new Controler(repository);
        Controler.setMuteLogProgramStateExecution(true);
        MyDictionaryI<String, Type> typeEnvironment = new MyDictionary<>();
        StatementI program1 =
                new CompStmt(
                        new VarDecStmt("v", new RefType(new IntType())),
                        new CompStmt(
                                new NewStmt("v", new ValueExp(new IntValue(20))),
                                new CompStmt(
                                        new VarDecStmt("a",new RefType(new RefType(new IntType()))),
                                        new CompStmt(
                                                new NewStmt("a", new VarExp("v")),
                                                new CompStmt(
                                                        new NewStmt("v",new ValueExp(new IntValue(30))),
                                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                                )

                                        )
                                )
                        )
                );

        program1.typecheck(typeEnvironment);

        MyStackI<StatementI> executionStack = new MyStack<>();
        executionStack.push(program1);

        MyDictionaryI<String, Value> symbolTable = new MyDictionary<>();
        MyListI<Value> out = new MyList<>();

        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        Controler.addProgram(myProgramState);
        return Controler;
    }

    //int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;printf(v);print(rH(a)) ); printf(v); print(rH(a))
    private static Controler example6() throws Exceptions {
        Repo repository = new Repo();
        Controler Controler = new Controler(repository);
        Controler.setMuteLogProgramStateExecution(true);
        MyDictionaryI<String, Type> typeEnvironment = new MyDictionary<>();
        StatementI program = new CompStmt(
                new VarDecStmt("v",new IntType()),
                new CompStmt(
                        new VarDecStmt("a",new RefType(new IntType())),
                        new CompStmt(
                            new AssignStmt("v",new ValueExp(new IntValue(10))),
                            new CompStmt(
                                    new NewStmt("a",new ValueExp(new IntValue(22))),
                                    new CompStmt(
                                            new ForkStmt(
                                                    new CompStmt(
                                                            new WriteHeapStmt("a",new ValueExp(new IntValue(30))),
                                                            new CompStmt(
                                                                    new AssignStmt("v",new ValueExp(new IntValue(32))),
                                                                    new CompStmt(
                                                                            new PrintStmt(new VarExp("v")),
                                                                            new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                                    )
                                                            )
                                                    )
                                            ),
                                            new CompStmt(
                                                    new PrintStmt(new VarExp("v")),
                                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                            )
                                    )
                            )
                        )
                )
        );

        program.typecheck(typeEnvironment);

        MyStackI<StatementI> executionStack = new MyStack<>();
        MyDictionaryI<String, Value> symbolTable = new MyDictionary<>();
        MyListI<Value> out = new MyList<>();

        executionStack.push(program);
        PrgState myProgramState = new PrgState(executionStack, symbolTable, out);

        Controler.addProgram(myProgramState);
        return Controler;
    }

    public static void main(String[] args) throws Exceptions {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0","exit"));
        menu.addCommand(new RunExampleCommand("1","int v; v=2; Print(v)",example1()) );
        menu.addCommand(new RunExampleCommand("2","int a; int b; a=2+3*5; b=a+1; Print(b)", example2()) );
        menu.addCommand(new RunExampleCommand("3","bool a; int v; a=true; If(a)Then(v=2)Else(v=3); Print(v)", example3()) );
        menu.addCommand(new RunExampleCommand("4","int v; v=4; while(v>0) print(v);v=v-1", example4()));
        menu.addCommand(new RunExampleCommand("5","Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); printf(rH(rH(a)))", example5()));
        menu.addCommand(new RunExampleCommand("6","int v; Ref int a; v=10; new(a,22); fork(wH(a,30);v=32;printf(v);print(rH(a)) ); printf(v); print(rH(a))", example6()));

        menu.show();
    }
}
