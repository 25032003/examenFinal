//package org.example;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//class StudentDetailsFrame extends JFrame {
//    private JTextField nameField;
//    private JTextField lastNameField;
//    private JTextField emailField;
//    private JButton addButton;
//
//    private Connection conexion;
//
//    public StudentDetailsFrame() {
//        setTitle("Detalles del Estudiante");
//        setSize(400, 200);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        // Establecer información de conexión
//        String url = "jdbc:postgresql://localhost:5432/postgres";
//        String usuario = "postgres";
//        String contraseña = "mysecretpassword";
//
//        try {
//            conexion = DriverManager.getConnection(url, usuario, contraseña);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
//            System.exit(1);
//        }
//
//        JPanel panel = new JPanel(new GridLayout(4, 2));
//        nameField = new JTextField(10);
//        lastNameField = new JTextField(10);
//        emailField = new JTextField(10);
//        addButton = new JButton("Agregar Estudiante");
//
//        // Establecer nuevos colores
//        Color backgroundColor = new Color(240, 255, 240); // Verde Pálido
//        Color buttonColor = new Color(46, 139, 87); // Verde Mar
//
//        panel.setBackground(backgroundColor);
//        addButton.setBackground(buttonColor);
//
//        addButton.addActionListener(e -> agregarEstudiante());
//
//        panel.add(new JLabel("Nombre:"));
//        panel.add(nameField);
//        panel.add(new JLabel("Apellido:"));
//        panel.add(lastNameField);
//        panel.add(new JLabel("Email:"));
//        panel.add(emailField);
//        panel.add(addButton);
//
//        add(panel);
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    private void agregarEstudiante() {
//        try {
//            String nombre = nameField.getText();
//            String apellido = lastNameField.getText();
//            String email = emailField.getText();
//
//            // Verificar que los campos no estén vacíos
//            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
//                return; // Salir del método si hay campos vacíos
//            }
//
//            // Consulta SQL para agregar estudiante
//            String sql = "INSERT INTO estudiantes (nombre, apellido, email) VALUES (?, ?, ?)";
//            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
//                statement.setString(1, nombre);
//                statement.setString(2, apellido);
//                statement.setString(3, email);
//
//                int filasAfectadas = statement.executeUpdate();
//
//                if (filasAfectadas > 0) {
//                    JOptionPane.showMessageDialog(this, "Estudiante agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(this, "No se pudo agregar el estudiante", "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace(); // Imprimir el detalle del error
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Imprimir el detalle del error
//        }
//    }
//}
