package Model.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type {
    private Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return this.inner;
    }

    @Override
    public boolean equals(Type anotherType) {
        if (anotherType instanceof RefType) {
            return inner.equals(((RefType) anotherType).getInner());
        } else {
            return false;
        }
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, this.inner);
    }

    @Override
    public String toString() {
        return String.format("Ref(%s)", this.inner);
    }

}
