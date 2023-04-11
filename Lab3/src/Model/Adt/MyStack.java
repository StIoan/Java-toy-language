package Model.Adt;
import Exceptions.Exceptions;
import java.util.Stack;

public class MyStack<E> implements MyStackI<E> {
    Stack<E> stack;

    public MyStack() {
        this.stack = new Stack<>();
    }

    @Override
    public E pop() throws Exceptions {
        if (stack.isEmpty())
            throw new Exceptions("Stack is empty!");
        return this.stack.pop();
    }

    @Override
    public void push(E element) {
        this.stack.push(element);
    }

    @Override
    public String toString() {
        return this.stack.toString();
    }

    @Override
    public E peek() {
        return this.stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public Stack<E> getStack(){
        return this.stack;
    }
}
