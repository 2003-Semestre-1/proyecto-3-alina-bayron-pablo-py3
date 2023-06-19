package Controller;


import Controller.JSON;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * @author bayron 
 * @author alina
 */
public class RecibirComando {
    public void exit() {
        System.out.println("Cerrando el File System program");
        System.exit(0);
    }
    
    public void format(String nombreDisco, String tamanoDisco, JSON json) {
        json.setJsonContent(new StringBuilder());
        json.getJsonContent().append("{");
        
        json.setFilePath(nombreDisco);
        json.agregarAtributo("nombreDisco", nombreDisco);
        json.agregarAtributo("tamanio", tamanoDisco);
        json.agregarAtributo("tamanioUtilizado", "32");
        double tamanoDiscoDbl = Double.parseDouble(tamanoDisco);
        tamanoDiscoDbl = tamanoDiscoDbl-32;
        json.agregarAtributo("tamanioDisponible", tamanoDiscoDbl+"");
        
        int tamanoInt = Integer.parseInt(tamanoDisco);
        double tamanioBloques = tamanoInt*3;
        json.agregarAtributo("bloques", ""+tamanioBloques);
        json.agregarAtributo("bloquesUtilizados", "32");
        json.agregarAtributo("bloquesDisponibles", ""+(tamanioBloques-32));
        json.agregarAtributo("asignacion", "contigua");
        json.agregarAtributo("unidad", "kb");

        String nombreCompleto = "root";
        
        System.out.print("Contraseña de usuario: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        
        String nombreUsuario = "root";
        String userJson =  "{\"nombre\":"+"\"" + nombreUsuario + "\", \"nombreCompleto\": \"" + nombreCompleto + "\", \"permisos\": \"7\", \"password\": \"" + password + "\"}";
        
        LocalDate currentDate = java.time.LocalDate.now();
        String currentDateString = currentDate.toString();
        
        String directorioJson = "{\"nombre\": \"HOME\",\"permisos\":\"777\",\"path\": \"HOME\", \"duenio\": \"root\", \"grupo\": \"rootgroup\", \"fechaCreacion\":"+"\"" + currentDateString + "\"}";
        String grupoJson = "{\"nombre\": \"rootgroup\", \"usuarios\": {\"root\"}}";
        String archivoJson = "{\"nombre\": \"settings.ini\",\"permisos\":\"777\",\"dueno\": \"root\",\"contenido\": \"CONFIGURACIÓN DEL SISTEMA OPERATIVO\", \"grupo\": \"rootgroup\",  \"fechaCreacion\":"+"\"" + currentDateString + "\", \"abierto\":\"false\", \"tamano\":\"16\", \"path\":\"HOME/settings.ini\"}";
        
        json.agregarObjeto("usuarios", "[" + userJson + "]");
        json.agregarObjeto("directorios", "[" + directorioJson + "]");
        json.agregarObjeto("grupos", "[" + grupoJson + "]");
        json.agregarObjeto("archivos", "[" + archivoJson + "]");
        
        json.EscribirArchivo();
        System.out.println("Disco " + nombreDisco + " formateado.");
    }
    
    public void userAdd(String nombreUsuario, String nombre, JSON json) {
        
        System.out.print("Digite la contraseña: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        
        String userJson =  "{\"nombre\":"+"\"" + nombreUsuario + "\", \"nombreCompleto\": \"" + nombre + "\", \"permisos\": \"7\", \"password\": \"" + password + "\"}";
        json.agregarUsuario(json.getJsonContent().toString(), userJson);
        json.EscribirArchivo();
        System.out.println("Usuario " + nombreUsuario + " agregado correctamente.");
    }
    
    public void groupAdd(String nombreGrupo, JSON json){
        String grupoJson = "{\"nombre\":" + "\"" + nombreGrupo + "\", \"usuarios\": [\"root\"]}";
        json.agregarGrupo(json.getJsonContent().toString(), grupoJson);
        json.EscribirArchivo();
        System.out.println("Grupo " + nombreGrupo + " agregado correctamente.");
    }
    
    public void passwd(String nombreUsuario, JSON json){
        System.out.print("Digite la contraseña anterior: ");
        Scanner scanner = new Scanner(System.in);
        String viejaPassword = scanner.nextLine();
        
        String foundPassword = json.parseArray(json.getJsonContent().toString(), "usuarios", "directorios");
        
        if (foundPassword.contains(viejaPassword)){
            System.out.print("Escriba la nueva contraseña: ");
            scanner = new Scanner(System.in);
            String nuevaPassword = scanner.nextLine();
        
            String updatedJson = json.ActualizarArrayAtributo(json.getJsonContent().toString(), "usuarios", "password", nuevaPassword, viejaPassword);
            json.EscribirArchivo();
            System.out.println("Contraseña del usuario " + nombreUsuario + " restablecida correctamente.");
        }
        else{
            System.out.println("La contraseña acterior es incorrecta. Digite nuevamente la contraseña anterior.");
        }
        
    }
    
    public void su(String nombreUsuario, String contrasenia, JSON json){
        String foundPassword = json.parseArray(json.getJsonContent().toString(), "usuarios", "directorios");
        
        if (foundPassword.contains(contrasenia)){
            System.out.println("Se cambió al usuario " + nombreUsuario);
        }
        else{
            System.out.println("La contraseña es incorrecta. Digite nuevamente la contraseña.");
        }
    }
    
    public void whoAmI(String nombreUsuario, String nombre){
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Nombre: " + nombre);
    }
    
    public void pwd(String path){
        System.out.println("Ruta actual: " + path);
    }
    
    public void mkdir(String path, String nombre, JSON json, String usuario, String nombreDirectorio){
        LocalDate currentDate = java.time.LocalDate.now();
        String currentDateString = currentDate.toString();
        
        String grupoName = "rootgroup";
        String directoryJson = "{\"nombre\":"+"\"" + nombre + "\",\"permisos\":\"777\",\"path\":"+"\"" + path + "\", \"dueno\":"+"\"" + usuario + "\", \"grupo\":"+"\"" + grupoName + "\", \"fechaCreacion\":"+"\"" + currentDateString + "\"}";
        json.agregarDirectorio(json.getJsonContent().toString(),directoryJson);
        json.EscribirArchivo();
        System.out.println("Directorio " + nombreDirectorio + " creado correctamente.");
    }
    
    public void clear(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
    
    public void cd(String path){
        System.out.println("Se cambió al directorio " + path);
    }
    
    public void touch(String nombreArchivo, String usuario, JSON json, String currentDirectory){
        LocalDate currentDate = java.time.LocalDate.now();
        String currentDateString = currentDate.toString();
        String contenido = " ";
        currentDirectory = currentDirectory+"/"+nombreArchivo;
        String grupoName = "rootgroup";
        String archivoJson = "{\"nombre\": "+"\"" + nombreArchivo + "\",\"permisos\":\"777\",\"duenio\": "+"\"" + usuario + "\", \"contenido\":"+"\"" + contenido + "\", \"grupo\":"+"\"" + grupoName + "\", \"fechaCreacion\":"+"\"" + currentDateString + "\", \"abierto\":\"false\", \"tamano\":\"32\", \"path\":"+"\"" + currentDirectory + "\"}";
        json.AgregarArchivo(json.getJsonContent().toString(), archivoJson);
        System.out.println("Se creó el archivo "+ nombreArchivo +" en el directorio "+ currentDirectory + " correctamente.");
    }
    
    public void cat(String nombreArchivo, JSON json, String currentDirectory){
        String jsonString = json.getJsonContent().toString();
        
        int archivosIndex = jsonString.indexOf("\"archivos\":");
        int archivosStartIndex = jsonString.indexOf("[", archivosIndex) + 1;
        int archivosEndIndex = jsonString.indexOf("]", archivosIndex);
        String archivosContent = jsonString.substring(archivosStartIndex, archivosEndIndex);
        String[] archivos = archivosContent.split("\\},");
        boolean encontrado = false;
        
        for (int i = 0; i < archivos.length; i++) {
            String archivo = archivos[i];
            if (!archivo.contains("}"))
                archivo = archivo + "}";
            String currentFile = json.parseAttribute(archivo, "path").trim();
            String currentFileName = json.parseAttribute(archivo, "nombre").trim();
            String currentFileContent = json.parseAttribute(archivo, "contenido").trim();
            currentDirectory = currentDirectory.replace("\"", "");
            currentFileName = currentFileName.replace("\"", "");
            currentFile = currentFile.replace("\"", "");
            String testDirectory = currentDirectory+"/"+currentFileName;
            if (nombreArchivo.equalsIgnoreCase(currentFile)){
                System.out.println("Contenido de "+ currentFileName+": \n" + currentFileContent);
                encontrado = true;
            }
        }
        if (!encontrado)
            System.out.println("Error: El archivo no existe.");
    }
    
    
    public void openFile(JSON json, String path, String currentDirectory){
        json.AbrirJson(json.getJsonContent().toString(), path, currentDirectory, true);
    }
    
    public void closeFile(JSON json, String path, String currentDirectory){
        json.AbrirJson(json.getJsonContent().toString(), path, currentDirectory, false);
    }
    
    public void viewFilesOpen(JSON json){
        String jsonString = json.getJsonContent().toString();
        
        int archivosIndex = jsonString.indexOf("\"archivos\":");
        int archivosStartIndex = jsonString.indexOf("[", archivosIndex) + 1;
        int archivosEndIndex = jsonString.indexOf("]", archivosIndex);
        String archivosContent = jsonString.substring(archivosStartIndex, archivosEndIndex);
        String[] archivos = archivosContent.split("\\},");
        boolean encontrado = false;
        int abiertosCount = 0;
        
        for (int i = 0; i < archivos.length; i++) {
            String archivo = archivos[i];
            if (!archivo.contains("}"))
                archivo = archivo + "}";
            String currentFile = json.parseAttribute(archivo, "path").trim();
            String currentFileName = json.parseAttribute(archivo, "nombre").trim();
            String currentFileContent = json.parseAttribute(archivo, "contenido").trim();
            String currentFileStatus = json.parseAttribute(archivo, "abierto").trim();
            currentFileName = currentFileName.replace("\"", "");
            currentFile = currentFile.replace("\"", "");
            currentFileStatus = currentFileStatus.replace("\"", "");
            if (currentFileStatus.equalsIgnoreCase("true")){
                encontrado = true;
                abiertosCount = abiertosCount + 1;
            }
        }
        if (!encontrado)
            System.out.println("No hay archivos abiertos actualmente.");
        System.out.println("Archivos abiertos: "+abiertosCount);
    }
    
    public void infoFS(JSON json){
        String jsonString = json.getJsonContent().toString();
        
        String nombreFS = json.parseAttribute(jsonString, "nombreDisco").trim();
        String tamanoFS = json.parseAttribute(jsonString, "tamano").trim();
        String tamanoUsado = json.parseAttribute(jsonString, "tamanoUsado").trim();
        String tamanoDisponible = json.parseAttribute(jsonString, "tamanoDisponible").trim();
        String unidadTamano = json.parseAttribute(jsonString, "unidad").trim();
        
        nombreFS = nombreFS.replace("\"", "");
        tamanoFS = tamanoFS.replace("\"", "");
        tamanoUsado = tamanoUsado.replace("\"", "");
        tamanoDisponible = tamanoDisponible.replace("\"", "");
        unidadTamano = unidadTamano.replace("\"", "");
        
        System.out.println("Nombre del FileSystem: "+nombreFS);
        System.out.println("Tamano total: "+tamanoFS+" kb");
        System.out.println("Espacio utilizado: "+tamanoUsado+" kb");
        System.out.println("Disponible: "+tamanoDisponible+" kb");
        System.out.println("Unidad usada para los tamanos del disco: "+unidadTamano);
    }
    
}
