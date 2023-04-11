package Model.Statement;
import Exceptions.Exceptions;
import Model.Adt.MyDictionary;
import Model.Adt.MyDictionaryI;
import Model.Adt.MyStackI;
import Model.Expresion.ExpresionI;
import Model.PrgState.PrgState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStmt implements StatementI {
    private ExpresionI exp;
    private StatementI stmt;

    public WhileStmt(ExpresionI exp, StatementI stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws Exceptions {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        MyStackI<StatementI> stack = state.getStack();

        if (!value.getType().equals(new BoolType())) {
            throw new Exceptions(String.format("%s is not of type BoolType", value));
        }

        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            stack.push(this);
            stack.push(stmt);
        }

        return null;
    }

    @Override
    public MyDictionaryI<String, Type> typecheck(MyDictionaryI<String, Type> typeEnvironment) throws Exceptions {
        Type typeExpression = exp.typecheck(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            stmt.typecheck(new MyDictionary<>((MyDictionary<String, Type>) typeEnvironment));
            return typeEnvironment;
        }else{
            throw new Exceptions("Condition not bool type!");
        }
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", exp, stmt);
    }

}
