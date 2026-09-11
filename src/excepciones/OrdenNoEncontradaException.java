
package excepciones;

/**
 * @archivo : OrdenNoEncontradaException.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Excepción lanzada cuando se intenta buscar o modificar una orden de trabajo que no existe en el sistema
 * @author : Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 16-08-26
 */

public class OrdenNoEncontradaException extends Exception {
    public OrdenNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
