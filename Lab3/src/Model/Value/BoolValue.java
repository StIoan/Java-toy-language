package Model.Value;
import Model.Type.Type;
import Model.Type.BoolType;

public class BoolValue implements Value {
    private boolean value;

    public BoolValue(boolean v) {
        this.value = v;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object anotherValue) {
        if (!(anotherValue instanceof BoolValue))
            return false;
        BoolValue castValue = (BoolValue) anotherValue;
        return this.value == castValue.value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }
}
