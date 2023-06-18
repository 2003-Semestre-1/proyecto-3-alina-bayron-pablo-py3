/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Alina
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Users {
    //private int id;
    private String user;
    private String fullName;
    private int permissions;
    private String password;

    public Users (String user, String fullName, int permissions, String password) {
        //this.id = id;
        this.user = user;
        this.fullName = fullName;
        this.permissions = permissions;
        this.password = password;
    }

    public static boolean eliminarUsuario(List<Users> listaUsers, String user) {
        Iterator<Users> iterator = listaUsers.iterator();
        while (iterator.hasNext()) {
            Users usuario = iterator.next();
            if (usuario.getUser().equals(user)) {
                iterator.remove();
                //System.out.println("Usuario eliminado: " + user);
                return true; // Se eliminó el usuario
            }
        }
        return false;
    }

    public static Users buscarUsuario(List<Users> listaUsers, String user) {
        for (Users usuario : listaUsers) {
            if (usuario.getUser().equals(user)) {
                return usuario;
            }
        }
        return null; // El usuario no fue encontrado
    }
    
    public static List<Users> crearListaUsers(Users usuario, List<Users> listaUsers) {
        listaUsers.add(usuario);
        return listaUsers;
    }

        
     public String getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public int getPermissions() {
        return permissions;
    }

    public String getPassword() {
        return password;
    }
    
    public void setUser(String user) {
        this.user = user;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public static void prueba () {
        Users usuario1 = new Users("user1", "John Doe", 1, "password1");
        Users usuario2 = new Users("user2", "Jane Smith", 2, "password2");
        Users usuario3 = new Users("user3", "Alice Johnson", 3, "password3");

        List<Users> listaUsers = new ArrayList<>();
        listaUsers = crearListaUsers(usuario1, listaUsers);
        listaUsers = crearListaUsers(usuario2, listaUsers);
        listaUsers = crearListaUsers(usuario3, listaUsers);

        
        // Imprimir la lista de usuarios
        for (Users usuario : listaUsers) {
            System.out.println("Usuario: " + usuario.getUser());
            System.out.println("Nombre completo: " + usuario.getFullName());
            System.out.println("Permisos: " + usuario.getPermissions());
            System.out.println("Contraseña: " + usuario.getPassword());
            System.out.println("-----------------------------");
        }
        
        System.out.println(buscarUsuario(listaUsers,"user1"));
        
        System.out.println(eliminarUsuario(listaUsers,"user1"));
    }
   
}
