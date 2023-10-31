package org.example;

import org.example.CourseDetailsFrame;
import org.example.RegistrationFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends JFrame {
    private JButton studentDetailsButton;
    private JButton courseDetailsButton;
    private JButton registrationButton;
    private JButton readDataButton;
    private JButton deleteDataButton;

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mysecretpassword";

    private Connection conexion;

    public Main() {
        setTitle("Menú Principal");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        studentDetailsButton = new JButton("Detalles del Estudiante");
        courseDetailsButton = new JButton("Detalles del Curso");
        registrationButton = new JButton("Inscripción");
        readDataButton = new JButton("Leer Datos");
        deleteDataButton = new JButton("Eliminar Datos");

        // Establecer nuevos colores y tipos de letra...

        // Agregar acciones a los botones
        studentDetailsButton.addActionListener(e -> abrirFormulario(new StudentDetailsFrame()));
        courseDetailsButton.addActionListener(e -> abrirFormulario(new CourseDetailsFrame()));
        registrationButton.addActionListener(e -> abrirFormulario(new RegistrationFrame()));

        // Acciones para Leer Datos y Eliminar Datos
        readDataButton.addActionListener(e -> {
            String studentName = JOptionPane.showInputDialog(this, "Nombre del Estudiante:");
            if (studentName != null) {
                leerEstudiante(studentName);
            }
        });

        deleteDataButton.addActionListener(e -> {
            String studentName = JOptionPane.showInputDialog(this, "Nombre del Estudiante:");
            if (studentName != null) {
                eliminarEstudiante(studentName);
            }
        });

        panel.add(studentDetailsButton);
        panel.add(courseDetailsButton);
        panel.add(registrationButton);
        panel.add(readDataButton);
        panel.add(deleteDataButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirFormulario(JFrame frame) {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    private void leerEstudiante(String studentName) {
        // Implementar la lógica para leer los datos del estudiante
        // ...
    }

    private void eliminarEstudiante(String studentName) {
        // Implementar la lógica para eliminar al estudiante
        // ...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

class StudentDetailsFrame extends JFrame {
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JButton addButton;

    private Connection conexion;

    public StudentDetailsFrame() {
        setTitle("Detalles del Estudiante");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Establecer información de conexión
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String usuario = "postgres";
        String contraseña = "mysecretpassword";

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de conexión a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        nameField = new JTextField(10);
        lastNameField = new JTextField(10);
        emailField = new JTextField(10);
        addButton = new JButton("Agregar Estudiante");

        // Establecer nuevos colores
        Color backgroundColor = new Color(240, 255, 240); // Verde Pálido
        Color buttonColor = new Color(46, 139, 87); // Verde Mar

        panel.setBackground(backgroundColor);
        addButton.setBackground(buttonColor);

        addButton.addActionListener(e -> agregarEstudiante());

        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Apellido:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(addButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarEstudiante() {
        try {
            String nombre = nameField.getText();
            String apellido = lastNameField.getText();
            String email = emailField.getText();

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir del método si hay campos vacíos
            }

            // Consulta SQL para agregar estudiante
            String sql = "INSERT INTO estudiantes (nombre, apellido, email) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, apellido);
                statement.setString(3, email);

                int filasAfectadas = statement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Estudiante agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Imprimir el detalle del error
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el detalle del error
        }
    }
}
