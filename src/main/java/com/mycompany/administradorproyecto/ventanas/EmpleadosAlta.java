package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD; 
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.disenio.RoundedComboBox;
import com.mycompany.administradorproyecto.disenio.RoundedTextField;
import com.toedter.calendar.JDateChooser; // ¡NUEVO!: Importamos la librería del calendario

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class EmpleadosAlta extends JFrame {
    
    // Declaramos los componentes como variables globales
    private RoundedTextField txtNombre, txtApellidoP, txtApellidoM;
    private RoundedComboBox cmbPuesto;
    private JDateChooser jdFechaNac, jdFechaIng; // ¡NUEVO!: Cambiamos a JDateChooser
    private RoundedButton btnRegistrar;

    // Constructor
    public EmpleadosAlta() {
        configurarVentana();
        crearComponentes();
    }
    
    // Vista general de la ventana
    private void configurarVentana() {
        setTitle("Alta de Empleados");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        // Diseño del fondo
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 500);
        panel.setLayout(null);
        setContentPane(panel);

        // TÍTULO principal
        JLabel lblTitulo = new JLabel("ALTA DE EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(175, 40, 400, 40); 
        panel.add(lblTitulo);

        // =========================================================================
        // COLUMNA IZQUIERDA: DATOS GENERALES
        // =========================================================================
        
        // NOMBRE 
        JLabel lblNombre = crearLabel("NOMBRE(S)");
        lblNombre.setBounds(50, 140, 140, 30);
        panel.add(lblNombre);

        txtNombre = new RoundedTextField();
        txtNombre.setBounds(200, 140, 160, 35);
        panel.add(txtNombre);

        // APELLIDO PATERNO
        JLabel lblApellidoP = crearLabel("APELLIDO P.");
        lblApellidoP.setBounds(50, 220, 140, 30);
        panel.add(lblApellidoP);

        txtApellidoP = new RoundedTextField();
        txtApellidoP.setBounds(200, 220, 160, 35);
        panel.add(txtApellidoP);

        // APELLIDO MATERNO 
        JLabel lblApellidoM = crearLabel("APELLIDO M.");
        lblApellidoM.setBounds(50, 300, 140, 30);
        panel.add(lblApellidoM);

        txtApellidoM = new RoundedTextField();
        txtApellidoM.setBounds(200, 300, 160, 35);
        panel.add(txtApellidoM);

        // =========================================================================
        // COLUMNA DERECHA: PUESTO Y CALENDARIOS
        // =========================================================================
        
        // PUESTO 
        JLabel lblPuesto = crearLabel("PUESTO");
        lblPuesto.setBounds(400, 140, 130, 30);
        panel.add(lblPuesto);

        cmbPuesto = new RoundedComboBox(new String[]{"Profesor", "Administrativo", "Ambos"});
        cmbPuesto.setBounds(540, 140, 160, 35);
        cmbPuesto.setSelectedIndex(-1); 
        panel.add(cmbPuesto);

        // FECHA DE NACIMIENTO (JDateChooser)
        JLabel lblFechaNac = crearLabel("F. NACIMIENTO");
        lblFechaNac.setBounds(400, 220, 130, 30);
        panel.add(lblFechaNac);

        jdFechaNac = new JDateChooser();
        jdFechaNac.setDateFormatString("yyyy-MM-dd"); // Obligamos a que la fecha se vea así
        jdFechaNac.setBounds(540, 220, 160, 35);
        panel.add(jdFechaNac);

        // FECHA DE INGRESO (JDateChooser)
        JLabel lblFechaIng = crearLabel("F. INGRESO");
        lblFechaIng.setBounds(400, 300, 130, 30);
        panel.add(lblFechaIng);

        jdFechaIng = new JDateChooser();
        jdFechaIng.setDateFormatString("yyyy-MM-dd");
        jdFechaIng.setBounds(540, 300, 160, 35);
        panel.add(jdFechaIng);

        // =========================================================================
        // BOTÓN DE ACCIÓN CENTRALIZADO 
        // =========================================================================
        Color rosa = new Color(255, 100, 130);
        btnRegistrar = new RoundedButton("Terminar registro", rosa);
        btnRegistrar.setBounds(265, 400, 220, 40);
        panel.add(btnRegistrar);

        // Asignación del evento clic
        btnRegistrar.addActionListener(e -> ejecutarAltaEmpleado());
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
    
    // --- LÓGICA DE VALIDACIÓN E INSERCIÓN EN AWS ---
    private void ejecutarAltaEmpleado() {
        String nombre = txtNombre.getText().trim();
        String apPaterno = txtApellidoP.getText().trim();
        String apMaterno = txtApellidoM.getText().trim();

        // 1. Validar textos
        if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos de texto son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validar puesto
        if (cmbPuesto.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un puesto para el empleado.", "Falta Puesto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String puesto = cmbPuesto.getSelectedItem().toString();

        // 3. Validar que los calendarios no estén vacíos
        if (jdFechaNac.getDate() == null || jdFechaIng.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione ambas fechas en el calendario.", "Fechas Vacías", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 4. Convertir la fecha del calendario de Java (java.util.Date) a formato SQL (java.sql.Date)
        java.sql.Date fechaNacimiento = new java.sql.Date(jdFechaNac.getDate().getTime());
        java.sql.Date fechaIngreso = new java.sql.Date(jdFechaIng.getDate().getTime());

        // 5. Guardado en la nube
        try (Connection con = ConexionBD.conectar()) {
            if (con != null) {
                String sql = "INSERT INTO Empleados (nombre, apellidop, apellidom, fecha_nac, puesto, fecha_ingreso, huella) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, nombre);
                    ps.setString(2, apPaterno);
                    ps.setString(3, apMaterno);
                    ps.setDate(4, fechaNacimiento); // Pasamos las fechas convertidas
                    ps.setString(5, puesto);
                    ps.setDate(6, fechaIngreso);
                    
                    // Simulación de la huella dactilar
                    String huellaSimuladaStr = "01010101110011010101"; 
                    byte[] huellaBytes = huellaSimuladaStr.getBytes();
                    ps.setBytes(7, huellaBytes); 

                    ps.executeUpdate();
                    
                    try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            long matriculaGenerada = rs.getLong(1);
                            JOptionPane.showMessageDialog(this, 
                                "¡Empleado guardado exitosamente en AWS!\n\nMATRÍCULA ASIGNADA: " + matriculaGenerada, 
                                "Registro Exitoso", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    
                    limpiarFormulario();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en AWS RDS:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        cmbPuesto.setSelectedIndex(-1);
        jdFechaNac.setDate(null); // Esto blanquea el calendario
        jdFechaIng.setDate(null); // Esto blanquea el calendario
    }
}