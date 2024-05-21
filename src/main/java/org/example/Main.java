package org.example;

import dao.DAOGenerico;
import modelo.Cliente;
import modelo.Producto;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DAOGenerico dao= new DAOGenerico(Producto.class);
        List<Producto> productos= dao.findAll(Producto.class);
        System.out.println(productos);
        ////
        XML xm = new XML();
        xm.importarProductos(productos);

        DAOGenerico clienteDao = new DAOGenerico(Cliente.class);
        List<Cliente> clientes= clienteDao.findAll(Cliente.class);
        System.out.println(clientes);

        XML xs = new XML();
        xm.importarClientes(clientes);
}
}