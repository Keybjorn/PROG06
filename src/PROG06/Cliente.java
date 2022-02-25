package PROG06;

import java.io.*;
import java.util.*;

/**
 * Clase Cliente (PROG06)
 * @author key
 */

public class Cliente implements Serializable {

    private static final long serialVersionUID = -1L;

    // Atributos privados
    private String NIF;
    private String nombre;
    private String tlfn;
    private String direccion;
    private double deuda;

    // Constructor personalizado
    public Cliente(String NIF, String nombre, String tlfn, String direccion, double deuda) {
        this.NIF = NIF;
        this.nombre = nombre;
        this.tlfn = tlfn;
        this.direccion = direccion;
        this.deuda = deuda;
    }

    // Métodos Setters & Getters
    public String getNIF() {
        return NIF;
    }
    
    public void setNIF(String NIF){
        this.NIF = NIF;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getTlfn() {
        return tlfn;
    }
    
    public void setTlfn(String tlfn){
        this.tlfn = tlfn;
    }

    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }

    public double getDeuda() {
        return deuda;
    }
    
    public void setDeuda(double deuda){
        this.deuda = deuda;
    }
    
    // Método toString()
    public String toString(){
        return NIF + " " + nombre + " " + tlfn + " " + direccion + " " + deuda;
    }
    
}
