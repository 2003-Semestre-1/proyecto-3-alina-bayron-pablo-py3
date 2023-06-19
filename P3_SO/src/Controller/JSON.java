package Controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author bayron
 * @author alina
 */
public class JSON {
    private String filePath;
    private StringBuilder jsonContent;

    public JSON() {
        this.jsonContent = new StringBuilder();
        jsonContent.append("{");
    }
    
    public void agregarUsuario(String jsonString, String nuevoUsuario){
        int usuariosIndex = jsonString.indexOf("\"usuarios\":");
        int usuariosStartIndex = jsonString.indexOf("[", usuariosIndex) + 1;
        int usuariosEndIndex = jsonString.indexOf("]", usuariosIndex);
        String modifiedJsonString = jsonString.substring(0, usuariosStartIndex)
                + nuevoUsuario + "," + jsonString.substring(usuariosStartIndex, usuariosEndIndex)
                + jsonString.substring(usuariosEndIndex);
        jsonContent = new StringBuilder(modifiedJsonString);
    }
    
    public void agregarGrupo(String jsonString, String nuevoGrupo){
        int gruposIndex = jsonString.indexOf("\"grupos\":");
        int gruposStartIndex = jsonString.indexOf("[", gruposIndex) + 1;
        int gruposEndIndex = jsonString.indexOf("]", gruposIndex);
        String modifiedJsonString = jsonString.substring(0, gruposStartIndex)
                + nuevoGrupo + "," + jsonString.substring(gruposStartIndex, gruposEndIndex)
                + jsonString.substring(gruposEndIndex);
        jsonContent = new StringBuilder(modifiedJsonString);
    }
    
    public void agregarBloque(String jsonString, String nuevoBloque){
        int bloquesIndex = jsonString.indexOf("\"bloques\":");
        int bloquesStartIndex = jsonString.indexOf("[", bloquesIndex) + 1;
        int bloquesEndIndex = jsonString.indexOf("]", bloquesIndex);
        String modifiedJsonString = jsonString.substring(0, bloquesStartIndex)
                + nuevoBloque + "," + jsonString.substring(bloquesStartIndex, bloquesEndIndex)
                + jsonString.substring(bloquesEndIndex);
        jsonContent = new StringBuilder(modifiedJsonString);
    }
    
    public void agregarDirectorio(String jsonString, String path){
        int directoriosIndex = jsonString.indexOf("\"directorios\":");
        int directoriosStartIndex = jsonString.indexOf("[", directoriosIndex) + 1;
        int directoriosEndIndex = jsonString.indexOf("]", directoriosIndex);
        String modifiedJsonString = jsonString.substring(0, directoriosStartIndex)
                + path + "," + jsonString.substring(directoriosStartIndex, directoriosEndIndex)
                + jsonString.substring(directoriosEndIndex);
        jsonContent = new StringBuilder(modifiedJsonString);
    }
     public void AgregarArchivo(String jsonString, String archivoObject){
        int archivoIndex = jsonString.indexOf("\"archivos\":");
        int archivosStartIndex = jsonString.indexOf("[", archivoIndex) + 1;
        int archivosEndIndex = jsonString.indexOf("]", archivoIndex);
        String modifiedJsonString = jsonString.substring(0, archivosStartIndex)
                + archivoObject + "," + jsonString.substring(archivosStartIndex, archivosEndIndex)
                + jsonString.substring(archivosEndIndex);
        jsonContent = new StringBuilder(modifiedJsonString);
        this.EscribirArchivo();
    }
     
     public void agregarAtributo(String key, Object value) {
        jsonContent.append("\"").append(key).append("\":");
        if (value instanceof String) {
            jsonContent.append("\"").append(value).append("\"");
        } else {
            jsonContent.append(value);
        }
        jsonContent.append(",");
    }

    public void agregarObjeto(String key, String json) {
        if (json.startsWith("[")) {
            jsonContent.append("\"").append(key).append("\":").append(json).append(",");
        } else {
            jsonContent.append("\"").append(key).append("\":").append(json).append(",");
        }
    }
    
    public void AbrirArchivo(String jsonString, String archivoPath, String currentDirectory, boolean openFile) {
        int archivosIndex = jsonString.indexOf("\"archivos\":");
        int archivosStartIndex = jsonString.indexOf("[", archivosIndex) + 1;
        int archivosEndIndex = jsonString.indexOf("]", archivosIndex);
        String archivosContent = jsonString.substring(archivosStartIndex, archivosEndIndex);
        if ( (!archivosContent.contains(archivoPath)) ){
            System.out.println("Error: El archivo no existe.");
            return;
        }
        String[] archivos = archivosContent.split("\\},");
        StringBuilder updatedArchivosContent = new StringBuilder();
        String archivoName = archivoPath;
        if (archivoName.contains("/")){
            int lastSlashIndex = archivoName.lastIndexOf("/")+1;
            archivoName = archivoName.substring(lastSlashIndex);
        }
        if (!archivoPath.contains("/")){ // si es un relative path, le pongo mi current directory para formar el path completo al archivo
            archivoPath = currentDirectory+"/"+archivoPath;
        }
        String newStatus = "";
        if (openFile)
            newStatus = "\"true\"";
        else
            newStatus = "\"false\"";
        for (int i = 0; i < archivos.length; i++) {
            String archivo = archivos[i];
            String oldStatus = this.parseAttribute(archivo, "abierto");
            if (archivo.contains(archivoPath)) 
                archivo = archivo.replace(oldStatus, newStatus);
            updatedArchivosContent.append(archivo);
            if (i < archivos.length - 1) {
                    updatedArchivosContent.append("},");
            }
        }
        String modifiedJsonString = jsonString.substring(0, archivosStartIndex)
        + updatedArchivosContent.toString() + jsonString.substring(archivosEndIndex);
        jsonContent = new StringBuilder(modifiedJsonString);
        EscribirArchivo();
        if (openFile)
            System.out.println("Se abrió el archivo " + archivoName + ".");
        else
            System.out.println("Se cerró el archivo " + archivoName + ".");
    }
    
    public void AbrirJson(String jsonString, String pathToOpen, String currentDirectory, boolean openFile){
        String archivoPath = currentDirectory+"/"+pathToOpen;
        if ( pathToOpen.contains(".") && (pathToOpen.contains("\\") || pathToOpen.contains("/")) ){ // es un archivo y es absolute path
            this.AbrirArchivo(jsonString,archivoPath, currentDirectory, openFile);
        }
        else if ( pathToOpen.contains(".") && !(pathToOpen.contains("\\") || pathToOpen.contains("/")) ){ // es un archivo y es relative path
            this.AbrirArchivo(jsonString,archivoPath, currentDirectory, openFile);
        }
        else if ( !pathToOpen.contains(".") && (pathToOpen.contains("\\") || pathToOpen.contains("/")) ){ // es un directorio y es absolute path
            System.out.println("Error: No se pueden abrir o cerrar directorios, solamente se pueden abrir o cerrar archivos.");
        }
        else if ( !pathToOpen.contains(".") && !(pathToOpen.contains("\\") || pathToOpen.contains("/")) ){ // es un directorio y es relative path
            System.out.println("Error: No se pueden abrir o cerrar directorios, solamente se pueden abrir o cerrar archivos.");
        }
    }
    
    
    public String LeerArchivo(String filePath) {
        StringBuilder Contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Contenido.append(line);
        }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            return "";
        }
        this.jsonContent = Contenido;
        return Contenido.toString();
    }

    public void EscribirArchivo() {
        if (jsonContent.charAt(jsonContent.length() - 1) == ',') {
            jsonContent.deleteCharAt(jsonContent.length() - 1);
        }
        char lastChar = jsonContent.charAt(jsonContent.length() - 1);
        if (lastChar == '}')
            ;
        else
            jsonContent.append("}");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonContent.toString());
            //System.out.println("Se escribió correctamente al archivo: " + filePath);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }

    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public StringBuilder getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(StringBuilder jsonContent) {
        this.jsonContent = jsonContent;
    }
    
    public String parseAttribute(String jsonContent, String attributeName) {
        String pattern = "\"" + attributeName + "\":\\[?(.*?)\\]?(?:,|\\}|\\])";
        java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = compiledPattern.matcher(jsonContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    public String parseArray(String jsonContent, String attributeName, String stopAttributeName) {
        jsonContent = jsonContent.replace("\\", "");
        int start = jsonContent.indexOf("\"" + attributeName + "\"") + 11;
        int end = jsonContent.lastIndexOf("]", jsonContent.indexOf("\"" + stopAttributeName + "\""));
        String usuariosString = jsonContent.substring(start, end)+']';
        return usuariosString;
    }
    
    public String ActualizarAtributo(String jsonString, String attributeName, String attributeValue) {
        StringBuilder updatedJson = new StringBuilder();
        int attributeIndex = jsonString.indexOf("\"" + attributeName + "\"");
        if (attributeIndex != -1) {
            int valueStartIndex = jsonString.indexOf(":", attributeIndex + attributeName.length() + 1);
            if (valueStartIndex != -1) {
                int valueEndIndex = jsonString.indexOf(",", valueStartIndex + 1);
                    if (valueEndIndex == -1) {
                        valueEndIndex = jsonString.indexOf("}", valueStartIndex + 1);
                    }
                if (valueEndIndex != -1) {
                    updatedJson.append(jsonString, 0, valueStartIndex + 1);
                    updatedJson.append(attributeValue);
                    updatedJson.append(jsonString, valueEndIndex, jsonString.length());
                }
            }
        }
        this.jsonContent = updatedJson;
        return updatedJson.toString();
    }
    
    public String ActualizarArrayAtributo(String jsonString, String arrayName, String attributeName, String attributeValue, String objectNombre) {
    StringBuilder updatedJson = new StringBuilder();
    int startIndex = jsonString.indexOf(arrayName);
    if (startIndex != -1) {
        int arrayStartIndex = jsonString.indexOf("[", startIndex + arrayName.length());
        int arrayEndIndex = jsonString.indexOf("]", arrayStartIndex);
        if (arrayStartIndex != -1 && arrayEndIndex != -1) {
            updatedJson.append(jsonString, 0, arrayStartIndex + 1);
            String arrayContent = jsonString.substring(arrayStartIndex + 1, arrayEndIndex);
            String[] objects = arrayContent.split("\\},");
            boolean objectFound;
            for (int i = 0; i < objects.length; i++) {
                String object = objects[i];
                if (object.contains(objectNombre)) {
                    objectFound = true;
                    if (object.endsWith("}")) {
                        object += ",";
                    }
                    String updatedObject = ActualizarObjeto(object, attributeName, attributeValue);
                    updatedJson.append(updatedObject);
                } else {
                    updatedJson.append(object);
                }
                if (i < objects.length - 1) {
                    updatedJson.append("},");
                }
            }
            updatedJson.append("]");
            if (arrayEndIndex < jsonString.length() - 1) {
                updatedJson.append(jsonString, arrayEndIndex + 1, jsonString.length());
            }
            } else {
                return jsonString;
            }
        } else {
            return jsonString;
        }
        this.jsonContent = updatedJson;
        return updatedJson.toString();
    }

    private String ActualizarObjeto(String object, String attributeName, String attributeValue) {
        int attributeIndex = object.indexOf("\"" + attributeName + "\"");
        if (attributeIndex != -1) {
            int valueStartIndex = object.indexOf(":", attributeIndex + attributeName.length() + 1);
            valueStartIndex = valueStartIndex + 1;
            if (attributeName.equalsIgnoreCase("usuarios"))
                valueStartIndex = valueStartIndex + 1;
            if (attributeName.equalsIgnoreCase("password"))
                valueStartIndex = valueStartIndex + 1;
            if (valueStartIndex != -1) {
                int valueEndIndex = object.indexOf("\"", valueStartIndex + 1);
                if (valueEndIndex != -1) {
                    StringBuilder updatedObject = new StringBuilder(object);
                    updatedObject.replace(valueStartIndex + 1, valueEndIndex, attributeValue);
                    return updatedObject.toString();
                }
            }
        }
        return object;
    }
    
}
