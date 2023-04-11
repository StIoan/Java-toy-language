package Model.Adt;
import Exceptions.Exceptions;
import java.util.Collection;
import java.util.HashMap;

public interface MyDictionaryI<E1,E2> {
    boolean isDefined(E1 key);
    void put(E1 key, E2 value);
    E2 lookUp(E1 key) throws Exceptions;
    void update(E1 key, E2 value) throws Exceptions;
    Collection<E2> values();
    void remove(E1 key) throws Exceptions;
    HashMap<E1, E2> getContent();
}
