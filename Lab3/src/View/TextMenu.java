package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands;
    private final static boolean exitCommandNotGiven = true;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    public void addCommand(Command command){
        commands.put(command.getKey(),command);
    }

    public void printMenu(){
        System.out.println("MENU");
        for(Command command : commands.values()){
            String line = String.format("%s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    @SuppressWarnings("all")
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(exitCommandNotGiven){
            printMenu();
            System.out.println("> ");
            String key = scanner.next();
            Command command = commands.get(key);
            if(command == null){
                System.out.println("[!] Invalid option");
                continue;
            }
            command.execute();
        }
    }
}
