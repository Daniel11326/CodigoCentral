package org.example;


import modelo.Cliente;
import modelo.Producto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Iterator;
import java.util.List;

public class XML {
    public boolean importarClientes(List<Cliente> lista) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.newDocument();

            Element rootElement = documento.createElement("Clientes");

            Iterator<Cliente> it = lista.iterator();

            while (it.hasNext()) {

                Cliente p = it.next();
                Element ClienteElement = documento.createElement("Cliente");
                ClienteElement.setAttribute("id_cliente", "" + p.getIdcliente());

                Element nombre = documento.createElement("Nombre");
                nombre.appendChild(documento.createTextNode(p.getNombre()));
                ClienteElement.appendChild(nombre);  // Nombre es hijo de producto

                Element dni = documento.createElement("DNI");
                dni.setTextContent("" + p.getDni());
                ClienteElement.appendChild(dni);  //  Unidades es hijo de producto

                rootElement.appendChild(ClienteElement); // Producto es hijo de tienda

            }

            documento.appendChild(rootElement);

            // Configurar indentación XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Activar la indentación
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Espacios para la indentación

            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult("./src/main/resources/xml/Clientes.xml");
            transformer.transform(source, result);

            System.out.println("Archivo XML creado correctamente.");

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
    public boolean importarProductos(List<Producto> lista) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.newDocument();

            Element rootElement = documento.createElement("Productos");

            Iterator<Producto> it = lista.iterator();

            while (it.hasNext()) {

                Producto p = it.next();
                Element ProductoElement = documento.createElement("Producto");
                ProductoElement.setAttribute("id_producto", "" + p.getIdproducto());

                Element nombre = documento.createElement("Nombre");
                nombre.appendChild(documento.createTextNode(p.getNombre()));
                ProductoElement.appendChild(nombre);  // Nombre es hijo de producto

                Element Precio = documento.createElement("Precio");
                Precio.setTextContent("" + p.getPrecio());
                ProductoElement.appendChild(Precio);  //  Unidades es hijo de producto

                Element Stock = documento.createElement("Stock");
                Stock.setTextContent("" + p.getStock());
                ProductoElement.appendChild(Stock);  //  Unidades es hijo de producto

                rootElement.appendChild(ProductoElement); // Producto es hijo de tienda

            }


            documento.appendChild(rootElement);

            // Configurar indentación XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Activar la indentación
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Espacios para la indentación

            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult("./src/main/resources/xml/Productos.xml");
            transformer.transform(source, result);

            System.out.println("Archivo XML creado correctamente.");

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
    public boolean importarProveedores(List<Proveedor> lista) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document documento = dBuilder.newDocument();

            Element rootElement = documento.createElement("Proveedores");

            Iterator<Proveedor> it = lista.iterator();

            while (it.hasNext()) {

                Proveedor p = it.next();
                Element ProveedorElement = documento.createElement("Proveedor");
                ProveedorElement.setAttribute("cif", "" + p.getCif());

                Element nombre = documento.createElement("Nombre");
                nombre.appendChild(documento.createTextNode(p.getNombre()));
                ProveedorElement.appendChild(nombre);  // Nombre es hijo de producto

                rootElement.appendChild(ProveedorElement); // Producto es hijo de tienda

            }
            documento.appendChild(rootElement);

            // Configurar indentación XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // Activar la indentación
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); // Espacios para la indentación

            DOMSource source = new DOMSource(documento);
            StreamResult result = new StreamResult("./src/main/resources/xml/Proveedores.xml");
            transformer.transform(source, result);

            System.out.println("Archivo XML creado correctamente.");

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static void main(String[] args){

}
}