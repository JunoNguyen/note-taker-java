package juno.controller;

import juno.model.TodoEntity;
import juno.repository.IRepository;
import juno.repository.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TodoController {

    private final Scanner console;
    private IRepository<Integer, TodoEntity> repository;

    public TodoController(IRepository<Integer, TodoEntity> repository, Scanner console) {
        this.repository = repository;
        this.console = console;
    }

    private void handleCreate(String todoDescription) {
        /** Add something to the repository **/
        int rand = 0;

            // define the range
            int max = 100000;
            int min = 1;
            int range = max - min + 1;
            rand = (int) (Math.random() * range) + min;

            // This should keep creating a new rand num if it exists
            while (this.repository.exists(rand)) {
                rand = (int) (Math.random() * range) + min;
            }

        TodoEntity newTodo = new TodoEntity(rand, todoDescription);
//            System.out.println(newTodo + " ID: " + rand);
            this.repository.addOne(newTodo);

    }

    public void showRepository() {
        System.out.println("Repository here: " + this.repository);
    }

    private void handleDelete(Integer id) throws EntityNotFoundException {
        /** Delete something from the repository **/
        /** Bonus: what happens if the ID doesn't exist? **/
        try {
            this.repository.deleteOne(id);
        } catch (EntityNotFoundException e) {
            System.out.println("Entity not found");
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        }
    };

    private void handleUpdate (Integer id, String todoDescription) {
        /** Update/replace an existing entity **/
        TodoEntity updatedTodo = new TodoEntity(id, todoDescription);
        this.repository.updateOne(id, updatedTodo);
    }

    private void handleList() throws EntityNotFoundException {
        ArrayList<TodoEntity> foundTodos = this.repository.getAll();
        var builder = new StringBuilder();
        for (int i = 0; i < foundTodos.size(); i++) {
            TodoEntity todo = foundTodos.get(i);
            builder.append(todo.toString());
            builder.append("\n");
        }
        System.out.println(builder);
    }

    private void handleGetOne(Integer id) throws EntityNotFoundException {
        TodoEntity data = this.repository.getOne(id);
        System.out.println(data.toString());
    }

    public void run() {
        int exitFlag = 0;

        while(exitFlag == 0) {
            System.out.println("Enter command (create/update/delete/view/view one/exit): ");
            String command = this.console.nextLine();
                    switch (command.trim()) {
                case "create": {
                    promptCreate();
                    break;
                }
                case "update": {
                    promptUpdate();
                    break;
                }
                case "delete": {
                    promptDelete();
                    break;
                }
                case "view": {
                    promptList();
                    break;
                }
                case "view one": {
                    promptListOne();
                    break;
                }
                case "exit": {
                    exitFlag = 1;
                    System.out.println("Goodbye!");
                    break;
                }
                default: {
                    System.out.println("Sorry, I didn't understand that command.");
                }
            }
        }
    }

    private void promptListOne() {
        System.out.println("Enter the ID of the task to view: ");
        String id = this.console.nextLine();
        try {
            Integer parsedId = parseInt(id);
            handleGetOne(parsedId);
        } catch (NumberFormatException _e) {
            System.out.println("Sorry, I couldn't interpret that ID.");
        } catch (EntityNotFoundException e) {
            System.out.println("Sorry, that ID does not exist");
        }
    }

    private void promptCreate() {
        System.out.println("Enter the description of the task: ");
        String todoDescription;
        todoDescription = this.console.nextLine();
        handleCreate(todoDescription);
        System.out.println("Added the task to your todo list!");
    }

    private void promptDelete() {
        System.out.println("Enter the ID of the task to delete: ");
        String id = this.console.nextLine();
        try {
            Integer parsedId = parseInt(id);
            handleDelete(parsedId);
        } catch (NumberFormatException _e) {
            System.out.println("Sorry, I couldn't interpret that ID.");
        }
    }

    private void promptUpdate() {
        System.out.println("Enter the ID of the task to update: ");
        String id = this.console.nextLine();
        try {
            Integer parsedId = parseInt(id);
            System.out.println("Enter the description of the task: ");
            String todoDescription = this.console.nextLine();
            handleUpdate(parsedId, todoDescription);
        } catch (NumberFormatException _e) {
            System.out.println("Sorry, I couldn't interpret that ID.");
        }
    }

    private void promptList() {
        System.out.println("Displaying all tasks...");
        try {
            handleList();
        } catch (EntityNotFoundException e) {
            System.out.println("Sorry, could not find tasks.");
        }
    }

}
