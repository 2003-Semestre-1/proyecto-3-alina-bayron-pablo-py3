package View;

import Controller.JSON;
import Controller.RecibirComando;
import Model.AbrirArchivo;
import Model.AbrirContenidoArchivo;
import Model.AgregarUsuario;
import Model.CambiarContrasenia;
import Model.CambiarDirectorio;
import Model.CambiarUsuario;
import Model.CerrarArchivo;
import Model.CrearArchivo;
import Model.CrearDirectorio;
import Model.AgregarGrupo;
import Model.CrearDisco;
import Model.DatosUsuario;
import Model.EjecucionComando;
import Model.Exit;
import Model.InformacionSistema;
import Model.LimpiarPantalla;
import Model.RutaActual;
import Model.OberservarArchivosAbiertos;
import Model.Comando;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;




/**
 * @author bayron 
 * @author alina
 */
public class Consola {

    public static String UsuarioEnSesion = "";
    public static String NombreSesion = "";
    public static String ActualFS = "";
    public static String DirectorioActual = "";
    public static int bloqueltotales;
    public static int bloquesUsado = 8;
    public static int bloquesLibres;
    
    public static void main(String[] args) {
        System.out.println("Ejecuntando el MINI File System....");
        RecibirComando comando = new RecibirComando();
        Comando terminarPrograma = new Exit(comando);
        Comando limpiarPantalla = new LimpiarPantalla(comando);
        EjecucionComando ejecucion = new EjecucionComando();
        JSON json = new JSON();
        Scanner scanner = new Scanner(System.in);
        boolean diskChosen = false;
        boolean salir = false;
        while (!salir) {
            if (!ActualFS.equalsIgnoreCase("")) {
                System.out.print(">> " + UsuarioEnSesion + "@" + ActualFS + " ");
            } else {
                System.out.print(">> ");
            }
            String input = scanner.nextLine();
            String[] command = input.trim().split(" ", 5);
            String commandName = command[0].trim();
            if (!(commandName.equalsIgnoreCase("usedisk") || commandName.equalsIgnoreCase("format") || commandName.equalsIgnoreCase("exit")) && !diskChosen) {
                System.out.println("Por favor formatee un disco con format miDiscoDuro.fs tamano, o bien seleccione un disco duro con usedisk miDiscoDuro.fs");
                continue;
            }
            switch (commandName) {
                case "usedisk" -> {
                    String nombreDisco;
                    String jsonContent;
                    try {
                        nombreDisco = command[1].trim();
                        jsonContent = json.LeerArchivo(nombreDisco);
                        if (jsonContent.equalsIgnoreCase("")) {
                            System.out.println("Error: Debe especificar el nombre de un disco previamente creado, por ejemplo: usedisk miDiscoDuro.fs");
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error: Debe especificar el nombre de un disco previamente creado, por ejemplo: usedisk miDiscoDuro.fs");
                        break;
                    }
                    System.out.println("Se utilizará el disco: " + nombreDisco);
                    json.setFilePath(nombreDisco);
                    json.LeerArchivo(nombreDisco);
                    ActualFS = nombreDisco;
                    UsuarioEnSesion = "root";
                    NombreSesion = "root";
                    DirectorioActual = "HOME";
                    diskChosen = true;
                }
                case "format" -> {
                    String tamanoDisco;
                    double checkNumeric;
                    String nombreDisco;
                    try {
                        nombreDisco = command[1].trim();
                        tamanoDisco = command[2].trim();
                        checkNumeric = Double.parseDouble(tamanoDisco);
                        bloqueltotales = Integer.parseInt(tamanoDisco);

                    } catch (Exception e) {
                        System.out.println("Error: Debe especificar el nombre y un tamano del disco que sea numérico, por ejemplo: format pruebaDisk 4096");
                        break;
                    }
                    json.setFilePath(nombreDisco);
                    Comando formatCommand = new CrearDisco(comando, nombreDisco, tamanoDisco, json);
                    ejecucion.setCommand(formatCommand);
                    ejecucion.ejecutarComando();
                    formatCommand = null;
                    ActualFS = nombreDisco;
                    UsuarioEnSesion = "root";
                    NombreSesion = "root";
                    DirectorioActual = "HOME";
                    diskChosen = true;
                    String jsonContent = json.LeerArchivo(nombreDisco);
                }
                case "exit" -> {
                    scanner.close();
                    salir = true;
                    ejecucion.setCommand(terminarPrograma);
                    ejecucion.ejecutarComando();
                }
                case "useradd" -> {
                    String nombreUsuario;
                    try {
                        nombreUsuario = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando useradd debe ir seguido por el nombre de usuario, por ejemplo: useradd ccampos");
                        break;
                    }
                    System.out.print("Digite el nombre completo del nuevo usuario: ");
                    scanner = new Scanner(System.in);
                    String nombreCompleto = scanner.nextLine();
                    Comando agregarusuario = new AgregarUsuario(comando, nombreUsuario, nombreCompleto, json);
                    ejecucion.setCommand(agregarusuario);
                    ejecucion.ejecutarComando();
                    bloquesUsado += 3;
                }
                case "groupadd" -> {
                    String nombreGrupo;
                    try {
                        nombreGrupo = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando groupadd debe ir seguido por el nombre de grupo: groupadd GrupoSO");
                        break;
                    }

                    if (UsuarioEnSesion.equalsIgnoreCase("root")) {
                        Comando agregargrupo = new AgregarGrupo(comando, nombreGrupo, json);
                        ejecucion.setCommand(agregargrupo);
                        ejecucion.ejecutarComando();
                    } else {
                        System.out.println("Error: El usuario actual no tiene permiso para crear grupos.");
                    }
                    bloquesUsado ++;
                }
                case "whoami" -> {
                    Comando usuariodatos = new DatosUsuario(comando, UsuarioEnSesion, NombreSesion);
                    ejecucion.setCommand(usuariodatos);
                    ejecucion.ejecutarComando();
                }
                case "pwd" -> {
                    Comando ruta = new RutaActual(comando, DirectorioActual);
                    ejecucion.setCommand(ruta);
                    ejecucion.ejecutarComando();
                }
                case "passwd" -> {
                    String nombreUsuario;
                    try {
                        nombreUsuario = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando passwd debe ir seguido por el nombre de usuario ya registrado, por ejemplo: passwd root");
                        break;
                    }
                    Comando cambiarContrasenia = new CambiarContrasenia(comando, nombreUsuario, json);
                    ejecucion.setCommand(cambiarContrasenia);
                    ejecucion.ejecutarComando();
                }
                case "su" -> {
                    String nombreUsuario = "";
                    if (command.length == 1) {
                        nombreUsuario = "root";
                    } else if (command.length > 1) {
                        nombreUsuario = command[1].trim();
                    }
                    String foundUsuario = json.parseArray(json.getJsonContent().toString(), "usuarios", "directorios");
                    String nombreCompleto;
                    if (foundUsuario.contains(nombreUsuario)) {
                        System.out.print("Digite la contraseñaa: ");
                        scanner = new Scanner(System.in);
                        String password = scanner.nextLine();
                        String foundPassword = json.parseArray(json.getJsonContent().toString(), "usuarios", "directorios");
                        if (foundPassword.contains(password)) {
                            int startIndex = foundPassword.indexOf(nombreUsuario);
                            String subString1 = foundPassword.substring(startIndex);
                            int startIndex2 = subString1.indexOf("nombreCompleto");
                            String subString2 = subString1.substring(startIndex2);
                            int startIndex3 = subString2.indexOf(":");
                            String subString3 = subString2.substring(startIndex3 + 3);
                            int endIndex = subString3.indexOf('"');
                            nombreCompleto = subString3.substring(0, endIndex);

                            UsuarioEnSesion = nombreUsuario;
                            NombreSesion = nombreCompleto;
                            Comando cambiarusuario = new CambiarUsuario(comando, nombreUsuario, json, password);
                            ejecucion.setCommand(cambiarusuario);
                            ejecucion.ejecutarComando();
                        } else {
                            System.out.println("La contraseña es incorrecta. Digite correctamnete la contraseña.");
                        }
                    } else {
                        System.out.println("El usuario no ha sido registrado. Digite un usuario que haya sido previamente registrado");
                    }
                }
                case "mkdir" -> {
                    String path;
                    String currentPathDir = "";
                    try {
                        path = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando mkdir debe ir seguido por un nombre de directorio deseado, por ejemplo: mkdir HOME/documents");
                        break;
                    }
                    if (path.contains(",") && command.length == 2) {
                        int count = 0;
                        int startIndex = 0;
                        int endIndex = 0;
                        for (int i = 0; i < path.length(); i++) {
                            if (path.charAt(i) == ',') {
                                count++;
                                endIndex = i;
                                String currentPath = path.substring(startIndex, endIndex).trim();
                                if (!currentPath.contains("/") && !currentPath.contains("\\")) {
                                    currentPathDir = DirectorioActual + "/" + currentPath;
                                }
                                Comando creardirectorio = new CrearDirectorio(comando, json, currentPath, UsuarioEnSesion, currentPathDir);
                                ejecucion.setCommand(creardirectorio);
                                ejecucion.ejecutarComando();
                                startIndex = i + 1;
                                currentPath = path.substring(startIndex).trim();
                                if (i == path.lastIndexOf(",")) {
                                    if (!currentPath.contains("/") && !currentPath.contains("\\")) {
                                        currentPathDir = DirectorioActual + "/" + currentPath;
                                    }
                                    creardirectorio = new CrearDirectorio(comando, json, currentPath, UsuarioEnSesion, currentPathDir);
                                    ejecucion.setCommand(creardirectorio);
                                    ejecucion.ejecutarComando();
                                }
                            }
                        }
                    } else if (command.length > 2) {

                        for (int i = 1; i < command.length; i++) {
                            String currentPath = command[i].trim();

                            if (currentPath.endsWith(",")) {
                                currentPath = currentPath.substring(0, currentPath.length() - 1);
                            }

                            if (!currentPath.contains("/") && !currentPath.contains("\\")) {
                                currentPathDir = DirectorioActual + "/" + currentPath;
                            }
                            Comando crearDirectorio = new CrearDirectorio(comando, json, currentPath, UsuarioEnSesion, currentPathDir);
                            ejecucion.setCommand(crearDirectorio);
                            ejecucion.ejecutarComando();
                        }
                    } else {
                        if (!path.contains("/") && !path.contains("\\")) {
                            currentPathDir = DirectorioActual + "/" + path;
                        }
                        Comando creardirectorio = new CrearDirectorio(comando, json, path, UsuarioEnSesion, currentPathDir);
                        ejecucion.setCommand(creardirectorio);
                        ejecucion.ejecutarComando();
                    }
                }
                case "clear" -> {
                    System.out.println("Limpiando la pantalla..."  + "/f");
                    ejecucion.setCommand(limpiarPantalla);
                    ejecucion.ejecutarComando();
                    for (int i = 0; i < 100; i++) {
                        System.out.println(" ");
                    }
                    System.out.println("Pantalla limpiada."); 
                }
                case "cd" -> {
                    String path = "";
                    try {
                        path = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: Debe especificar el directorio, por ejemplo: cd HOME/test");
                        break;
                    }
                    String jsonString = json.getJsonContent().toString();
                    int directoriosIndex = jsonString.indexOf("\"directorios\":");
                    int directoriosStartIndex = jsonString.indexOf("[", directoriosIndex) + 1;
                    int directoriosEndIndex = jsonString.indexOf("]", directoriosIndex);
                    String directoriosContent = jsonString.substring(directoriosStartIndex, directoriosEndIndex);
                    if (path.equalsIgnoreCase("..")){ // Para devolverse un directorio para arriba
                        if (DirectorioActual.equalsIgnoreCase("HOME")){
                            System.out.println("Error: No puede devolverse más directorios porque HOME es el directorio raíz.");
                            break;
                        }
                        else if (DirectorioActual.contains("/")){
                            int lastSlashIndex;
                            String lastSlashString;
                            String lastSlashString2;
                            lastSlashIndex = DirectorioActual.lastIndexOf("/");
                            lastSlashString = DirectorioActual.substring(lastSlashIndex);
                            lastSlashString2 = DirectorioActual.substring(0, lastSlashIndex);
                            path = lastSlashString2;
                            DirectorioActual = path;
                            System.out.println("Se cambió al directorio " + path);
                            break;
                        }
                    }
                    if ((!directoriosContent.contains(path))) {
                        System.out.println("Error: El directorio no existe.");
                        break;
                    }
                    if (!path.contains("/")) { // es un relative path
                        path = DirectorioActual + "/" + path;
                    }
                    if ((!directoriosContent.contains(path))) {
                        System.out.println("Error: El directorio no existe.");
                        break;
                    }
                    if (path.equalsIgnoreCase("HOME/")) // Para devolverse al directorio HOME
                        path = "HOME";
                    DirectorioActual = path;
                    Comando cambiarD = new CambiarDirectorio(comando, path);
                    ejecucion.setCommand(cambiarD);
                    ejecucion.ejecutarComando();
                }
                case "touch" -> {
                    String fileName;
                    try {
                        fileName = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando touch debe ir seguido por el nombre del archivo, por ejemplo: touch proyecto.txt");
                        break;
                    }
                    if ((fileName.contains("/"))) {
                        System.out.println("Error: El archivo no puede tener un / en el nombre.");
                        break;
                    }
                    Comando crearArchivo = new CrearArchivo(comando,json,UsuarioEnSesion,fileName,DirectorioActual);
                    ejecucion.setCommand(crearArchivo);
                    ejecucion.ejecutarComando();
                    crearArchivo = null;
                    bloquesUsado += 16;
                }
                case "cat" -> {
                    String fileName = "";
                    try {
                        fileName = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando cat debe ir seguido por el nombre del archivo previamente creado, por ejemplo: cat proyecto.txt");
                        break;
                    }
                    String jsonString = json.getJsonContent().toString();
                    int archivosIndex = jsonString.indexOf("\"archivos\":");
                    int archivosStartIndex = jsonString.indexOf("[", archivosIndex) + 1;
                    int archivosEndIndex = jsonString.indexOf("]", archivosIndex);
                    String archivosContent = jsonString.substring(archivosStartIndex, archivosEndIndex);
                    if (!archivosContent.contains(fileName)){
                        System.out.println("Error: El archivo no existe.");
                        break;
                    }
                    if (!fileName.contains("/")){
                        fileName = DirectorioActual+"/"+fileName;
                    }
                    Comando verContenido = new AbrirContenidoArchivo(comando,json,fileName,DirectorioActual);
                    ejecucion.setCommand(verContenido);
                    ejecucion.ejecutarComando();
                    verContenido = null;
                }
                case "openFile" -> {
                    String fileName;
                    try {
                        fileName = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El comando openFile debe ir seguido por el nombre de un archivo previamente creado, por ejemplo: openFile proyecto.txt");
                        break;
                    }
                    Comando abrirArchivo = new AbrirArchivo(comando, json, fileName, DirectorioActual);
                    ejecucion.setCommand(abrirArchivo);
                    ejecucion.ejecutarComando();
                    bloquesUsado ++;
                }
                case "closeFile" -> {
                    String fileName;
                    try {
                        fileName = command[1].trim();
                    } catch (Exception e) {
                        System.out.println("Error: El commando closeFile debe ir seguido por un archivo previamente creado, por ejemplo: closeFile proyecto.txt");
                        break;
                    }
                    bloquesUsado --;
                    Comando cerrararchivo = new CerrarArchivo(comando, json, fileName, DirectorioActual);
                    ejecucion.setCommand(cerrararchivo);
                    ejecucion.ejecutarComando();
                }
                    
                case "viewFilesOpen" -> {
                    Comando archivosAbiertos = new OberservarArchivosAbiertos(comando,json);
                    ejecucion.setCommand(archivosAbiertos);
                    ejecucion.ejecutarComando();
                }
                    
                case "infoFS" -> {
                    Comando infoFileSys = new InformacionSistema(comando,json);
                    ejecucion.setCommand(infoFileSys);
                    ejecucion.ejecutarComando();
                }
                    
                case "visualizar" -> {
                    bloquesLibres = bloqueltotales - bloquesUsado;
                    String bloques = String.valueOf(bloqueltotales);
                    String bloquesUsados = String.valueOf(bloquesUsado);
                    String bloquesDisponibles = String.valueOf(bloquesLibres);
                    String asignacion = "Continua";

                    bloques = bloques.replace("\"", "");
                    bloquesUsados = bloquesUsados.replace("\"", "");
                    bloquesDisponibles = bloquesDisponibles.replace("\"", "");                    
                    asignacion = asignacion.replace("\"", "");

                    String bloquesString = "Bloques totales: "+bloques;
                    String bloquesUsadosString = " | Bloques usados: "+bloquesUsados;
                    String bloquesDisponiblesString = " | Bloques disponibles: "+bloquesDisponibles;
                    String asignacionString = " | Asignación: "+asignacion;

                    JFrame frame = new JFrame("Visualizando bloques del disco duro: "+ActualFS);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(1000, 700);


                    JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    containerPanel.setPreferredSize(new Dimension(200, 0)); // Set the preferred width


                    for (int i = 0; i < bloqueltotales; i++) {
                        Color color;
                        if (i > bloquesUsado)
                            color = Color.WHITE;
                        else
                            color = Color.RED;

                        containerPanel.add(new BloquesMemoria(color));
                    }

                    JLabel label1 = new JLabel(bloquesString);
                    JLabel label2 = new JLabel(bloquesUsadosString);
                    JLabel label3 = new JLabel(bloquesDisponiblesString);
                    JLabel label4 = new JLabel(asignacionString);

                    JPanel labelPanel = new JPanel(new FlowLayout());
                    labelPanel.add(label1);
                    labelPanel.add(label2);
                    labelPanel.add(label3);
                    labelPanel.add(label4);

                    JPanel mainPanel = new JPanel(new BorderLayout());
                    mainPanel.add(containerPanel, BorderLayout.CENTER);
                    mainPanel.add(labelPanel, BorderLayout.SOUTH);

                    JScrollPane scrollPane = new JScrollPane(mainPanel);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

                    frame.getContentPane().add(scrollPane); // Add scrollPane to the frame
                    frame.setVisible(true);
                    }
                    default -> System.out.println("Comando no realizado o mal escrito. Digite un comando nuevamente");
            }
        }
    }
}
