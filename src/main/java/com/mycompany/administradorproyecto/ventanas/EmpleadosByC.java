package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD;
import com.mycompany.administradorproyecto.disenio.CustomDialog;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.disenio.RoundedTextField;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EmpleadosByC extends JFrame {
    
    // Variables globales
    private RoundedTextField txtMatriculaBusqueda;
    private RoundedTextField txtNombre, txtApellidoP, txtApellidoM, txtFechaNac, txtFechaIng, txtPuesto;
    private JLabel lblNombreEmp, lblApellidoP, lblApellidoM, lblFechaNac, lblFechaIng, lblPuestoEmp, lblEstadoHuella;
    
    public EmpleadosByC() {
        configurarVentana();
        crearComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Bajas y Consultas de Empleados");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 550);
        panel.setLayout(null);
        setContentPane(panel);

        // TÍTULO (Bajado a Y=40)
        JLabel lblTitulo = new JLabel("BAJAS Y CONSULTAS DE EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 40, 650, 40);
        panel.add(lblTitulo);

        // =========================================================================
        // ZONA DE BÚSQUEDA (Siempre visible)
        // =========================================================================
        JLabel lblBuscar = crearLabel("INGRESAR MATRÍCULA:");
        lblBuscar.setBounds(70, 110, 200, 30);
        panel.add(lblBuscar);
        
        txtMatriculaBusqueda = new RoundedTextField();
        txtMatriculaBusqueda.setBounds(270, 110, 120, 35);
        panel.add(txtMatriculaBusqueda);
        
        Color azul = new Color(79, 190, 220);
        Color rosa = new Color(255, 100, 130);
        
        RoundedButton btnConsulta = new RoundedButton("Buscar", azul);
        btnConsulta.setBounds(410, 110, 110, 35);
        btnConsulta.addActionListener(e -> buscarEmpleado());
        panel.add(btnConsulta);
        
        RoundedButton btnBaja = new RoundedButton("Eliminar", rosa);
        btnBaja.setBounds(540, 110, 110, 35);
        btnBaja.addActionListener(e -> confirmarYEliminar());
        panel.add(btnBaja);

        // =========================================================================
        // ZONA DE RESULTADOS (Inicialmente oculta)
        // =========================================================================
        
        // FILA 1
        lblNombreEmp = crearLabel("NOMBRE");
        lblNombreEmp.setBounds(50, 190, 130, 30);
        panel.add(lblNombreEmp);

        txtNombre = new RoundedTextField();
        txtNombre.setBounds(180, 190, 140, 35);
        txtNombre.setEditable(false); // Solo lectura
        panel.add(txtNombre);
        
        lblPuestoEmp = crearLabel("PUESTO");
        lblPuestoEmp.setBounds(370, 190, 130, 30);
        panel.add(lblPuestoEmp);

        txtPuesto = new RoundedTextField();
        txtPuesto.setBounds(500, 190, 140, 35);
        txtPuesto.setEditable(false);
        panel.add(txtPuesto);
        
        // FILA 2
        lblApellidoP = crearLabel("APELLIDO P.");
        lblApellidoP.setBounds(50, 260, 130, 30);
        panel.add(lblApellidoP);

        txtApellidoP = new RoundedTextField();
        txtApellidoP.setBounds(180, 260, 140, 35);
        txtApellidoP.setEditable(false);
        panel.add(txtApellidoP);
        
        lblFechaNac = crearLabel("F. NACIMIENTO");
        lblFechaNac.setBounds(370, 260, 130, 30);
        panel.add(lblFechaNac);

        txtFechaNac = new RoundedTextField();
        txtFechaNac.setBounds(500, 260, 140, 35);
        txtFechaNac.setEditable(false);
        panel.add(txtFechaNac);
        
        // FILA 3
        lblApellidoM = crearLabel("APELLIDO M.");
        lblApellidoM.setBounds(50, 330, 130, 30);
        panel.add(lblApellidoM);

        txtApellidoM = new RoundedTextField();
        txtApellidoM.setBounds(180, 330, 140, 35);
        txtApellidoM.setEditable(false);
        panel.add(txtApellidoM);
        
        lblFechaIng = crearLabel("F. INGRESO");
        lblFechaIng.setBounds(370, 330, 140, 30);
        panel.add(lblFechaIng);

        txtFechaIng = new RoundedTextField();
        txtFechaIng.setBounds(500, 330, 140, 35);
        txtFechaIng.setEditable(false);
        panel.add(txtFechaIng);

        // HUELLA
        lblEstadoHuella = new JLabel("HUELLA: Pendiente");
        lblEstadoHuella.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstadoHuella.setForeground(Color.GRAY);
         // Aumentamos el ancho a 400 para que quepa todo el texto
        lblEstadoHuella.setBounds(280, 410, 400, 30);
        panel.add(lblEstadoHuella);

        // Ocultar resultados al arrancar la ventana
        ocultarResultados();
        
        //Botones para regresar-------------------------------
        Color morado = new Color(130, 100, 210);
        Font fuenteNavegacion = new Font("Arial", Font.BOLD, 14);
        RoundedButton btnHome = new RoundedButton("⌂", morado);
        btnHome.setFont(new Font("Arial", Font.BOLD, 18)); // Un poco más grande para el símbolo de casa
        btnHome.setBounds(75, 20, 50, 35); // Al lado del botón volver
            btnHome.addActionListener(e -> {
            // 1. Abrir el menú principal
            new Menu().setVisible(true);
            // 2. Destruir la ventana actual
            this.dispose(); 
            });
        panel.add(btnHome);
        
        
        // ⏪️ Botón Volver Atrás (Flecha izquierda)
        RoundedButton btnVolver = new RoundedButton("←", morado);
        btnVolver.setFont(fuenteNavegacion);
        btnVolver.setBounds(20, 20, 50, 35); // Esquina superior izquierda
        btnVolver.addActionListener(e -> {
            // 1. Instancias la ventana a la que quieres regresar (o usas una referencia)
            // Supongamos que venías de una ventana llamada "GestionHorarios"
            MenuAdministrativo ventanaAnterior = new MenuAdministrativo(); 
            ventanaAnterior.setVisible(true);

            // 2. Cierras por completo la ventana actual para que no se amontone
            this.dispose(); 
        });
        panel.add(btnVolver);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    private void ocultarResultados() {
        lblNombreEmp.setVisible(false); txtNombre.setVisible(false);
        lblApellidoP.setVisible(false); txtApellidoP.setVisible(false);
        lblApellidoM.setVisible(false); txtApellidoM.setVisible(false);
        lblPuestoEmp.setVisible(false); txtPuesto.setVisible(false);
        lblFechaNac.setVisible(false); txtFechaNac.setVisible(false);
        lblFechaIng.setVisible(false); txtFechaIng.setVisible(false);
        lblEstadoHuella.setVisible(false);
    }

    private void mostrarResultados() {
        lblNombreEmp.setVisible(true); txtNombre.setVisible(true);
        lblApellidoP.setVisible(true); txtApellidoP.setVisible(true);
        lblApellidoM.setVisible(true); txtApellidoM.setVisible(true);
        lblPuestoEmp.setVisible(true); txtPuesto.setVisible(true);
        lblFechaNac.setVisible(true); txtFechaNac.setVisible(true);
        lblFechaIng.setVisible(true); txtFechaIng.setVisible(true);
        lblEstadoHuella.setVisible(true);
    }

    // =========================================================
    // LÓGICA DE BÚSQUEDA
    // =========================================================
    private void buscarEmpleado() {
        String matricula = txtMatriculaBusqueda.getText().trim();
        
        if (matricula.isEmpty()) {
            CustomDialog.mostrar(this, "Por favor, ingrese una matrícula.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        String sql = "SELECT nombre, apellidop, apellidom, puesto, fecha_nac, fecha_ingreso, huella FROM Empleados WHERE matricula = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Llenar los campos
                    txtNombre.setText(rs.getString("nombre"));
                    txtApellidoP.setText(rs.getString("apellidop"));
                    txtApellidoM.setText(rs.getString("apellidom"));
                    txtPuesto.setText(rs.getString("puesto"));
                    txtFechaNac.setText(rs.getString("fecha_nac"));
                    txtFechaIng.setText(rs.getString("fecha_ingreso"));

                    // Checar la huella
                    byte[] huella = rs.getBytes("huella");
                    if (huella != null && huella.length > 0) {
                        lblEstadoHuella.setText("HUELLA: Registrada Exitosamente");
                        lblEstadoHuella.setForeground(new Color(79, 190, 220)); // Azul si hay huella
                    } else {
                        lblEstadoHuella.setText("HUELLA: No Registrada");
                        lblEstadoHuella.setForeground(new Color(255, 100, 130)); // Rosa si no hay
                    }

                    // Prender la visibilidad
                    mostrarResultados();
                } else {
                    ocultarResultados();
                    CustomDialog.mostrar(this, "No se encontró ningún empleado\ncon esa matrícula.", CustomDialog.Tipo.ADVERTENCIA);
                }
            }
        } catch (SQLException ex) {
            CustomDialog.mostrar(this, "Error de base de datos:\n" + ex.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    // =========================================================
    // LÓGICA DE ELIMINACIÓN
    // =========================================================
    private void confirmarYEliminar() {
        String matricula = txtMatriculaBusqueda.getText().trim();
        
        if (matricula.isEmpty()) {
            CustomDialog.mostrar(this, "Por favor, ingrese la matrícula\ndel empleado a eliminar.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        // Usamos nuestro nuevo CustomDialog que devuelve true o false
        boolean seguro = CustomDialog.mostrarConfirmacion(
                this, 
                "¿Está completamente seguro de que\ndesea ELIMINAR al empleado\ncon matrícula " + matricula + "?", 
                CustomDialog.Tipo.ADVERTENCIA
        );

        // Si presionó el botón "Sí, eliminar"
        if (seguro) {
            ejecutarBorrado(matricula);
        }
    }

    private void ejecutarBorrado(String matricula) {
        String sql = "DELETE FROM Empleados WHERE matricula = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                ocultarResultados();
                txtMatriculaBusqueda.setText("");
                CustomDialog.mostrar(this, "Empleado eliminado del\nsistema correctamente.", CustomDialog.Tipo.EXITO);
            } else {
                CustomDialog.mostrar(this, "No se pudo eliminar.\nVerifique que la matrícula exista.", CustomDialog.Tipo.ERROR);
            }
        } catch (SQLException ex) {
            CustomDialog.mostrar(this, "Error al eliminar:\n" + ex.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }
}