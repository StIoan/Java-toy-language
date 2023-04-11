package Repository;
import java.io.IOException;
import java.util.List;

import Model.PrgState.PrgState;

public interface RepoI {
    void addPrgState(PrgState newPrgState);
    void logPrgStateExec(PrgState newPrgState) throws IOException;
    void setLogFilePath(String filePath);
    List<PrgState> getPrgStateList();
    void setPrgStateList(List<PrgState> newPrgStateList);
}
