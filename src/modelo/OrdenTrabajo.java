/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

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
    }

    public int getIdOrden()
    {
        return idOrden;
    }

    public void setIdOrden(int idOrden)
    {
        this.idOrden = idOrden;
    }

    public String getFechaRecepcion()
    {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(String fechaRecepcion)
    {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getAnalisisPrevio()
    {
        return analisisPrevio;
    }

    public void setAnalisisPrevio(String analisisPrevio)
    {
        this.analisisPrevio = analisisPrevio;
    }
    
    public String getFechaEntregaEstimada()
    {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(String fechaEntregaEstimada)
    {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }
    
    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public Cliente getClienteAtendido()
    {
        return clienteAtendido;
    }

    public void setClienteAtendido(Cliente clienteAtendido)
    {
        this.clienteAtendido = clienteAtendido;
    }

    public Computador getComputadorMalo()
    {
        return computadorMalo;
    }

    public void setComputadorMalo(Computador computadorMalo)
    {
        this.computadorMalo = computadorMalo;
    }
}