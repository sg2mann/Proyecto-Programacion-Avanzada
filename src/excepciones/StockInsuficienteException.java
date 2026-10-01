
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
    private final String codigoComponente;
    private final int stockDisponible;
    private final int cantidadSolicitada;

    public StockInsuficienteException(String codigoComponente, int stockDisponible, int cantidadSolicitada) {
        super("Stock insuficiente para el componente: " + codigoComponente);
        this.codigoComponente = codigoComponente;
        this.stockDisponible = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getStockDisponible() { return stockDisponible; }
    public int getCantidadSolicitada() { return cantidadSolicitada; }
    public String getCodigoComponente() { return codigoComponente; }
}