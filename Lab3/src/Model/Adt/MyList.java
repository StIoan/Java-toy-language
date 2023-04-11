package Model.Adt;
import Exceptions.Exceptions;
import java.util.List;
import java.util.ArrayList;

public class MyList<E> implements MyListI<E> {
    List<E> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(E elem) {
        this.list.add(elem);
    }

    @Override
    public E pop() throws Exceptions{
        if (list.isEmpty())
            throw new Exceptions("The list is empty!");
        return this.list.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public List<E> getList() {
        return list;
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
