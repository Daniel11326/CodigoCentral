package org.example;

public class Proveedor {
    private String cif;
    private String nombre;
    private String direccion;


    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Proveedor(String nombre, String cif, String direccion) {
        this.nombre = nombre;
        this.cif = cif;
        this.direccion = direccion;
}
}
