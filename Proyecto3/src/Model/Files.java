/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Alina
 */
public class Files {
    private String fileName;
    private int permissions;
    private String author;
    private int size;
    private String content;
    private String creationDate;
    private String state;
    private String group;
    
    public Files (String fileName,int permissions, String author,int size, String content, String state, String group) {
        this.fileName = fileName;
        this.permissions = permissions;
        this.size = size;
        this.author = author;
        LocalDate currentDate = java.time.LocalDate.now();
        creationDate = currentDate.toString();
        this.content = content;
        this.state = state;
        this.group = group;
    }

    public static boolean eliminarFiles(List<Files> listaFiles, String fileName) {
        Iterator<Files> iterator = listaFiles.iterator();
        while (iterator.hasNext()) {
            Files directory = iterator.next();
            if (directory.getFileName().equals(fileName)) {
                iterator.remove();
                return true; // Se eliminó el directorio
            }
        }
        return false;
    }

    public static Files buscarFiles(List<Files> listaFiles, String fileName) {
        for (Files file : listaFiles) {
            if (file.getFileName().equals(fileName)) {
                return file;
            }
        }
        return null; // El directorio no fue encontrado
    }
    
    public static List<Files> crearListaFiles(Files file, List<Files> listaFiles) {
        listaFiles.add(file);
        return listaFiles;
    }
   
    public String getFileName() {
        return fileName;
    }      
        
    public int getPermissions() {
        return permissions;
    }

    public String getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }
    
    public String getAuthor() {
        return author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getState() {
        return state;
    }

    public String getGroup() {
        return group;
    }
    
    public void setFilesName(String fileName) {
        this.fileName = fileName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String setGroup() {
        return group;
    }
    
}
