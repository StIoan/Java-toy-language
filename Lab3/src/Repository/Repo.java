package Repository;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import Model.PrgState.PrgState;

public class Repo implements RepoI {
    private List<PrgState> prgStates;
    private String logFilePath;

    public Repo() {
        this.prgStates = new ArrayList<>();
    }

    @Override
    public void addPrgState(PrgState newPrgState) {
        prgStates.add(newPrgState);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) {
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new RuntimeException("cannot log program state\n");
        }
        logFile.write(prgState.toString());
        logFile.close();
    }

    @Override
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override
    public List<PrgState> getPrgStateList() {
        return this.prgStates;
    }

    @Override
    public void setPrgStateList(List<PrgState> prgStates) {
        this.prgStates = prgStates;
    }
}
