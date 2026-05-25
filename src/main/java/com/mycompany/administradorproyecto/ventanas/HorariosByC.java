package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD;
import com.mycompany.administradorproyecto.disenio.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HorariosByC extends JFrame {

    private RoundedTextField txtGrupoBusqueda;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scroll;

    public HorariosByC() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Consulta de Horarios");
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

        // --- BÚSQUEDA ---
        JLabel lblBusqueda = new JLabel("ID GRUPO:");
        lblBusqueda.setFont(new Font("Arial", Font.BOLD, 18));
        lblBusqueda.setBounds(150, 30, 100, 35);
        panel.add(lblBusqueda);

        txtGrupoBusqueda = new RoundedTextField();
        txtGrupoBusqueda.setBounds(260, 30, 150, 35);
        panel.add(txtGrupoBusqueda);

        Color azul = new Color(79, 190, 220);
        RoundedButton btnBuscar = new RoundedButton("BUSCAR", azul);
        btnBuscar.setBounds(430, 30, 120, 35);
        btnBuscar.addActionListener(e -> buscarHorario());
        panel.add(btnBuscar);

        // Tabla que al inicio va a estar innvisible
        String[] columnas = {"Matrícula", "Materia", "Inicio", "Fin", "Días", "Aula", "Checador"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        
        scroll = new JScrollPane(tablaResultados);
        scroll.setBounds(50, 100, 650, 350);
        scroll.setVisible(false); // La tabla no se ve hasta que haya resultados
        panel.add(scroll);
    }

    private void buscarHorario() {
        String idGrupo = txtGrupoBusqueda.getText().trim();

        if (idGrupo.isEmpty()) {
            CustomDialog.mostrar(this, "Por favor, ingrese un ID de grupo.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        modeloTabla.setRowCount(0); // Limpiar tabla antes de nueva búsqueda

        String sql = "SELECT matricula, clave_materia, hora_inicio, hora_fin, dia_semana, aula, id_checador " +
                     "FROM Horario_Clase WHERE clave_grupo = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idGrupo); // Sigue igual porque es la búsqueda del usuario
            try (ResultSet rs = ps.executeQuery()) {
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    modeloTabla.addRow(new Object[]{
                        rs.getString("matricula"),
                        rs.getString("clave_materia"),
                        rs.getTime("hora_inicio"),
                        rs.getTime("hora_fin"),
                        rs.getString("dia_semana"),
                        rs.getString("aula"),
                        rs.getInt("id_checador") 
                    });
                }

                if (encontrado) {
                    scroll.setVisible(true); // Mostrar tabla
                } else {
                    scroll.setVisible(false); // Ocultar tabla si no hay datos
                    CustomDialog.mostrar(this, "No se encontraron horarios para el grupo: " + idGrupo, CustomDialog.Tipo.ADVERTENCIA);
                }
                this.revalidate();
                this.repaint();
            }
        } catch (SQLException e) {
            CustomDialog.mostrar(this, "Error al conectar con la base de datos:\n" + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }
}