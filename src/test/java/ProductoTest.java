import modelo.ProductoJunit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class ProductoTest {
    ProductoJunit producto = new ProductoJunit(1,"Ordenador",500, 10);
    @Test
    public void testPrecioDouble(){
        double expected = 1000;
        double actual = producto.precioDoble();
        assertEquals(expected,actual);
}
}