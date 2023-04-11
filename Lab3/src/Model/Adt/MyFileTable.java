package Model.Adt;
import java.util.HashMap;

public class MyFileTable<T1, T2> implements MyFileTableI<T1, T2> {
    private HashMap<T1,T2> dict;

    public MyFileTable() {
        this.dict = new HashMap<>();
    }

    @Override
    public void put(T1 v1, T2 v2) {
        dict.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dict.replace(v1, v2);
    }

    @Override
    public void remove(T1 v1){
        dict.remove(v1);
    }

    @Override
    public T2 lookUp(T1 id) {
        return dict.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dict.containsKey(id);
    }

    @Override
    public String toString() {
        return this.dict.toString();
    }
}
