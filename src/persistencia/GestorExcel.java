/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author cris
 */

import modelo.Componente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class GestorExcel {

    public static String exportarInventario(Map<String, Componente> inventario, String nombreArchivo) throws IOException {
        String nombreFinal = nombreArchivo.endsWith(".xlsx") ? nombreArchivo : nombreArchivo + ".xlsx";
        File archivoDestino = new File(nombreFinal);

        try (Workbook libro = new XSSFWorkbook()) {
            Sheet hoja = libro.createSheet("Stock de Taller");

            Row filaCabecera = hoja.createRow(0);
            String[] columnas = {"Código de Pieza", "Descripción", "Precio Unitario (CLP)", "Unidades en Stock"};
            
            for (int i = 0; i < columnas.length; i++) {
                Cell celda = filaCabecera.createCell(i);
                celda.setCellValue(columnas[i]);
            }

            int numeroFila = 1;
            for (Componente comp : inventario.values()) {
                Row fila = hoja.createRow(numeroFila++);
                fila.createCell(0).setCellValue(comp.getCodigo());
                fila.createCell(1).setCellValue(comp.getNombre());
                fila.createCell(2).setCellValue(comp.getPrecio());
                fila.createCell(3).setCellValue(comp.getStock());
            }

            for (int i = 0; i < columnas.length; i++) {
                hoja.autoSizeColumn(i);
            }

            try (FileOutputStream salida = new FileOutputStream(archivoDestino)) {
                libro.write(salida);
            }
        }

        // Retorna la ruta absoluta exacta donde el sistema operativo guardó el archivo
        return archivoDestino.getAbsolutePath();
    }
}