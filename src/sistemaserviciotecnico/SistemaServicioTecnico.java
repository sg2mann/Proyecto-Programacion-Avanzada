/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemaserviciotecnico;

import excepciones.OrdenNoEncontradaException;
import excepciones.StockInsuficienteException;
import modelo.Cliente;
import modelo.Componente;
import modelo.Computador;
import modelo.OrdenTrabajo;
import persistencia.GestorArchivosCSV;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author simon
 */
public class SistemaServicioTecnico {

    private Map<Integer, OrdenTrabajo> ordenes;
    
    private Map<String, Componente> inventarioStock;
    
    public SistemaServicioTecnico() {
        this.ordenes = new HashMap<>();
        this.inventarioStock = new HashMap<>();
        
        GestorArchivosCSV.cargarInventario(this.inventarioStock);
        GestorArchivosCSV.cargarOrdenes(this.ordenes);
        
        if (this.inventarioStock.isEmpty() && this.ordenes.isEmpty()){
            cargarDatosIniciales(); // SIA-3: Datos de prueba iniciales
        }
    }
    
    private void cargarDatosIniciales() {
        // Stock inicial de piezas en taller
        inventarioStock.put("RAM-16", new Componente("RAM-16", "Memoria RAM DDR4 16GB", 45000.0, 5));
        inventarioStock.put("SSD-1TB", new Componente("SSD-1TB", "SSD NVMe M.2 1TB", 65000.0, 3));
        inventarioStock.put("PSU-650", new Componente("PSU-650", "Fuente de Poder 650W 80+", 55000.0, 0)); // Sin stock a propósito

        // Clientes y equipos de prueba
        Cliente c1 = new Cliente("12345678-9", "Juan Perez", 912345678, "juan@email.com");
        Computador comp1 = new Computador("Asus", "TUF Gaming", "No enciende tras corte de luz", (short) 2022);
        OrdenTrabajo ot1 = new OrdenTrabajo(101, LocalDate.now().toString(), "Falla en fuente/RAM", "", "RECIBIDO", c1, comp1);
        ot1.agregarComponente(new Componente("RAM-16", "Memoria RAM DDR4 16GB", 45000.0, 1));
        calcularFechaEstimadaEntrega(ot1);

        Cliente c2 = new Cliente("98765432-1", "Maria Lopez", 987654321, "maria@email.com");
        Computador comp2 = new Computador("Lenovo", "ThinkPad L14", "Pantalla azul intermitente", (short) 2021);
        OrdenTrabajo ot2 = new OrdenTrabajo(102, LocalDate.now().toString(), "Revisión de placa", "", "RECIBIDO", c2, comp2);
        calcularFechaEstimadaEntrega(ot2);

        ordenes.put(ot1.getIdOrden(), ot1);
        ordenes.put(ot2.getIdOrden(), ot2);
    }
    
    // SIA-7 SIA-8
    public void agregarOrden(OrdenTrabajo orden) {
        calcularFechaEstimadaEntrega(orden);
        ordenes.put(orden.getIdOrden(), orden);
    }

    public List<OrdenTrabajo> listarOrdenes() {
        return new ArrayList<>(ordenes.values());
    }

    // SIA-5 (Clase 2): Sobrecarga Variante 1 (por ID numérico de orden)
    public OrdenTrabajo buscarOrden(int idOrden) throws OrdenNoEncontradaException {
        OrdenTrabajo ot = ordenes.get(idOrden);
        if (ot == null) {
            throw new OrdenNoEncontradaException("La orden N° " + idOrden + " no existe.");
        }
        return ot;
    }

    // SIA-5 (Clase 2): Sobrecarga Variante 2 (por RUT del cliente)
    public List<OrdenTrabajo> buscarOrden(String rutCliente) {
        List<OrdenTrabajo> resultados = new ArrayList<>();
        for (OrdenTrabajo ot : ordenes.values()) {
            if (ot.getClienteAtendido().getRut().equalsIgnoreCase(rutCliente)) {
                resultados.add(ot);
            }
        }
        return resultados;
    }

    public boolean eliminarOrden(int idOrden) throws OrdenNoEncontradaException {
        if (!ordenes.containsKey(idOrden)) {
            throw new OrdenNoEncontradaException("No se puede eliminar: orden N° " + idOrden + " no encontrada.");
        }
        ordenes.remove(idOrden);
        return true;
    }

    public void actualizarEstadoOrden(int idOrden, String nuevoEstado) throws OrdenNoEncontradaException {
        OrdenTrabajo ot = buscarOrden(idOrden);
        ot.setEstado(nuevoEstado);
    }
    
    public void agregarComponenteAOrden(int idOrden, String codigoComp, int cantidad) 
            throws OrdenNoEncontradaException, StockInsuficienteException {
        OrdenTrabajo ot = buscarOrden(idOrden);
        Componente compStock = inventarioStock.get(codigoComp);

        if (compStock == null) {
            throw new StockInsuficienteException("El componente " + codigoComp + " no está registrado en el inventario.");
        }
        if (compStock.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente para " + compStock.getNombre() + 
                    ". Disponibles: " + compStock.getStock() + ", Solicitados: " + cantidad);
        }

        // Descontar stock y asociar copia a la orden de trabajo
        compStock.setStock(compStock.getStock() - cantidad);
        ot.agregarComponente(new Componente(compStock.getCodigo(), compStock.getNombre(), compStock.getPrecio(), cantidad));
        
        // Recalcular entrega
        calcularFechaEstimadaEntrega(ot);
    }

    public void eliminarComponenteDeOrden(int idOrden, String codigoComp) throws OrdenNoEncontradaException {
        OrdenTrabajo ot = buscarOrden(idOrden);
        Componente compEnOrden = ot.buscarComponente(codigoComp);
        
        if (compEnOrden != null) {
            // Restaurar el stock al inventario principal
            Componente compInventario = inventarioStock.get(codigoComp);
            if (compInventario != null) {
                compInventario.setStock(compInventario.getStock() + compEnOrden.getStock());
            }
            // Eliminar el componente de la orden de trabajo
            ot.eliminarComponente(codigoComp);
        }
    }
    
    public void calcularFechaEstimadaEntrega(OrdenTrabajo orden) {
        int diasEstimados = 2; // Tiempo base mínimo de revisión

        // Factor 1: Demanda (cada 2 órdenes pendientes agregan 1 día de cola)
        long ordenesPendientes = ordenes.values().stream()
                .filter(o -> !o.getEstado().equalsIgnoreCase("ENTREGADO") && !o.getEstado().equalsIgnoreCase("LISTO"))
                .count();
        diasEstimados += (int) (ordenesPendientes / 2);

        // Factor 2: Complejidad por repuestos requeridos
        if (!orden.getComponentesRequeridos().isEmpty()) {
            diasEstimados += 1;
        }

        orden.setFechaEntregaEstimada(LocalDate.now().plusDays(diasEstimados).toString());
    }

    // Filtro de negocio: obtener solo órdenes activas
    public List<OrdenTrabajo> filtrarOrdenesActivas() {
        List<OrdenTrabajo> activas = new ArrayList<>();
        for (OrdenTrabajo ot : ordenes.values()) {
            if (!ot.getEstado().equalsIgnoreCase("ENTREGADO")) {
                activas.add(ot);
            }
        }
        return activas;
    }

    public Map<String, Componente> getInventarioStock() {
        return inventarioStock;
    }
    
    public void guardarDatosSistema() {
        GestorArchivosCSV.guardarInventario(this.inventarioStock);
        GestorArchivosCSV.guardarOrdenes(this.ordenes);
    }
}