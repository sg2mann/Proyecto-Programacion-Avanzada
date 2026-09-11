
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
        for (OrdenTrabajo ot : lista) {
            System.out.println("----------------------------------------");
            System.out.println(ot);
        }
    }

    private void buscarPorId() throws OrdenNoEncontradaException {
        System.out.print("\nIngrese ID de la orden a buscar: ");
        int id = Integer.parseInt(scanner.nextLine());
        OrdenTrabajo ot = sistema.buscarOrden(id);
        System.out.println("Orden encontrada:\n" + ot);
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
        for (OrdenTrabajo ot : activas) {
            System.out.println("ID " + ot.getIdOrden() + " | Cliente: " + ot.getClienteAtendido().getNombre() + " | Estado: " + ot.getEstado() + " | Entrega: " + ot.getFechaEntregaEstimada());
        }
    }
}