package org.example;
import java.sql.PreparedStatement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;


    public LoginFrame() {
        setTitle("Inicio de Sesión");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Iniciar Sesión");

        // Establecer nuevos colores
        Color backgroundColor = new Color(255, 228, 196); // Color Almendra
        Color buttonColor = new Color(65, 105, 225); // Azul Real

        panel.setBackground(backgroundColor);
        usernameLabel.setForeground(Color.DARK_GRAY);
        passwordLabel.setForeground(Color.DARK_GRAY);
        usernameField.setBackground(Color.WHITE);
        passwordField.setBackground(Color.WHITE);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            if (autenticar(username, password)) {
                abrirVentanaPrincipal();
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }

            Arrays.fill(passwordChars, ' '); // Borra la contraseña en memoria
            passwordField.setText(""); // Borra la contraseña visible en el campo
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean autenticar(String username, String password) {
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usuarioDB = "postgres";
            String contrasenaDB = "mysecretpassword";

            Connection conexion = DriverManager.getConnection(url, usuarioDB, contrasenaDB);

            // Consulta SQL para verificar las credenciales
            String sql = "SELECT * FROM login WHERE username = ? AND password = ?";

            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Las credenciales son correctas
                    resultSet.close();
                    return true;
                }

                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo de errores

            // Agrega el siguiente mensaje para obtener más información en la consola
            System.out.println("Error en la consulta SQL: " + ex.getMessage());
        }
        return false; // Autenticación fallida por defecto
    }

    private void abrirVentanaPrincipal() {
        // Lógica para abrir la ventana principal
        SwingUtilities.invokeLater(() -> {
            PrincipalFrame principalFrame = new PrincipalFrame();
            principalFrame.setVisible(true);
        });
        dispose(); // Cierra la ventana de inicio de sesión
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

class PrincipalFrame extends JFrame {
    private JButton studentDetailsButton;
    private JButton courseDetailsButton;
    private JButton registrationButton;

    public PrincipalFrame() {
        setTitle("Menú Principal");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        studentDetailsButton = new JButton("Detalles del Estudiante");
        courseDetailsButton = new JButton("Detalles del Curso");
        registrationButton = new JButton("Inscripción");

        // Establecer nuevos colores
        Color backgroundColor = new Color(173, 216, 230); // Azul Claro
        Color buttonColor = new Color(50, 205, 50); // Verde Lima

        panel.setBackground(backgroundColor);
        studentDetailsButton.setBackground(buttonColor);
        courseDetailsButton.setBackground(buttonColor);
        registrationButton.setBackground(buttonColor);

        studentDetailsButton.addActionListener(e -> abrirFormulario(new StudentDetailsFrame()));
        courseDetailsButton.addActionListener(e -> abrirFormulario(new CourseDetailsFrame()));
        registrationButton.addActionListener(e -> abrirFormulario(new RegistrationFrame()));

        panel.add(studentDetailsButton);
        panel.add(courseDetailsButton);
        panel.add(registrationButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void abrirFormulario(JFrame frame) {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PrincipalFrame::new);
    }
}