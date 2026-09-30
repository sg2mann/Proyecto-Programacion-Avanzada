/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * @archivo : LimpiezaFisica.java
 * @Project : Sistema Servicio Tecnico de Computadores
 * @Descripcion : Clase hija de ServicioAdicional para limpieza fisica.
 * @author : Cristobal Sazo
 * @Lenguaje : Java
 * @Fecha : 30-09-26
 */
public class LimpiezaFisica extends ServicioAdicional {
    private boolean incluyeCambioPastaTermica;

    public LimpiezaFisica(double precioBase, boolean incluyeCambioPastaTermica) {
        super("Limpieza física interna", precioBase);
        this.incluyeCambioPastaTermica = incluyeCambioPastaTermica;
    }

    // SIA-6: Sobreescritura 1
    @Override
    public double calcularCostoFinal() {
        if (incluyeCambioPastaTermica) {
            return precioBase + 15000.0; // Cargo extra por insumos
        }
        return precioBase;
    }
}
