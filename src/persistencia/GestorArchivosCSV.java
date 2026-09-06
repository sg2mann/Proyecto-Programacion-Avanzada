/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import modelo.Componente;
import java.io.*;
import java.util.Map;

/**
 *
 * @author seba
 */
public class GestorArchivosCSV {
    private static final String ARCHIVO_INVENTARIO = "inventario.csv";
    
    // Método para guardar los datos al salir (escritura)
    public static void guardarInventario(Map<String, Componente> inventario) {
        try (PrintWriter escritor = new PrintWriter (new FileWriter(ARCHIVO_INVENTARIO))){
            for (Componente comp : inventario.values()){
                // Se escriben los dato separados por punto y coma (formato CSV)
                escritor.println(comp.getCodigo() + ";" + comp.getNombre() + ";" +
                                 comp.getPrecio() + ";" + comp.getStock());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de inventario: " + e.getMessage())
        }
    }
    
    // Método para cargar los datos al iniciar (Lectura)
    public static void cargarInventario(Map<String, Componente> inventario) {
        File archivo = new File(ARCHIVO_INVENTARIO);
        // si el archivo no existe, no hace nada
        if (!archivo.exists()){
            return;
        }
        
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Se separa lla línea de texto usando ";"
                String[] datos = linea.split(";");
                if (datos.length == 4) {
                    String codigo = datos[0];
                    String nombre = datos[1];
                    double precio = Double.parseDouble(datos[2]);
                    int stock = Integer.parseInt(datos[3]);
                    
                    // Se reconstruye el objeto y se mete al mapa
                    Componente c = new Componente(codigo, nombre, precio, stock)
                            inventario.put(codigo, c)
                }
            }
        } catch (IOException e){
            System.out.println("Error al leer el archivo de inventario: " + e.getMessage());
        }
    }
}
