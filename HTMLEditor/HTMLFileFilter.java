package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.nio.file.Files;

/**
 * Created by Ana on 09.07.2017.
 */
public class HTMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if(f.isDirectory()){
            return true;
        } else if(!f.isDirectory()){
            String resolution = f.getName().substring(f.getName().lastIndexOf(".")).toLowerCase();

            if(resolution.equals(".html") || resolution.equals(".htm")){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
