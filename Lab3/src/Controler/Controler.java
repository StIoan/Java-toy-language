package Controler;
import Repository.RepoI;
import Model.Adt.MyHeapI;
import Model.PrgState.PrgState;
import Model.Value.RefValue;
import Model.Value.Value;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controler {
    private final RepoI repository;
    private ExecutorService executorService;
    private boolean muteLogProgramStateExecution;
    private boolean mutePrintProgramStateExecution;

    public Controler(RepoI repository) {
        this.repository = repository;
        muteLogProgramStateExecution = false;
        mutePrintProgramStateExecution = false;
    }

    public void setMuteLogProgramStateExecution(boolean muteLogProgramStateExecution) {
        this.muteLogProgramStateExecution = muteLogProgramStateExecution;
    }

    public void setMutePrintProgramStateExecution(boolean mutePrintProgramStateExecution) {
        this.mutePrintProgramStateExecution = mutePrintProgramStateExecution;
    }

    public void addProgram(PrgState newProgramState) {
        repository.addPrgState(newProgramState);
    }

    private List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues){
        return  symbolTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> {RefValue valueFromSymbolTable = (RefValue)value; return valueFromSymbolTable.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromHeap(Collection<Value> heapValues){
        return heapValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> {RefValue valueFromHeap = (RefValue)value; return valueFromHeap.getAddress();})
                .collect(Collectors.toList());
    }

    private Map<Integer, Value> garbageCollector(List<Integer> addressesFromSymbolTable, List<Integer> addressesFromHeapValues, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(entry -> addressesFromSymbolTable.contains(entry.getKey()) || addressesFromHeapValues.contains(entry.getKey()) )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<PrgState> removeCompletedPrograms(List<PrgState> programList){
        return programList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    void oneStepForAllProgramStates(List<PrgState> programStateList) {
        if(!muteLogProgramStateExecution)
            programStateList.forEach(t -> {
                try {
                    repository.logPrgStateExec(t);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        List<Callable<PrgState>> callableProgramStateList = programStateList
                .stream()
                .map((PrgState programState) -> (Callable<PrgState>)(programState::oneStep))
                .collect(Collectors.toList());

        try {
            List<PrgState> newProgramStateList = executorService.invokeAll(callableProgramStateList)
                    .stream()
                    .map(programStateFuture -> {
                        try {
                            return programStateFuture.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException("InterruptedException()\n");
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            throw new RuntimeException("ExecutionException()\n");
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            programStateList.addAll(newProgramStateList);
            if(!muteLogProgramStateExecution) {
                programStateList.forEach(t -> {
                    try {
                        repository.logPrgStateExec(t);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            repository.setPrgStateList(programStateList);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException\n");
        }
    }

    public void allStep() {
        repository.setLogFilePath("D:\\University\\Year - 2\\Metode advansate de programare\\Lab3\\Lab3\\log.txt");
        executorService = Executors.newFixedThreadPool(2);

        List<PrgState> programStateList = removeCompletedPrograms(repository.getPrgStateList());
        if (!mutePrintProgramStateExecution)
            System.out.println(programStateList.get(0).toString());

        while(programStateList.size() > 0) {
            MyHeapI<Integer, Value> heap = programStateList.get(0).getHeap();
            for (PrgState programState: programStateList) {
                programState.getHeap().setContent(garbageCollector(
                        getAddressesFromSymbolTable(programState.getSymTable().getContent().values()),
                        getAddressesFromHeap(heap.getContent().values()),
                        heap.getContent()
                    )
                );
            }
            
            oneStepForAllProgramStates(programStateList);
            if (!mutePrintProgramStateExecution)
                for (PrgState programState: programStateList) {
                    System.out.println(programState.toString());
                }

            programStateList = removeCompletedPrograms(repository.getPrgStateList());
        }
        executorService.shutdown();

        repository.setPrgStateList(programStateList);
    }
}