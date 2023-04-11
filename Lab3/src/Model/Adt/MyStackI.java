package Model.Adt;
import java.util.Stack;

import Exceptions.Exceptions;

public interface MyStackI<E> {
    E pop() throws Exceptions;
    void push(E elem);
    boolean isEmpty();
    E peek();
    Stack<E> getStack();
}
