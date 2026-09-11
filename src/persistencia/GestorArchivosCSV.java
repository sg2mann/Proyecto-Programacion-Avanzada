
package persistencia;

import modelo.Cliente;
import modelo.Computador;
import modelo.OrdenTrabajo;

import modelo.Componente;
import java.io.*;
import java.util.Map;

/**
 * @archivo : GestorArchivosCSV.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Maneja la lectura y escritura en archivos CSV para guardar y cargar los datos de las órdenes y el inventario
 * @author : Sebastian Riveros
 * @Lenguaje : Java
 * @Fecha : 05-09-26
 */

public class GestorArchivosCSV {
    private static final String ARCHIVO_INVENTARIO = "inventario.csv";
    private static final String ARCHIVO_ORDENES = "ordenes.csv";
    
    // Método para guardar los datos al salir (escritura)
    public static void guardarInventario(Map<String, Componente> inventario) {
        try (PrintWriter escritor = new PrintWriter (new FileWriter(ARCHIVO_INVENTARIO))){
            for (Componente comp : inventario.values()){
                // Se escriben los dato separados por punto y coma (formato CSV)
                escritor.println(comp.getCodigo() + ";" + comp.getNombre() + ";" +
                                 comp.getPrecio() + ";" + comp.getStock());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo de inventario: " + e.getMessage());
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
                    Componente c = new Componente(codigo, nombre, precio, stock);
                    inventario.put(codigo, c);
                }
            }
        } catch (IOException e){
            System.out.println("Error al leer el archivo de inventario: " + e.getMessage());
        }
    }
    
    // Método para guardar órdenes al salor
    public static void guardarOrdenes(Map<Integer, OrdenTrabajo> ordenes){
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_ORDENES))) {
            for (OrdenTrabajo ot : ordenes.values()) {
                StringBuilder repuestosStr = new StringBuilder();
                for (Componente c : ot.getComponentesRequeridos()) {
                    repuestosStr.append(c.getCodigo()).append(":").append(c.getStock()).append("-");
                }
                
                String linea = ot.getIdOrden() + ";" +
                               ot.getFechaRecepcion() + ";" +
                               ot.getAnalisisPrevio() + ";" +
                               ot.getFechaEntregaEstimada() + ";" +
                               ot.getEstado() + ";" +
                               ot.getClienteAtendido().getRut() + ";" +
                               ot.getClienteAtendido().getNombre() + ";" +
                               ot.getClienteAtendido().getNumeroTelefono() + ";" +
                               ot.getClienteAtendido().getCorreo() + ";" +
                               ot.getComputadorMalo().getMarca() + ";" +
                               ot.getComputadorMalo().getModelo() + ";" +
                               ot.getComputadorMalo().getDescripcionProblema() + ";" +
                               ot.getComputadorMalo().getAnioComprado() + ";" +
                               repuestosStr.toString(); // Agregamos la columna 14 con los repuestos
                
                escritor.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las órdenes: " + e.getMessage());
        }
    }
    
    // Método para cargar las órdenes al iniciar
    public static void cargarOrdenes(Map<Integer, OrdenTrabajo> ordenes) {
        File archivo = new File(ARCHIVO_ORDENES);
        if (!archivo.exists()) {
            return; 
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");
                
                if (datos.length >= 13) {
                    int id = Integer.parseInt(datos[0]);
                    String fecha = datos[1];
                    String analisis = datos[2];
                    String entrega = datos[3];
                    String estado = datos[4];
                    
                    Cliente cli = new Cliente(datos[5], datos[6], Integer.parseInt(datos[7]), datos[8]);
                    Computador pc = new Computador(datos[9], datos[10], datos[11], Short.parseShort(datos[12]));
                    
                    OrdenTrabajo ot = new OrdenTrabajo(id, fecha, analisis, entrega, estado, cli, pc);
                    
                    if (datos.length >= 14 && !datos[13].isEmpty()) {
                        String[] piezas = datos[13].split("-");
                        for (String p : piezas) {
                            if (p.contains(":")) {
                                String[] info = p.split(":");
                                String codigoPieza = info[0];
                                int cantidad = Integer.parseInt(info[1]);
                                
                                // Método sobrecargado (SIA-5)
                                ot.agregarComponente(codigoPieza, "Repuesto Asignado", 0.0, cantidad);
                            }
                        }
                    }
                    
                    ordenes.put(id, ot);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de órdenes: " + e.getMessage());
        }
    }
}
