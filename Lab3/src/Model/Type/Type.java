package Model.Type;
import Model.Value.Value;

public interface Type {
    boolean equals(Type type);
    Value defaultValue();
}
