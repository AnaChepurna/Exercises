package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {

        try {
            path = Files.createTempFile(null, null);
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
        path.toFile().deleteOnExit();
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            ExceptionHandler.log(e);
        }
        return 0;
    }

    public void putEntry(Entry entry) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path));
            oos.writeObject(entry);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            ExceptionHandler.log(ex);
        }
//        }
    }

    public Entry getEntry() {
        if(getFileSize() == 0)
            return null;
        Entry entry = null;
        try {
            ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path));
            entry = (Entry) in.readObject();
            in.close();
        }catch (
                Exception ex){
            ExceptionHandler.log(ex);
        }
        return entry;
    }

    public void remove() {
        try{
            Files.delete(path);
        }catch (IOException e){
            ExceptionHandler.log(e);
        }
    }
}