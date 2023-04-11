package Model.Statement;
import Exceptions.Exceptions;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyStackI;
import Model.PrgState.PrgState;
import Model.Type.Type;

public class CompStmt implements StatementI {
    private StatementI firstStmt;
    private StatementI secondStmt;

    public CompStmt(StatementI firstStmt, StatementI secondStmt) {
        this.firstStmt = firstStmt;
        this.secondStmt = secondStmt;
    }

    public PrgState execute(PrgState state) {
        MyStackI<StatementI> stack = state.getStack();
        stack.push(secondStmt);
        stack.push(firstStmt);
        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        return secondStmt.typecheck(firstStmt.typecheck(typeEnvironment));
    }

    @Override
    public String toString(){
        return "(" + firstStmt.toString() + "\n" + secondStmt.toString() + ")";
    }
}
