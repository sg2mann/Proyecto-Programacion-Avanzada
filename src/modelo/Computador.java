/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author simon
 */
public class Computador 
{
    private String marca;
    private String modelo;
    private String descripcionProblema;
    private short anioComprado;

    public Computador(String marca, String modelo, String descripcionProblema, short anioComprado)
    {
        this.marca = marca;
        this.modelo = modelo;
        this.descripcionProblema = descripcionProblema;
        this.anioComprado = anioComprado;
    }

    public String getMarca()
    {
        return marca;
    }

    public void setMarca(String marca)
    {
        this.marca = marca;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    public String getDescripcionProblema()
    {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema)
    {
        this.descripcionProblema = descripcionProblema;
    }

    public short getAnioComprado()
    {
        return anioComprado;
    }

    public void setAnioComprado(short anioComprado)
    {
        this.anioComprado = anioComprado;
    }
}