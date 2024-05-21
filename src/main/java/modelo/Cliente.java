package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="clientes")

public class Cliente {

    public Cliente(String nombre, String dni, int codigopostal, int idcliente) {
        this.nombre = nombre;
        this.dni = dni;
        this.codigopostal = codigopostal;
        this.idcliente = idcliente;
    }

    @Id
    @GenericGenerator(name="incrementId",strategy="increment") @GeneratedValue(generator="incrementId")

    private int idcliente;
    private String nombre;
    public Cliente(){}
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(int codigopostal) {
        this.codigopostal = codigopostal;
    }

    private String dni;
    private int codigopostal;
}