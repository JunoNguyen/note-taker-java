package juno;

import juno.model.TodoEntity;
import juno.repository.IRepository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Serializer {
    public static void serialize(IRepository<Integer, TodoEntity> repository, String filePath) throws Exception {
        File outCsv = new File(filePath);
        outCsv.delete();
        try {
            outCsv.createNewFile();
        } catch (Exception _e) {
            throw new Exception("Couldn't create file");
        }

        var fileWriter = new FileWriter(filePath);
        try {
            for (TodoEntity entity : repository.getAll()) {
                fileWriter.write(entity.getId() + "," + entity.getDescription() + "\n");
            }
            fileWriter.write('\u001a');
        } finally {
            fileWriter.close();
        }
    }

    public static void deserializeCsv(IRepository<Integer, TodoEntity> repository, String filePath) throws Exception {
        File readCsv = new File(filePath);
        if(!readCsv.exists()) {
            // throw new Exception("File not found");
            return;
        }

        var fileReader = new FileReader(filePath);


        var builder = new StringBuilder();
        boolean done = false;
        while(!done) {
            char readChar = (char) fileReader.read();
            if(readChar == '\u001a'){
                done = true;
                System.out.println("Reached EOF!");
                break;
            }

            if(readChar == '\n') {
                var str = builder.toString();
                var args = str.split(",");
                repository.addOne(
                    new TodoEntity(
                        Integer.parseInt(args[0]),
                        args[1])
                );
                // Debug
                System.out.println("Loaded entity #" + args[0]);
                builder = new StringBuilder();
            } else {
                builder.append(readChar);
            }
        }
        fileReader.close();

        System.out.println("Finished loading entities");
    }
}
