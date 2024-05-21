package modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="productos")

public class Producto {
    @Id
    @GenericGenerator(name="incrementId",strategy="increment") @GeneratedValue(generator="incrementId")
    private int idproducto;
    private String nombre;
    private int stock;
    private String categoria;
    private int precio;
    public Producto(){}
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public Producto(String nombre, int stock, String categoria, int precio, int idproducto) {
        this.nombre = nombre;
        this.stock = stock;
        this.categoria = categoria;
        this.precio = precio;
        this.idproducto = idproducto;
}
}