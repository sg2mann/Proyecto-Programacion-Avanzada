
package modelo;

/**
 * @archivo : Computador.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Representa el equipo a reparar ingresado al servicio, detallando sus características y la falla reportada
 * @author : Simón Guzmán, Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 15-08-26
 */

public class Computador {
    private String marca;
    private String modelo;
    private String descripcionProblema;
    private short anioComprado;

    public Computador(String marca, String modelo, String descripcionProblema, short anioComprado) {
        this.marca = marca;
        this.modelo = modelo;
        this.descripcionProblema = descripcionProblema;
        this.anioComprado = anioComprado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    public short getAnioComprado() {
        return anioComprado;
    }

    public void setAnioComprado(short anioComprado) {
        this.anioComprado = anioComprado;
    }

    // Sobreescritura requerida (SIA-6)
    @Override
    public String toString() {
        return marca + " " + modelo + " (" + anioComprado + ") - Falla: " + descripcionProblema;
    }
}