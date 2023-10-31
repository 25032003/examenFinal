package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

class RegistrationFrame extends JFrame {
    private JTextField studentIdField;
    private JTextField courseIdField;
    private JTextField registrationDateField;
    private JButton registerButton;
    private JButton exitButton;

    private Connection conexion;

    public RegistrationFrame() {
        setTitle("Inscripción");
        setSize(600, 300);
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
        studentIdField = new JTextField(10);
        courseIdField = new JTextField(10);
        registrationDateField = new JTextField(10);
        registerButton = new JButton("Registrar");
        exitButton = new JButton("Salir");

        Color backgroundColor = new Color(173, 216, 230);
        Color buttonColor = new Color(0, 128, 0);
        Color exitButtonColor = new Color(139, 0, 0);

        panel.setBackground(backgroundColor);
        registerButton.setBackground(buttonColor);
        exitButton.setBackground(exitButtonColor);

        Font font = new Font("Arial", Font.PLAIN, 14);
        studentIdField.setFont(font);
        courseIdField.setFont(font);
        registrationDateField.setFont(font);
        registerButton.setFont(font);
        exitButton.setFont(font);

        registerButton.addActionListener(e -> registrarEstudianteEnCurso());
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(new JLabel("ID Estudiante:"));
        panel.add(studentIdField);
        panel.add(new JLabel("ID Curso:"));
        panel.add(courseIdField);
        panel.add(new JLabel("Fecha de Inscripción (YYYY-MM-DD):"));
        panel.add(registrationDateField);
        panel.add(registerButton);
        panel.add(exitButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registrarEstudianteEnCurso() {
        try {
            String studentId = studentIdField.getText();
            int studentIdValue = Integer.parseInt(studentId);

            String courseId = courseIdField.getText();
            int courseIdValue = Integer.parseInt(courseId); // Convertir a INTEGER

            String registrationDate = registrationDateField.getText();

            if (studentId.isEmpty() || courseId.isEmpty() || registrationDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la fecha actual
            Timestamp fechaInscripcion = Timestamp.valueOf(registrationDate + " 00:00:00");

            // Insertar datos en la tabla
            String sql = "INSERT INTO inscripciones (id_estudiante, id_curso, fecha_inscripcion) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setInt(1, studentIdValue);
                statement.setInt(2, courseIdValue);
                statement.setTimestamp(3, fechaInscripcion);

                int filasAfectadas = statement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Estudiante inscrito correctamente en el curso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo realizar la inscripción", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationFrame::new);
    }
}
