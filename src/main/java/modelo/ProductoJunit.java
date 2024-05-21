package modelo;

/**
 * Esta es la clase producto
 */
public class ProductoJunit {
    private int id;
    private String nombre;
    private double precio;
    private int stock;


    public ProductoJunit(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Este metodo multiplica por dos el precio del producto
     * @return Devuelve un double
     */
    public double precioDoble(){
        return this.precio*2;
}
}