package Model.Adt;
import Exceptions.Exceptions;
import java.util.HashMap;
import java.util.Collection;

public class MyDictionary<E1,E2> implements MyDictionaryI<E1,E2> {
    HashMap<E1, E2> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    public MyDictionary(MyDictionary<E1, E2> myDict){
        try {
            this.dictionary = (HashMap<E1, E2>) myDict.dictionary.clone();
        } catch (Exception e) {
            throw new RuntimeException("copy constructor error");
        }
    }

    @Override
    public boolean isDefined(E1 key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public E2 lookUp(E1 key) throws Exceptions {
        if (!isDefined(key))
            throw new Exceptions( key + " is not defined.");
        return this.dictionary.get(key);
    }

    @Override
    public void update(E1 key, E2 value) throws Exceptions {
        if (!isDefined(key))
            throw new Exceptions(key + " is not defined.");
        this.dictionary.put(key, value);
    }

    @Override
    public Collection<E2> values() {
        return this.dictionary.values();
    }

    @Override
    public void remove(E1 key) throws Exceptions {
        if (!isDefined(key))
            throw new Exceptions(key + " is not defined.");
        this.dictionary.remove(key);
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }

    @Override
    public void put(E1 key, E2 value) {
        this.dictionary.put(key, value);
    }

    @Override
    public HashMap<E1, E2> getContent() {
        return this.dictionary;
    }

    @Override
    public MyDictionary<E1, E2> clone() throws CloneNotSupportedException {
        try{
            MyDictionary<E1, E2> newDict = (MyDictionary<E1, E2>) super.clone();
            newDict.dictionary = (HashMap<E1, E2>) dictionary.clone();
            return newDict;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone() in MyDict error\n");
        }
    }
}
