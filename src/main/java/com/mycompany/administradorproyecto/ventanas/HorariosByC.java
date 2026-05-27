package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD; 
import com.mycompany.administradorproyecto.disenio.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class HorariosByC extends JFrame {
    private RoundedTextField txtGrupoBusqueda;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scroll;
    private RoundedButton btnEliminar;

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

        // --- TABLA Y DISEÑO ---
        String[] columnas = {"Matrícula del Profesor", "Materia", "Inicio", "Fin", "Días", "Aula", "Checador"};
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
        tablaResultados.setSelectionBackground(new Color(200, 230, 255));
        
        tablaResultados.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                if (isSelected) c.setBackground(new Color(184, 207, 229));
                return c;
            }
        });
        
        scroll = new JScrollPane(tablaResultados);
        scroll.setBounds(50, 100, 650, 300);
        scroll.setVisible(false);
        panel.add(scroll);

        // --- BOTÓN ELIMINAR ---
        btnEliminar = new RoundedButton("ELIMINAR", new Color(220, 79, 79));
        btnEliminar.setBounds(250, 420, 250, 45);
        btnEliminar.setVisible(false);
        btnEliminar.addActionListener(e -> confirmarEliminacionGrupo());
        panel.add(btnEliminar);
        
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
            MenuProfesor ventanaAnterior = new MenuProfesor(); 
            ventanaAnterior.setVisible(true);

            // 2. Cierras por completo la ventana actual para que no se amontone
            this.dispose(); 
        });
        panel.add(btnVolver);
    }

    private void buscarHorario() {
        String idGrupo = txtGrupoBusqueda.getText().trim();
        if (idGrupo.isEmpty()) {
            CustomDialog.mostrar(this, "Por favor, ingrese un ID de grupo.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        modeloTabla.setRowCount(0);
        String sql = "SELECT matricula, clave_materia, hora_inicio, hora_fin, dia_semana, aula, id_checador FROM Horario_Clase WHERE clave_grupo = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    modeloTabla.addRow(new Object[]{
                        rs.getString("matricula"), rs.getString("clave_materia"), 
                        rs.getTime("hora_inicio"), rs.getTime("hora_fin"), 
                        rs.getString("dia_semana"), rs.getString("aula"), rs.getInt("id_checador")
                    });
                }

                if (encontrado) {
                    ajustarAnchoColumnas();
                    scroll.setVisible(true);
                    btnEliminar.setVisible(true);
                } else {
                    scroll.setVisible(false);
                    btnEliminar.setVisible(false);
                    CustomDialog.mostrar(this, "No se encontraron horarios para el grupo: " + idGrupo, CustomDialog.Tipo.ADVERTENCIA);
                }
                this.revalidate();
                this.repaint();
            }
        } catch (SQLException e) {
            CustomDialog.mostrar(this, "Error de conexión: " + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    private void confirmarEliminacionGrupo() {
        String idGrupo = txtGrupoBusqueda.getText().trim();
        boolean confirmacion = CustomDialog.mostrarConfirmacion(this,
            "¿Eliminar TODO el grupo " + idGrupo + "?\nEsta acción no se puede deshacer.",CustomDialog.Tipo.ADVERTENCIA);
        if (confirmacion) {
            eliminarGrupoCompleto(idGrupo);
        }
    }

    private void eliminarGrupoCompleto(String idGrupo) {
        String sql = "DELETE FROM Horario_Clase WHERE clave_grupo = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idGrupo);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                CustomDialog.mostrar(this, "Grupo eliminado exitosamente.", CustomDialog.Tipo.EXITO);
                modeloTabla.setRowCount(0);
                scroll.setVisible(false);
                btnEliminar.setVisible(false);
            }
        } catch (SQLException e) {
            CustomDialog.mostrar(this, "Error al eliminar: " + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    private void ajustarAnchoColumnas() {
        for (int i = 0; i < tablaResultados.getColumnCount(); i++) {
            int anchoEncabezado = tablaResultados.getFontMetrics(tablaResultados.getTableHeader().getFont())
                                                 .stringWidth(tablaResultados.getColumnName(i)) + 20;
            int anchoContenido = 0;
            for (int row = 0; row < tablaResultados.getRowCount(); row++) {
                Object value = tablaResultados.getValueAt(row, i);
                if (value != null) {
                    int anchoCelda = tablaResultados.getFontMetrics(tablaResultados.getFont())
                                                    .stringWidth(value.toString()) + 10;
                    if (anchoCelda > anchoContenido) anchoContenido = anchoCelda;
                }
            }
            int anchoFinal = Math.max(anchoEncabezado, anchoContenido);
            tablaResultados.getColumnModel().getColumn(i).setPreferredWidth(Math.min(anchoFinal, 200));
        }
    }
}