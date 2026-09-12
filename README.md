# SISTEMA DE GESTIÓN DE ÓRDENES DE TRABAJO - SERVICIO TÉCNICO

Sistema desarrollado en Java para optimizar la gestión de atención de clientes, diagnóstico de equipos computacionesles, asignación de órdenes de trabajo y gestión de stock de repuestos en un taller de servicio técnico.
El proyecto cuenta con un controlador central que gestiona la lógica de negocio, soporte parados interfaces de usuario(**Consola** e **Interfaz Gráfica Swing**), cálculo dinámico de tiempo de entrega, persistencia de archivos CSV y exportación de inventario a documentos Excel mediante Apache POI.

## Instrucciones de Ejecución
1. Descomprimir archivo **Informe Proyecto SIA - Sistema Servicio Técnico.zip** del proyecto, te quedarán dos archivos: el informe en pdf, y una carpeta con el proyecto.
2. Abrir NetBeans.
3. Selecciona **File -> Open Project...** en la esquina superior izquierda.
4. Selecciona la carpeta del proyecto que fue descomprimida anteriormente.
5. Ejecuta el proyecto con click derecho en el proyecto y **Run** o presionando F6.

## Características Principales
* **Gestión de Órdenes de Trabajo:**
  * Registro de órdenes vinculando cliente, equipo computacional y diagnóstico.
  * Búsqueda sobrecargada de órdenes por ID o por RUT de cliente.
  * Actualización de estados del servicio (*RECIBIDO*, *EN PROCESO*, *LISTO*, *ENTREGADO*).
  * Filtrado automatizado de órdenes activas en taller.
* **Cálculo Automatizado de Tiempos de Entrega:** Algoritmo que proyecta la fecha estimada de entrega analizando el volumen de trabajo pendiente en taller y la complejidad por repuestos requeridos.
* **Control de Inventario y Stock:**
  * Asignación y desasignación de repuestos a las órdenes de trabajo.
  * Control y validación estricta de stock disponible en tiempo real.
  * Devolución automática de componentes al inventario al modificar o cancelar una orden.
* **Persistencia de Datos y Reportes:**
  * Almacenamiento continuo de ordenes e inventario mediante archivos **CSV** (`ordenes.csv` e `inventario.csv`).
  * Exportación del stock de componentes a hojas de cálculo de **Excel (`.xlsx`)**.
* **Doble Modo de Ejecución:**
  * **Modo Consola:** Interfaz de texto interactiva basada en menús.
  * **Modo Gráfico:** Ventanas interactivas desarrolladas con **Java Swing** (`java.awt.EventQueue`).

## Requisitos de Instalación
* **Java Development Kit (JDK):** Versión 8 o 11 (o superior).
* **IDE:** NetBeans IDE (versión 12 o superior recomendada).
* **Librerías externas:**
  * **Apache POI (`poi-ooxml`):** Requerido para la lectura/escritura de archivos Excel en la clase `GestorExcel`.
