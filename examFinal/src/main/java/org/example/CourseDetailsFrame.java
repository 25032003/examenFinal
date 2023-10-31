package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class CourseDetailsFrame extends JFrame {
    private JTextField courseNameField;
    private JTextField instructorField;
    private JButton addCourseButton;

    private Connection conexion;

    public CourseDetailsFrame() {
        setTitle("Detalles del Curso");
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

        JPanel panel = new JPanel(new GridLayout(3, 2));
        courseNameField = new JTextField(10);
        instructorField = new JTextField(10);
        addCourseButton = new JButton("Agregar Curso");

        Color backgroundColor = new Color(240, 255, 240);
        Color buttonColor = new Color(46, 139, 87);

        panel.setBackground(backgroundColor);
        addCourseButton.setBackground(buttonColor);

        addCourseButton.addActionListener(e -> agregarCurso());

        panel.add(new JLabel("Nombre:"));
        panel.add(courseNameField);
        panel.add(new JLabel("Profesor:"));
        panel.add(instructorField);
        panel.add(addCourseButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarCurso() {
        try {
            String courseName = courseNameField.getText();
            String instructor = instructorField.getText();

            if (courseName.isEmpty() || instructor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO cursos (nombre_curso, profesor) VALUES (?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, courseName);
                statement.setString(2, instructor);

                int filasAfectadas = statement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Curso agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el curso", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            System.out.println("Agregar Curso: ¡Éxito!");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
