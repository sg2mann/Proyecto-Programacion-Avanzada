
package excepciones;

/**
 * @archivo : StockInsuficienteException.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Excepción lanzada al intentar asignar a una orden más componentes de los disponibles en el inventario
 * @author : Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 16-08-26
 */

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
