
package modelo;

/**
 * @archivo : Cliente.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Representa al cliente del servicio técnico, almacenando sus datos personales y de contacto
 * @author : Simón Guzmán, Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 15-08-26
 */

public class Cliente 
{
    private String rut;
    private String nombre;
    private int numeroTelefono;
    private String correo;

    public Cliente(String rut, String nombre, int numeroTelefono, String correo)
    {
        this.rut = rut;
        this.nombre = nombre;
        this.numeroTelefono = numeroTelefono;
        this.correo = correo;
    }

    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }
    public void setNumeroTelefono(int numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString(){
        return nombre + " (RUT: " + rut + ", Tel: " + numeroTelefono + ", Email: " + correo + ")";
    }
}