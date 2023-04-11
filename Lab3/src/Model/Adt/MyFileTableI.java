package Model.Adt;
import Exceptions.Exceptions;

public interface MyFileTableI<E1, E2> {
    boolean isDefined(E1 key);
    void put(E1 key, E2 value);
    E2 lookUp(E1 key) throws Exceptions;
    void update(E1 key, E2 value) throws Exceptions;
    void remove(E1 key) throws Exceptions;
}
