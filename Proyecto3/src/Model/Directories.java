/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Alina
 */
public class Directories {
    private String directoryName;
    private int permissions;
    private String route;
    private int size;
    private String author;
    private String creationDate;
    private String state;
    private String group;
    
    public Directories (String directoryName,int permissions, String route,int size, String author, String state, String group) {
        this.directoryName = directoryName;
        this.permissions = permissions;
        this.route = route;
        this.size = size;
        this.author = author;
        LocalDate currentDate = java.time.LocalDate.now();
        creationDate = currentDate.toString();
        this.state = state;
        this.group = group;
    }

    public static boolean eliminarDirectory(List<Directories> listaDirectories, String directoryName) {
        Iterator<Directories> iterator = listaDirectories.iterator();
        while (iterator.hasNext()) {
            Directories directory = iterator.next();
            if (directory.getDirectoryName().equals(directoryName)) {
                iterator.remove();
                return true; // Se eliminó el directorio
            }
        }
        return false;
    }

    public static Directories buscarDirectory(List<Directories> listaDirectories, String directoryName) {
        for (Directories directory : listaDirectories) {
            if (directory.getDirectoryName().equals(directoryName)) {
                return directory;
            }
        }
        return null; // El directorio no fue encontrado
    }
    
    public static List<Directories> crearListaDirectories(Directories directory, List<Directories> listaDirectories) {
        listaDirectories.add(directory);
        return listaDirectories;
    }
   
    public String getDirectoryName() {
        return directoryName;
    }

    public String getRoute() {
        return route;
    }

    public int getPermissions() {
        return permissions;
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
    
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public void setRoute(String route) {
        this.route = route;
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

    
    public static void pruebaDirec () {
        Directories dir1 = new Directories("directoryName1", 777,"Home",20, "user1", "Cerrado", "rootgroup");
        Directories dir2 = new Directories("directoryName2", 777,"Home",20, "user2", "Cerrado", "rootgroup");
        Directories dir3 = new Directories("directoryName3", 777,"Home",20, "user3", "Cerrado", "rootgroup");

        List<Directories> listaDirectories = new ArrayList<>();
        listaDirectories = crearListaDirectories(dir1, listaDirectories);
        listaDirectories = crearListaDirectories(dir2, listaDirectories);
        listaDirectories = crearListaDirectories(dir3, listaDirectories);

        
        // Imprimir la lista de dirs
        for (Directories dir : listaDirectories) {
            System.out.println("Nombre: " + dir.getDirectoryName());
            System.out.println("Permisos: " + dir.getPermissions());
            System.out.println("Ruta: " + dir.getRoute());
            System.out.println("Tamaño: " + dir.getSize());
            System.out.println("Autor: " + dir.getAuthor());
            System.out.println("Fecha de creacion: " + dir.getCreationDate());
            System.out.println("Estado: " + dir.getState());
            System.out.println("Grupo: " + dir.getGroup());
            System.out.println("-----------------------------");
        }
        
        System.out.println(buscarDirectory(listaDirectories,"directoryName1"));
        
        System.out.println(eliminarDirectory(listaDirectories,"directoryName1"));
    }
   

}
