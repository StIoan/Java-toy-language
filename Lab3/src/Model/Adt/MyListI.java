package Model.Adt;
import Exceptions.Exceptions;
import java.util.List;

public interface MyListI<E> {
    void add(E elem);
    E pop() throws Exceptions;
    boolean isEmpty();
    List<E> getList();
}
