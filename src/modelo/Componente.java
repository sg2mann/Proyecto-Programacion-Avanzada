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
public class Componente 
{
    private String nombreComponente;
    private int stockDisponible;

    public Componente(String nombreComponente, int stockDisponible)
    {
        this.nombreComponente = nombreComponente;
        this.stockDisponible = stockDisponible;
    }

    public String getNombreComponente()
    {
        return nombreComponente;
    }

    public void setNombreComponente(String nombreComponente)
    {
        this.nombreComponente = nombreComponente;
    }

    public int getStockDisponible()
    {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible)
    {
        this.stockDisponible = stockDisponible;
    }
}