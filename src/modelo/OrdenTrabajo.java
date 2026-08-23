/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author simon
 */
public class OrdenTrabajo 
{
    private int idOrden;
    private String fechaRecepcion;
    private String analisisPrevio;
    private String fechaEntregaEstimada;
    private String estado;
    private Cliente clienteAtendido;
    private Computador computadorMalo;
    private List<Componente> componentesRequeridos;

    public OrdenTrabajo(int idOrden, String fechaRecepcion, String analisisPrevio,
    String fechaEntregaEstimada, String estado, Cliente clienteAtendido,
    Computador computadorMalo)
    {
        this.idOrden = idOrden;
        this.fechaRecepcion = fechaRecepcion;
        this.analisisPrevio = analisisPrevio;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.estado = estado;
        this.clienteAtendido = clienteAtendido;
        this.computadorMalo = computadorMalo;
        this.componentesRequeridos = new ArrayList<>();
    }
    
    // SIA-5
    public void agregarComponente(Componente componente) {
        this.componentesRequeridos.add(componente);
    }
    
    public void agregarComponente(String codigo, String nombre, double precio, int stock) {
        Componente nuevo = new Componente(codigo, nombre, precio, stock);
        this.componentesRequeridos.add(nuevo);
    }
    
    public boolean eliminarComponente(String codigoComp) {
        for (int i = 0; i < this.componentesRequeridos.size(); i++) {
            if (this.componentesRequeridos.get(i).getCodigo().equalsIgnoreCase(codigoComp)) {
                this.componentesRequeridos.remove(i);
                return true;
            }
        }
        return false;
    }

    public Componente buscarComponente(String codigoComp) {
        for (Componente c : this.componentesRequeridos) {
            if (c.getCodigo().equalsIgnoreCase(codigoComp)) {
                return c;
            }
        }
        return null;
    }
    
    // getter y setters
    
    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getAnalisisPrevio() {
        return analisisPrevio;
    }

    public void setAnalisisPrevio(String analisisPrevio) {
        this.analisisPrevio = analisisPrevio;
    }

    public String getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(String fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getClienteAtendido() {
        return clienteAtendido;
    }

    public void setClienteAtendido(Cliente clienteAtendido) {
        this.clienteAtendido = clienteAtendido;
    }

    public Computador getComputadorMalo() {
        return computadorMalo;
    }

    public void setComputadorMalo(Computador computadorMalo) {
        this.computadorMalo = computadorMalo;
    }

    public List<Componente> getComponentesRequeridos() {
        return componentesRequeridos;
    }

    public void setComponentesRequeridos(List<Componente> componentesRequeridos) {
        this.componentesRequeridos = componentesRequeridos;
    }
    
    @Override
    public String toString() {
        return "=== Orden N°: " + idOrden + " [" + estado + "] ===" +
               "\nFecha Recepción: " + fechaRecepcion + " | Entrega Estimada: " + fechaEntregaEstimada +
               "\nAnálisis Previo: " + analisisPrevio +
               "\nCliente: " + clienteAtendido.getNombre() + " (" + clienteAtendido.getRut() + ")" +
               "\nEquipo: " + computadorMalo.toString() +
               "\nRepuestos requeridos (" + componentesRequeridos.size() + "): " + componentesRequeridos;
    }
}