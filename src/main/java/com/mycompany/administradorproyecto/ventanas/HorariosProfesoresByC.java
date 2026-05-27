package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD;
import com.mycompany.administradorproyecto.disenio.*;
import com.mycompany.administradorproyecto.model.Empleados;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HorariosProfesoresByC extends JFrame {
    private RoundedTextField txtMatriculaBusqueda;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scroll;

    public HorariosProfesoresByC() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Visualizar Horarios del Profesor");
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

        JLabel lblBusqueda = new JLabel("MATRÍCULA:");
        lblBusqueda.setFont(new Font("Arial", Font.BOLD, 18));
        lblBusqueda.setBounds(150, 30, 120, 35);
        panel.add(lblBusqueda);

        txtMatriculaBusqueda = new RoundedTextField();
        txtMatriculaBusqueda.setBounds(280, 30, 150, 35);
        panel.add(txtMatriculaBusqueda);

        RoundedButton btnBuscar = new RoundedButton("BUSCAR", new Color(79, 190, 220));
        btnBuscar.setBounds(450, 30, 120, 35);
        btnBuscar.addActionListener(e -> buscarHorarioProfesor());
        panel.add(btnBuscar);

        // --- TABLA DE CONSULTA ---
        String[] columnas = {"Matrícula", "Grupo", "Materia", "Día", "Inicio", "Fin", "Aula"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setRowHeight(30);
        tablaResultados.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaResultados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaResultados.getTableHeader().setBackground(new Color(79, 190, 220));
        tablaResultados.getTableHeader().setForeground(Color.WHITE);

        scroll = new JScrollPane(tablaResultados);
        scroll.setBounds(50, 100, 650, 350);
        scroll.setVisible(false);
        panel.add(scroll);
        
        // Botones para regresar
        Color morado = new Color(130, 100, 210);
        RoundedButton btnHome = new RoundedButton("⌂", morado);
        btnHome.setFont(new Font("Arial", Font.BOLD, 18));
        btnHome.setBounds(75, 20, 50, 35);
        btnHome.addActionListener(e -> {
            new Menu().setVisible(true);
            this.dispose();
        });
        panel.add(btnHome);
        
        RoundedButton btnVolver = new RoundedButton("←", morado);
        btnVolver.setBounds(20, 20, 50, 35);
        btnVolver.addActionListener(e -> {
            new Empleados().setVisible(true);
            this.dispose();
        });
        panel.add(btnVolver);
    }

    private void buscarHorarioProfesor() {
        String matricula = txtMatriculaBusqueda.getText().trim();
        if (matricula.isEmpty()) {
            CustomDialog.mostrar(this, "Ingrese la matrícula del profesor.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        modeloTabla.setRowCount(0);
        // Consulta a la tabla de clases
        String sql = "SELECT matricula, clave_grupo, clave_materia, dia_semana, hora_inicio, hora_fin, aula FROM Horario_Clase WHERE matricula = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(matricula));
            try (ResultSet rs = ps.executeQuery()) {
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    modeloTabla.addRow(new Object[]{
                        rs.getInt("matricula"),
                        rs.getString("clave_grupo"),
                        rs.getString("clave_materia"),
                        rs.getString("dia_semana"),
                        rs.getTime("hora_inicio"),
                        rs.getTime("hora_fin"),
                        rs.getString("aula")
                    });
                }

                if (encontrado) {
                    scroll.setVisible(true);
                } else {
                    scroll.setVisible(false);
                    CustomDialog.mostrar(this, "No hay horarios registrados para este profesor.", CustomDialog.Tipo.ADVERTENCIA);
                }
            }
        } catch (Exception e) {
            CustomDialog.mostrar(this, "Error: " + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }
}