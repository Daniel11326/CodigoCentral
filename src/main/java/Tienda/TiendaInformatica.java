package Tienda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TiendaInformatica {
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/codigocentral";
    private static final String USER = "JavaUser";
    private static final String PASSWORD = "123456789";

    public static void main(String[] args) {
        // Crear la interfaz gráfica de inicio de sesión
        JFrame frame = new JFrame("Tienda Informática");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        showLoginPanel(frame);

        frame.setVisible(true);
    }

    private static void showLoginPanel(JFrame frame) {
        JPanel loginPanel = new JPanel();
        frame.getContentPane().removeAll();
        frame.add(loginPanel);
        loginPanel.setLayout(null);

        JLabel userLabel = new JLabel("DNI:");
        userLabel.setBounds(10, 20, 80, 25);
        loginPanel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        loginPanel.add(userText);

        JLabel passwordLabel = new JLabel("Contrasena:");
        passwordLabel.setBounds(10, 50, 80, 25);
        loginPanel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        loginPanel.add(passwordText);

        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBounds(10, 80, 150, 25);
        loginPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = userText.getText();
                String contrasena = new String(passwordText.getPassword());
                if (authenticateUser(dni, contrasena)) {
                    JOptionPane.showMessageDialog(loginPanel, "Inicio de sesión exitoso");
                    showMainPanel(frame);
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "DNI o contraseña incorrectos");
                }
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private static boolean authenticateUser(String dni, String contrasena) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT * FROM trabajadores WHERE dni = ? AND contrasena = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dni);
            preparedStatement.setString(2, contrasena);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showMainPanel(JFrame frame) {
        JPanel mainPanel = new JPanel();
        frame.getContentPane().removeAll();
        frame.setSize(400, 400); // Ajusta el tamaño de la ventana
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton productosButton = new JButton("Productos");
        JButton proveedoresButton = new JButton("Proveedores");
        JButton clientesButton = new JButton("Clientes");
        JButton pedidosButton = new JButton("Hacer Pedidos");
        JButton ventasButton = new JButton("Vender Productos");
        JButton logoutButton = new JButton("Cerrar sesión");

        productosButton.setFont(new Font("Arial", Font.PLAIN, 20));
        proveedoresButton.setFont(new Font("Arial", Font.PLAIN, 20));
        clientesButton.setFont(new Font("Arial", Font.PLAIN, 20));
        pedidosButton.setFont(new Font("Arial", Font.PLAIN, 20));
        ventasButton.setFont(new Font("Arial", Font.PLAIN, 20));
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));

        mainPanel.add(productosButton);
        mainPanel.add(proveedoresButton);
        mainPanel.add(clientesButton);
        mainPanel.add(pedidosButton);
        mainPanel.add(ventasButton);
        mainPanel.add(logoutButton);

        frame.add(mainPanel);
        frame.setVisible(true);

        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProveedoresPanel(frame);
            }
        });

        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProductosPanel(frame);
            }
        });

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClientesPanel(frame);
            }
        });

        pedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPedidosPanel(frame);
            }
        });

        ventasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showVentasPanel(frame);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel(frame);
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private static void showProveedoresPanel(JFrame frame) {
        JPanel proveedoresPanel = new JPanel(new BorderLayout());
        frame.getContentPane().removeAll();
        frame.setSize(800, 600);
        frame.add(proveedoresPanel);

        String[] columnNames = {"CIF", "Nombre", "Dirección"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        proveedoresPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Añadir Proveedor");
        JButton modifyButton = new JButton("Modificar Proveedor");
        JButton deleteButton = new JButton("Borrar Proveedor");
        JButton backButton = new JButton("Atrás");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        proveedoresPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProveedorDialog(frame, tableModel);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    showModifyProveedorDialog(frame, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(proveedoresPanel, "Seleccione un proveedor para modificar");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String cif = (String) tableModel.getValueAt(selectedRow, 0);
                    deleteProveedor(cif, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(proveedoresPanel, "Seleccione un proveedor para borrar");
                }
            }
        });

        loadProveedoresData(tableModel);

        frame.revalidate();
        frame.repaint();
    }

    private static void loadProveedoresData(DefaultTableModel tableModel) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT cif, nombre, direccion FROM proveedores";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String cif = resultSet.getString("cif");
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                tableModel.addRow(new Object[]{cif, nombre, direccion});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showAddProveedorDialog(JFrame frame, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(frame, "Añadir Proveedor", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel cifLabel = new JLabel("CIF:");
        JTextField cifField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();
        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(cifLabel);
        dialog.add(cifField);
        dialog.add(nombreLabel);
        dialog.add(nombreField);
        dialog.add(direccionLabel);
        dialog.add(direccionField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cif = cifField.getText();
                String nombre = nombreField.getText();
                String direccion = direccionField.getText();

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "INSERT INTO proveedores (cif, nombre, direccion) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, cif);
                    preparedStatement.setString(2, nombre);
                    preparedStatement.setString(3, direccion);
                    preparedStatement.executeUpdate();

                    tableModel.addRow(new Object[]{cif, nombre, direccion});
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void showModifyProveedorDialog(JFrame frame, DefaultTableModel tableModel, int selectedRow) {
        JDialog dialog = new JDialog(frame, "Modificar Proveedor", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel cifLabel = new JLabel("CIF:");
        JTextField cifField = new JTextField((String) tableModel.getValueAt(selectedRow, 0));
        cifField.setEnabled(false); // El CIF no se puede modificar
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(cifLabel);
        dialog.add(cifField);
        dialog.add(nombreLabel);
        dialog.add(nombreField);
        dialog.add(direccionLabel);
        dialog.add(direccionField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cif = cifField.getText();
                String nombre = nombreField.getText();
                String direccion = direccionField.getText();

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "UPDATE proveedores SET nombre = ?, direccion = ? WHERE cif = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setString(2, direccion);
                    preparedStatement.setString(3, cif);
                    preparedStatement.executeUpdate();

                    tableModel.setValueAt(nombre, selectedRow, 1);
                    tableModel.setValueAt(direccion, selectedRow, 2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void deleteProveedor(String cif, DefaultTableModel tableModel, int selectedRow) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "DELETE FROM proveedores WHERE cif = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cif);
            preparedStatement.executeUpdate();

            tableModel.removeRow(selectedRow);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void showProductosPanel(JFrame frame) {
        JPanel productosPanel = new JPanel(new BorderLayout());
        frame.getContentPane().removeAll();
        frame.setSize(800, 600);
        frame.add(productosPanel);

        String[] columnNames = {"ID Producto", "Nombre", "Stock", "Categoría", "Precio"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        productosPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Añadir Producto");
        JButton modifyButton = new JButton("Modificar Producto");
        JButton deleteButton = new JButton("Borrar Producto");
        JButton backButton = new JButton("Atrás");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        productosPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog(frame, tableModel);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    showModifyProductDialog(frame, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(productosPanel, "Seleccione un producto para modificar");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int productId = (int) tableModel.getValueAt(selectedRow, 0);
                    deleteProduct(productId, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(productosPanel, "Seleccione un producto para borrar");
                }
            }
        });

        loadProductosData(tableModel);

        frame.revalidate();
        frame.repaint();
    }

    private static void loadProductosData(DefaultTableModel tableModel) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT idproducto, nombre, stock, categoria, precio FROM productos";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idproducto = resultSet.getInt("idproducto");
                String nombre = resultSet.getString("nombre");
                int stock = resultSet.getInt("stock");
                String categoria = resultSet.getString("categoria");
                int precio = resultSet.getInt("precio");
                tableModel.addRow(new Object[]{idproducto, nombre, stock, categoria, precio});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showAddProductDialog(JFrame frame, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(frame, "Añadir Producto", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField();
        JLabel categoriaLabel = new JLabel("Categoría:");
        JTextField categoriaField = new JTextField();
        JLabel precioLabel = new JLabel("Precio:");
        JTextField precioField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(nombreLabel);
        dialog.add(nombreField);
        dialog.add(stockLabel);
        dialog.add(stockField);
        dialog.add(categoriaLabel);
        dialog.add(categoriaField);
        dialog.add(precioLabel);
        dialog.add(precioField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                int stock = Integer.parseInt(stockField.getText());
                String categoria = categoriaField.getText();
                int precio = Integer.parseInt(precioField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "INSERT INTO productos (nombre, stock, categoria, precio) VALUES (?, ?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setInt(2, stock);
                    preparedStatement.setString(3, categoria);
                    preparedStatement.setInt(4, precio);
                    preparedStatement.executeUpdate();

                    loadProductosData(tableModel); // Recargar datos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void showModifyProductDialog(JFrame frame, DefaultTableModel tableModel, int selectedRow) {
        JDialog dialog = new JDialog(frame, "Modificar Producto", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        JLabel categoriaLabel = new JLabel("Categoría:");
        JTextField categoriaField = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
        JLabel precioLabel = new JLabel("Precio:");
        JTextField precioField = new JTextField(tableModel.getValueAt(selectedRow, 4).toString());

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(nombreLabel);
        dialog.add(nombreField);
        dialog.add(stockLabel);
        dialog.add(stockField);
        dialog.add(categoriaLabel);
        dialog.add(categoriaField);
        dialog.add(precioLabel);
        dialog.add(precioField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idproducto = (int) tableModel.getValueAt(selectedRow, 0);
                String nombre = nombreField.getText();
                int stock = Integer.parseInt(stockField.getText());
                String categoria = categoriaField.getText();
                int precio = Integer.parseInt(precioField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "UPDATE productos SET nombre = ?, stock = ?, categoria = ?, precio = ? WHERE idproducto = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setInt(2, stock);
                    preparedStatement.setString(3, categoria);
                    preparedStatement.setInt(4, precio);
                    preparedStatement.setInt(5, idproducto);
                    preparedStatement.executeUpdate();

                    loadProductosData(tableModel); // Recargar datos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void deleteProduct(int productId, DefaultTableModel tableModel, int selectedRow) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "DELETE FROM productos WHERE idproducto = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();

            tableModel.removeRow(selectedRow);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void showClientesPanel(JFrame frame) {
        JPanel clientesPanel = new JPanel(new BorderLayout());
        frame.getContentPane().removeAll();
        frame.setSize(800, 600);
        frame.add(clientesPanel);

        String[] columnNames = {"ID Cliente", "Nombre", "DNI", "Código Postal"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        clientesPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Añadir Cliente");
        JButton modifyButton = new JButton("Modificar Cliente");
        JButton deleteButton = new JButton("Borrar Cliente");
        JButton backButton = new JButton("Atrás");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        clientesPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddClienteDialog(frame, tableModel);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    showModifyClienteDialog(frame, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(clientesPanel, "Seleccione un cliente para modificar");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int clienteId = (int) tableModel.getValueAt(selectedRow, 0);
                    deleteCliente(clienteId, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(clientesPanel, "Seleccione un cliente para borrar");
                }
            }
        });

        loadClientesData(tableModel);

        frame.revalidate();
        frame.repaint();
    }

    private static void loadClientesData(DefaultTableModel tableModel) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT idcliente, nombre, dni, codigopostal FROM clientes";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idcliente = resultSet.getInt("idcliente");
                String nombre = resultSet.getString("nombre");
                String dni = resultSet.getString("dni");
                int codigopostal = resultSet.getInt("codigopostal");
                tableModel.addRow(new Object[]{idcliente, nombre, dni, codigopostal});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showAddClienteDialog(JFrame frame, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(frame, "Añadir Cliente", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();
        JLabel dniLabel = new JLabel("DNI:");
        JTextField dniField = new JTextField();
        JLabel codigopostalLabel = new JLabel("Código Postal:");
        JTextField codigopostalField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(nombreLabel);
        dialog.add(nombreField);
        dialog.add(dniLabel);
        dialog.add(dniField);
        dialog.add(codigopostalLabel);
        dialog.add(codigopostalField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String dni = dniField.getText();
                int codigopostal = Integer.parseInt(codigopostalField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "INSERT INTO clientes (nombre, dni, codigopostal) VALUES (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setString(2, dni);
                    preparedStatement.setInt(3, codigopostal);
                    preparedStatement.executeUpdate();

                    loadClientesData(tableModel); // Recargar datos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void showModifyClienteDialog(JFrame frame, DefaultTableModel tableModel, int selectedRow) {
        JDialog dialog = new JDialog(frame, "Modificar Cliente", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
        JLabel dniLabel = new JLabel("DNI:");
        JTextField dniField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JLabel codigopostalLabel = new JLabel("Código Postal:");
        JTextField codigopostalField = new JTextField(tableModel.getValueAt(selectedRow, 3).toString());

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(nombreLabel);
        dialog.add(nombreField);
        dialog.add(dniLabel);
        dialog.add(dniField);
        dialog.add(codigopostalLabel);
        dialog.add(codigopostalField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idcliente = (int) tableModel.getValueAt(selectedRow, 0);
                String nombre = nombreField.getText();
                String dni = dniField.getText();
                int codigopostal = Integer.parseInt(codigopostalField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "UPDATE clientes SET nombre = ?, dni = ?, codigopostal = ? WHERE idcliente = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setString(2, dni);
                    preparedStatement.setInt(3, codigopostal);
                    preparedStatement.setInt(4, idcliente);
                    preparedStatement.executeUpdate();

                    loadClientesData(tableModel); // Recargar datos
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void deleteCliente(int clienteId, DefaultTableModel tableModel, int selectedRow) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "DELETE FROM clientes WHERE idcliente = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clienteId);
            preparedStatement.executeUpdate();

            tableModel.removeRow(selectedRow);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void showPedidosPanel(JFrame frame) {
        JPanel pedidosPanel = new JPanel(new BorderLayout());
        frame.getContentPane().removeAll();
        frame.setSize(800, 600);
        frame.add(pedidosPanel);

        String[] columnNames = {"ID Pedido", "ID Producto", "CIF Proveedor"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        pedidosPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Añadir Pedido");
        JButton modifyButton = new JButton("Modificar Pedido");
        JButton deleteButton = new JButton("Borrar Pedido");
        JButton backButton = new JButton("Atrás");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        pedidosPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPedidoDialog(frame, tableModel);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    showModifyPedidoDialog(frame, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(pedidosPanel, "Seleccione un pedido para modificar");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int pedidoId = (int) tableModel.getValueAt(selectedRow, 0);
                    deletePedido(pedidoId, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(pedidosPanel, "Seleccione un pedido para borrar");
                }
            }
        });

        loadPedidosData(tableModel);

        frame.revalidate();
        frame.repaint();
    }

    private static void loadPedidosData(DefaultTableModel tableModel) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT idpedido, idproducto, idproveedores FROM hacerpedidos";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idpedido = resultSet.getInt("idpedido");
                int idproducto = resultSet.getInt("idproducto");
                String idproveedores = resultSet.getString("idproveedores");
                tableModel.addRow(new Object[]{idpedido, idproducto, idproveedores});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showAddPedidoDialog(JFrame frame, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(frame, "Añadir Pedido", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel idProductoLabel = new JLabel("ID Producto:");
        JTextField idProductoField = new JTextField();
        JLabel idProveedoresLabel = new JLabel("CIF Proveedor:");
        JTextField idProveedoresField = new JTextField();
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(idProductoLabel);
        dialog.add(idProductoField);
        dialog.add(idProveedoresLabel);
        dialog.add(idProveedoresField);
        dialog.add(stockLabel);
        dialog.add(stockField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idProducto = Integer.parseInt(idProductoField.getText());
                String idProveedores = idProveedoresField.getText();
                int stock = Integer.parseInt(stockField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "INSERT INTO hacerpedidos (idproducto, idproveedores) VALUES (?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, idProducto);
                    preparedStatement.setString(2, idProveedores);
                    preparedStatement.executeUpdate();

                    loadPedidosData(tableModel); // Recargar datos

                    // Actualizar el stock del producto
                    String updateStockSql = "UPDATE productos SET stock = stock + ? WHERE idproducto = ?";
                    PreparedStatement updateStockStatement = connection.prepareStatement(updateStockSql);
                    updateStockStatement.setInt(1, stock);
                    updateStockStatement.setInt(2, idProducto);
                    updateStockStatement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void showModifyPedidoDialog(JFrame frame, DefaultTableModel tableModel, int selectedRow) {
        JDialog dialog = new JDialog(frame, "Modificar Pedido", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel idProductoLabel = new JLabel("ID Producto:");
        JTextField idProductoField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        JLabel idProveedoresLabel = new JLabel("CIF Proveedor:");
        JTextField idProveedoresField = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(idProductoLabel);
        dialog.add(idProductoField);
        dialog.add(idProveedoresLabel);
        dialog.add(idProveedoresField);
        dialog.add(stockLabel);
        dialog.add(stockField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idPedido = (int) tableModel.getValueAt(selectedRow, 0);
                int idProducto = Integer.parseInt(idProductoField.getText());
                String idProveedores = idProveedoresField.getText();
                int stock = Integer.parseInt(stockField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "UPDATE hacerpedidos SET idproducto = ?, idproveedores = ? WHERE idpedido = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, idProducto);
                    preparedStatement.setString(2, idProveedores);
                    preparedStatement.setInt(3, idPedido);
                    preparedStatement.executeUpdate();

                    loadPedidosData(tableModel); // Recargar datos

                    // Actualizar el stock del producto
                    String updateStockSql = "UPDATE productos SET stock = stock + ? WHERE idproducto = ?";
                    PreparedStatement updateStockStatement = connection.prepareStatement(updateStockSql);
                    updateStockStatement.setInt(1, stock);
                    updateStockStatement.setInt(2, idProducto);
                    updateStockStatement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void deletePedido(int pedidoId, DefaultTableModel tableModel, int selectedRow) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "DELETE FROM hacerpedidos WHERE idpedido = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pedidoId);
            preparedStatement.executeUpdate();

            tableModel.removeRow(selectedRow);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void showVentasPanel(JFrame frame) {
        JPanel ventasPanel = new JPanel(new BorderLayout());
        frame.getContentPane().removeAll();
        frame.setSize(800, 600);
        frame.add(ventasPanel);

        String[] columnNames = {"ID Venta", "ID Cliente", "ID Producto"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        ventasPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Añadir Venta");
        JButton modifyButton = new JButton("Modificar Venta");
        JButton deleteButton = new JButton("Borrar Venta");
        JButton backButton = new JButton("Atrás");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        ventasPanel.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPanel(frame);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddVentaDialog(frame, tableModel);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    showModifyVentaDialog(frame, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(ventasPanel, "Seleccione una venta para modificar");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int ventaId = (int) tableModel.getValueAt(selectedRow, 0);
                    deleteVenta(ventaId, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(ventasPanel, "Seleccione una venta para borrar");
                }
            }
        });

        loadVentasData(tableModel);

        frame.revalidate();
        frame.repaint();
    }

    private static void loadVentasData(DefaultTableModel tableModel) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "SELECT idventa, idcliente, idproducto FROM ventaproductos";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idventa = resultSet.getInt("idventa");
                int idcliente = resultSet.getInt("idcliente");
                int idproducto = resultSet.getInt("idproducto");
                tableModel.addRow(new Object[]{idventa, idcliente, idproducto});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void showAddVentaDialog(JFrame frame, DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(frame, "Añadir Venta", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel idClienteLabel = new JLabel("ID Cliente:");
        JTextField idClienteField = new JTextField();
        JLabel idProductoLabel = new JLabel("ID Producto:");
        JTextField idProductoField = new JTextField();
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(idClienteLabel);
        dialog.add(idClienteField);
        dialog.add(idProductoLabel);
        dialog.add(idProductoField);
        dialog.add(stockLabel);
        dialog.add(stockField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idCliente = Integer.parseInt(idClienteField.getText());
                int idProducto = Integer.parseInt(idProductoField.getText());
                int stock = Integer.parseInt(stockField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "INSERT INTO ventaproductos (idcliente, idproducto) VALUES (?, ?)";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, idCliente);
                    preparedStatement.setInt(2, idProducto);
                    preparedStatement.executeUpdate();

                    loadVentasData(tableModel); // Recargar datos

                    // Actualizar el stock del producto
                    String updateStockSql = "UPDATE productos SET stock = stock - ? WHERE idproducto = ?";
                    PreparedStatement updateStockStatement = connection.prepareStatement(updateStockSql);
                    updateStockStatement.setInt(1, stock);
                    updateStockStatement.setInt(2, idProducto);
                    updateStockStatement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void showModifyVentaDialog(JFrame frame, DefaultTableModel tableModel, int selectedRow) {
        JDialog dialog = new JDialog(frame, "Modificar Venta", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel idClienteLabel = new JLabel("ID Cliente:");
        JTextField idClienteField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        JLabel idProductoLabel = new JLabel("ID Producto:");
        JTextField idProductoField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField();

        JButton saveButton = new JButton("Guardar");
        JButton cancelButton = new JButton("Cancelar");

        dialog.add(idClienteLabel);
        dialog.add(idClienteField);
        dialog.add(idProductoLabel);
        dialog.add(idProductoField);
        dialog.add(stockLabel);
        dialog.add(stockField);
        dialog.add(saveButton);
        dialog.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idVenta = (int) tableModel.getValueAt(selectedRow, 0);
                int idCliente = Integer.parseInt(idClienteField.getText());
                int idProducto = Integer.parseInt(idProductoField.getText());
                int stock = Integer.parseInt(stockField.getText());

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                try {
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql = "UPDATE ventaproductos SET idcliente = ?, idproducto = ? WHERE idventa = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, idCliente);
                    preparedStatement.setInt(2, idProducto);
                    preparedStatement.setInt(3, idVenta);
                    preparedStatement.executeUpdate();

                    loadVentasData(tableModel); // Recargar datos

                    // Actualizar el stock del producto
                    String updateStockSql = "UPDATE productos SET stock = stock - ? WHERE idproducto = ?";
                    PreparedStatement updateStockStatement = connection.prepareStatement(updateStockSql);
                    updateStockStatement.setInt(1, stock);
                    updateStockStatement.setInt(2, idProducto);
                    updateStockStatement.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (preparedStatement != null) preparedStatement.close();
                        if (connection != null) connection.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void deleteVenta(int ventaId, DefaultTableModel tableModel, int selectedRow) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "DELETE FROM ventaproductos WHERE idventa = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ventaId);
            preparedStatement.executeUpdate();

            tableModel.removeRow(selectedRow);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}



