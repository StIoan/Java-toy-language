package View;

import Controler.Controler;

public class RunExampleCommand extends Command {
    private final Controler controller;

    public RunExampleCommand(String key, String description, Controler controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (RuntimeException e) {
            System.out.println(e.toString());
        }
    }
}
