
package modelo;

/**
 * @archivo : Componente.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Representa un repuesto de hardware para las reparaciones, detallando su precio y cantidad en stock
 * @author : Simón Guzmán, Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 15-08-26
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