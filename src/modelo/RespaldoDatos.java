
package modelo;

/**
 * @archivo : RespaldoDatos.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Clase hija de ServicioAdicional para respaldar datos.
 * @author : Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 30-09-26
 */
public class RespaldoDatos extends ServicioAdicional {
    private int gigabytesARespaldar;

    public RespaldoDatos(double precioBase, int gigabytesARespaldar) {
        super("Respaldo de datos", precioBase);
        this.gigabytesARespaldar = gigabytesARespaldar;
    }

    // SIA-6: Sobreescritura 2
    @Override
    public double calcularCostoFinal() {
        if (gigabytesARespaldar > 500) {
            return precioBase + 10000.0; // Recargo por volumen alto de datos
        }
        return precioBase;
    }
}
