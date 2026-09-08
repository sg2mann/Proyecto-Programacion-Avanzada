/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sistemaserviciotecnico;
import java.util.Scanner;
import vista.ConsolaView;

/**
 *
 * @author cris
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SistemaServicioTecnico sistema = new SistemaServicioTecnico();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==========================================");
        System.out.println("  SISTEMA DE GESTIÓN DE SERVICIO TÉCNICO  ");
        System.out.println("==========================================");
        System.out.println("Seleccione el modo de ejecución:");
        System.out.println("1. Modo Consola (Texto interactivo)");
        System.out.println("2. Modo Gráfico (Ventana Swing - Próximamente)");
        System.out.print("Opción: ");
        
        String opcion = scanner.nextLine().trim();
        
        switch (opcion) {
            case "1":
                ConsolaView vistaConsola = new ConsolaView(sistema);
                vistaConsola.iniciar();
                break;
            case "2":
                System.out.println("Iniciando interfaz gráfica");
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new vista.gui.VentanaPrincipal(sistema).setVisible(true);
                    }
                });
                break;
            default:
                System.out.println("Opción no válida. Cerrando aplicación.");
                break;
        }
    }   
}
