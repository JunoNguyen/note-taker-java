package juno.controller;

import juno.model.TodoEntity;
import juno.repository.IRepository;

import static java.lang.Integer.parseInt;

public class TodoController {

    private IRepository<Integer, TodoEntity> repository;

    public TodoController(IRepository<Integer, TodoEntity> repository) {
        this.repository = repository;
    }

    private void handleCreate(String todoDescription) {
        /** Add something to the repository **/
    }

    private void handleDelete(Integer id) {
        /** Delete something from the repository **/
        /** Bonus: what happens if the ID doesn't exist? **/
    };

    private void handleUpdate (Integer id, String todoDescription) {
        /** Update/replace an existing entity **/
    }

    public void run() {
        int exitFlag = 0;

        while(exitFlag == 0) {
            String command = System.console().readLine("Enter command (create/update/delete/exit): ");
            switch (command) {
                case "create": {
                    promptCreate();
                }
                case "update": {
                    promptUpdate();
                }
                case "delete": {
                    promptDelete();
                }
                case "exit": {
                    exitFlag = 1;
                }
                default: {
                    System.out.println("Sorry, I didn't understand that command.");
                }
            }
        }
    }

    private void promptCreate() {
        String todoDescription = System.console().readLine("Enter the description of the task: ");
        handleCreate(todoDescription);
        System.out.println("Added the task to your todo list!");
    }

    private void promptDelete() {
        String id = System.console().readLine("Enter the ID of the task to delete: ");
        try {
            Integer parsedId = parseInt(id);
            handleDelete(parsedId);
            System.out.println("Deleted the task!");
        } catch (NumberFormatException _e) {
            System.out.println("Sorry, I couldn't interpret that ID.");
        }
    }

    private void promptUpdate() {
        String id = System.console().readLine("Enter the ID of the task to update: ");
        try {
            Integer parsedId = parseInt(id);
            String todoDescription = System.console().readLine("Enter the description of the task: ");
            handleUpdate(parsedId, todoDescription);
        } catch (NumberFormatException _e) {
            System.out.println("Sorry, I couldn't interpret that ID.");
        }
    }

}
