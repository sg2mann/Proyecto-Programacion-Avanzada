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
public class Componente {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

    public Componente(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    
    public String getCodigo() { 
        return codigo; 
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo; 
    }
    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getPrecio() {
        return precio; 
    }
    public void setPrecio(double precio) {
        this.precio = precio; 
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        return "[" + codigo + "] " + nombre + " | Precio: $" + precio + " | Stock disp: " + stock;
    }
}