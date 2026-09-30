package modelo;

/**
 * @archivo : ServicioAdicional.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Servicio Adicional
 * @author : Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 30-09-26
 */
public class ServicioAdicional {
    protected String nombreServicio;
    protected double precioBase;

    public ServicioAdicional(String nombreServicio, double precioBase) {
        this.nombreServicio = nombreServicio;
        this.precioBase = precioBase;
    }

    // Método de negocio que será sobreescrito por las clases hijas
    public double calcularCostoFinal() {
        return precioBase;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }
}
