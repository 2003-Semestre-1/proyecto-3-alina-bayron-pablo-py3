/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Alina
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Comandos {
    //format(100); // Ejemplo de uso: crear un archivo miDiscoDuro.fs con tamaño 100

    public static void format(int size) {
        try {
            File file = new File("miDiscoDuro.fs");

            if (file.exists()) {
                boolean deleted = file.delete();
                if (!deleted) {
                    System.out.println("No se pudo eliminar el archivo miDiscoDuro.fs existente.");
                    return;
                }
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la contraseña del usuario root: ");
            String password = scanner.nextLine(); //GUARDARLA EN EL ARCHIVO

            if (file.createNewFile()) {
                System.out.println("Archivo miDiscoDuro.fs creado exitosamente.");

                // Crear carpeta HOME para el usuario root
                File homeFolder = new File("HOME");
                if (homeFolder.mkdir()) {
                    System.out.println("Carpeta HOME creada exitosamente.");
                } else {
                    System.out.println("No se pudo crear la carpeta HOME.");
                }

                // Escritura del archivo
                FileWriter writer = new FileWriter(file);
                writer.write("Tamaño del disco: " + size + " GB " + password);
                writer.close();

                System.out.println("El disco ha sido formateado correctamente.");
            } else {
                System.out.println("No se pudo crear el archivo miDiscoDuro.fs.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}
