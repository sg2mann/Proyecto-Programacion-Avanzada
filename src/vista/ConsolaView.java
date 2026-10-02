
package vista;

import excepciones.OrdenNoEncontradaException;
import excepciones.StockInsuficienteException;
import modelo.Cliente;
import modelo.Componente;
import modelo.Computador;
import modelo.OrdenTrabajo;
import sistemaserviciotecnico.SistemaServicioTecnico;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * @archivo : ConsolaView.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Maneja la interfaz de usuario por terminal, mostrando los menús y capturando las entradas por teclado
 * @author : Sebastian Riveros, Cristobal Sazo, Simón Guzmán
 * @Lenguaje : Java
 * @Fecha : 01-09-26
 */

public class ConsolaView {
    private SistemaServicioTecnico sistema;
    private Scanner scanner;

    public ConsolaView(SistemaServicioTecnico sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n========== SISTEMA DE SERVICIO TÉCNICO ==========");
            System.out.println("--- GESTIÓN DE ÓRDENES (Colección 1) ---");
            System.out.println("1. Registrar nueva orden de trabajo");
            System.out.println("2. Listar todas las órdenes");
            System.out.println("3. Buscar orden (por ID)");
            System.out.println("4. Buscar órdenes por RUT de cliente");
            System.out.println("5. Modificar estado de una orden");
            System.out.println("6. Eliminar orden de trabajo");
            System.out.println("--- GESTIÓN DE PIEZAS / STOCK (Colección 2 Anidada) ---");
            System.out.println("7. Agregar repuesto a una orden");
            System.out.println("8. Listar repuestos de una orden");
            System.out.println("9. Eliminar repuesto de una orden");
            System.out.println("--- LÓGICA DE NEGOCIO (SIA-9) ---");
            System.out.println("10. Ver órdenes activas pendientes en taller");
            System.out.println("11. Nuevo repuesto (Inventario General)");
            System.out.println("12. Aumentar stock de repuesto");
            System.out.println("13. Eliminar repuesto (Inventario General)");
            System.out.println("14. Exportar inventario a Excel");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                opcion = -1;
            } catch (OrdenNoEncontradaException | StockInsuficienteException e) {
                // Manejo de excepciones personalizadas (SIA-12)
                System.out.println("Aviso del Sistema: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void ejecutarOpcion(int opcion) throws OrdenNoEncontradaException, StockInsuficienteException {
        switch (opcion) {
            case 1:
                registrarOrden();
                break;
            case 2:
                listarOrdenes();
                break;
            case 3:
                buscarPorId();
                break;
            case 4:
                buscarPorRut();
                break;
            case 5:
                modificarEstado();
                break;
            case 6:
                eliminarOrden();
                break;
            case 7:
                agregarRepuestoAOrden();
                break;
            case 8:
                listarRepuestosDeOrden();
                break;
            case 9:
                eliminarRepuestoDeOrden();
                break;
            case 10:
                listarOrdenesActivas();
                break;
            case 11:
                nuevoRepuesto();
                break;
            case 12:
                aumentarStock();
                break;
            case 13:
                eliminarRepuestoInventario();
                break;
            case 14:
                exportarExcel();
                break;
            case 0:
                System.out.println("Guardando información en el archivo CSV...");
                sistema.guardarDatosSistema();
                System.out.println("Datos guardados exitosamente. Cerrando sesión en consola...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private void registrarOrden() {
        System.out.println("\n--- Registro de Orden de Trabajo ---");
        System.out.print("Ingrese ID numérico de la orden (ej: 103): ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("RUT del Cliente: ");
        String rut = scanner.nextLine();
        System.out.print("Nombre del Cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Teléfono del Cliente: ");
        int telefono = Integer.parseInt(scanner.nextLine());
        System.out.print("Email del Cliente: ");
        String email = scanner.nextLine();
        Cliente cliente = new Cliente(rut, nombre, telefono, email);

        System.out.print("Marca del Computador: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo del Computador: ");
        String modelo = scanner.nextLine();
        System.out.print("Diagnóstico / Falla reportada: ");
        String problema = scanner.nextLine();
        System.out.print("Año del Computador: ");
        short anio = Short.parseShort(scanner.nextLine());
        Computador computador = new Computador(marca, modelo, problema, anio);

        OrdenTrabajo orden = new OrdenTrabajo(id, LocalDate.now().toString(), problema, "", "RECIBIDO", cliente, computador);
        sistema.agregarOrden(orden);
        System.out.println("¡Orden registrada exitosamente! Fecha estimada calculada: " + orden.getFechaEntregaEstimada());
    }

    private void listarOrdenes() {
        System.out.println("\n--- Listado Total de Órdenes ---");
        List<OrdenTrabajo> lista = sistema.listarOrdenes();
        if (lista.isEmpty()) {
            System.out.println("No hay órdenes registradas.");
            return;
        }
        
        // Cabecera de la tabla
        System.out.printf("%-5s | %-12s | %-12s | %-20s | %-15s | %-12s\n", 
                "ID", "Fecha", "RUT Cliente", "Equipo", "Estado", "Entrega");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        // Filas de la tabla
        for (OrdenTrabajo ot : lista) {
            String equipo = ot.getComputadorMalo().getMarca() + " " + ot.getComputadorMalo().getModelo();
            // Truncar el nombre del equipo si es muy largo para no desarmar la tabla
            if (equipo.length() > 20) {
                equipo = equipo.substring(0, 17) + "...";
            }
            
            System.out.printf("%-5d | %-12s | %-12s | %-20s | %-15s | %-12s\n",
                    ot.getIdOrden(),
                    ot.getFechaRecepcion(),
                    ot.getClienteAtendido().getRut(),
                    equipo,
                    ot.getEstado(),
                    ot.getFechaEntregaEstimada());
        }
        pausarConsola();
    }

    private void buscarPorId() throws OrdenNoEncontradaException {
        System.out.print("\nIngrese ID de la orden a buscar: ");
        int id = Integer.parseInt(scanner.nextLine());
        OrdenTrabajo ot = sistema.buscarOrden(id);
        System.out.println("Orden encontrada:\n" + ot);
        pausarConsola();
    }

    private void buscarPorRut() {
        System.out.print("\nIngrese RUT del cliente: ");
        String rut = scanner.nextLine();
        List<OrdenTrabajo> lista = sistema.buscarOrden(rut);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron órdenes para el RUT ingresado.");
        } else {
            System.out.println("Órdenes asociadas (" + lista.size() + "):");
            for (OrdenTrabajo ot : lista) {
                System.out.println(ot);
            }
        }
        pausarConsola();
    }

    private void modificarEstado() throws OrdenNoEncontradaException {
        System.out.print("\nIngrese ID de la orden a modificar: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nuevo estado (RECIBIDO / EN_REPARACION / LISTO / ENTREGADO): ");
        String estado = scanner.nextLine().toUpperCase();
        sistema.actualizarEstadoOrden(id, estado);
        System.out.println("Estado actualizado correctamente.");
    }

    private void eliminarOrden() throws OrdenNoEncontradaException {
        System.out.print("\nIngrese ID de la orden a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        sistema.eliminarOrden(id);
        System.out.println("Orden eliminada del sistema.");
    }

    private void agregarRepuestoAOrden() throws OrdenNoEncontradaException, StockInsuficienteException {
        System.out.print("\nIngrese ID de la orden: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Catálogo de repuestos disponibles en taller:");
        for (Componente c : sistema.getInventarioStock().values()) {
            System.out.println("  " + c);
        }

        System.out.print("Ingrese el código del repuesto a asignar (ej: RAM-16, SSD-1TB): ");
        String codPieza = scanner.nextLine();
        System.out.print("Cantidad requerida: ");
        int cant = Integer.parseInt(scanner.nextLine());

        sistema.agregarComponenteAOrden(id, codPieza, cant);
        System.out.println("Pieza asignada a la orden con éxito. Fecha de entrega actualizada.");
    }

    private void listarRepuestosDeOrden() throws OrdenNoEncontradaException {
        System.out.print("\nIngrese ID de la orden: ");
        int id = Integer.parseInt(scanner.nextLine());
        OrdenTrabajo ot = sistema.buscarOrden(id);
        List<Componente> piezas = ot.getComponentesRequeridos();
        if (piezas.isEmpty()) {
            System.out.println("Esta orden no tiene piezas ni repuestos asignados.");
        } else {
            System.out.println("Repuestos asociados a la orden " + id + ":");
            for (Componente c : piezas) {
                System.out.println(" - " + c);
            }
        }
        pausarConsola();
    }

    private void eliminarRepuestoDeOrden() throws OrdenNoEncontradaException {
        System.out.print("\nIngrese ID de la orden: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el código del repuesto a quitar: ");
        String cod = scanner.nextLine();
        sistema.eliminarComponenteDeOrden(id, cod);
        System.out.println("Componente eliminado de la orden.");
    }

    private void listarOrdenesActivas() {
        System.out.println("\n--- Órdenes Activas (Demanda actual en taller) ---");
        List<OrdenTrabajo> activas = sistema.filtrarOrdenesActivas();
        if (activas.isEmpty()) {
            System.out.println("No hay órdenes activas en este momento.");
            return;
        }

        // Cabecera de la tabla
        System.out.printf("%-5s | %-12s | %-12s | %-20s | %-15s | %-12s\n", 
                "ID", "Fecha", "RUT Cliente", "Equipo", "Estado", "Entrega");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        // Filas de la tabla
        for (OrdenTrabajo ot : activas) {
            String equipo = ot.getComputadorMalo().getMarca() + " " + ot.getComputadorMalo().getModelo();
            if (equipo.length() > 20) {
                equipo = equipo.substring(0, 17) + "...";
            }
            
            System.out.printf("%-5d | %-12s | %-12s | %-20s | %-15s | %-12s\n",
                    ot.getIdOrden(),
                    ot.getFechaRecepcion(),
                    ot.getClienteAtendido().getRut(),
                    equipo,
                    ot.getEstado(),
                    ot.getFechaEntregaEstimada());
        }
        pausarConsola();
    }
    
    private void nuevoRepuesto() {
        System.out.print("\nIngrese código del nuevo repuesto: ");
        String codigo = scanner.nextLine().trim();
        if (sistema.getInventarioStock().containsKey(codigo)) {
            System.out.println("Error: El código ya existe en el inventario.");
            return;
        }
        System.out.print("Nombre/Descripción: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Precio unitario: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock inicial: ");
        int stock = Integer.parseInt(scanner.nextLine());

        Componente nuevo = new Componente(codigo, nombre, precio, stock);
        sistema.getInventarioStock().put(codigo, nuevo);
        System.out.println("Repuesto agregado al inventario exitosamente.");
    }

    private void aumentarStock() {
        System.out.print("\nIngrese código del repuesto a modificar: ");
        String codigo = scanner.nextLine().trim();
        Componente comp = sistema.getInventarioStock().get(codigo);
        if (comp == null) {
            System.out.println("Error: Repuesto no encontrado en el inventario.");
            return;
        }
        System.out.print("Cantidad a sumar al stock actual (" + comp.getStock() + "): ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        if (cantidad < 0) {
            System.out.println("Error: No se pueden ingresar valores negativos.");
            return;
        }
        comp.setStock(comp.getStock() + cantidad);
        System.out.println("Stock actualizado. Nuevo stock: " + comp.getStock());
    }

    private void eliminarRepuestoInventario() {
        System.out.print("\nIngrese código del repuesto a eliminar del sistema: ");
        String codigo = scanner.nextLine().trim();
        if (sistema.getInventarioStock().remove(codigo) != null) {
            System.out.println("Repuesto eliminado del inventario general.");
        } else {
            System.out.println("Error: Repuesto no encontrado.");
        }
    }

    private void exportarExcel() {
        if (sistema.getInventarioStock().isEmpty()) {
            System.out.println("El inventario está vacío. No hay datos para exportar.");
            return;
        }
        System.out.print("\nIngrese el nombre del archivo Excel a generar: ");
        String nombreArchivo = scanner.nextLine().trim();
        try {
            String ruta = persistencia.GestorExcel.exportarInventario(sistema.getInventarioStock(), nombreArchivo);
            System.out.println("Archivo Excel generado exitosamente en:\n" + ruta);
        } catch (Exception e) {
            System.out.println("Error al generar Excel: " + e.getMessage());
        }
    }
    
    private void pausarConsola() {
    System.out.println("\nPresione ENTER para continuar y volver al menú...");
    scanner.nextLine();
    }
}