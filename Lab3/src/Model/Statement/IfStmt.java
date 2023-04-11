package Model.Statement;
import Model.Adt.MyDictionary;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyStackI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Value.BoolValue;
import Model.Value.Value;
import Model.Type.BoolType;
import Model.Type.Type;
import Exceptions.Exceptions;

public class IfStmt implements StatementI {
    ExpresionI exp;
    StatementI thenStmt;
    StatementI elseStmt;

    public IfStmt(ExpresionI exp, StatementI thenStmt, StatementI elseStmt) {
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        MyStackI<StatementI> stack = state.getStack();
        MyDictionaryI<String, Value> symbolTable = state.getSymTable();

        Value condition = exp.eval(symbolTable, state.getHeap());
        if (!condition.getType().equals(new BoolType())){
            throw new Exceptions("The condition evaluated to not BoolType");
        }

        BoolValue boolCondition = (BoolValue) condition;
        if(boolCondition.getValue()) {
            stack.push(thenStmt);
        } else{
            stack.push(elseStmt);
        }
        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String,Type> typeEnvironment) throws Exceptions {
        Type typeExpression = exp.typecheck(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            if(typeEnvironment instanceof MyDictionary){
                thenStmt.typecheck(new MyDictionary<>((MyDictionary<String, Type>) typeEnvironment));
                elseStmt.typecheck(new MyDictionary<>((MyDictionary<String, Type>) typeEnvironment));
                return typeEnvironment;
            }else {
                throw new Exceptions("Type mismatch!");
            }
        }else {
            throw new Exceptions("Condition not bool type!");
        }
    }

    @Override
    public String toString() {
        return "If(" + exp.toString() + ") " + "\n   (" + thenStmt.toString() + ")"
                + "\nelse (" + elseStmt.toString() + ")";
    }
}
