/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.ventanas;
import com.mycompany.administradorproyecto.bd.ConexionBD;
import com.mycompany.administradorproyecto.disenio.*;
import com.mycompany.administradorproyecto.model.Horarios;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USUARIO
 */
public class HorariosAdministrativoByC extends JFrame{
    private RoundedTextField txtMatriculaBusqueda;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scroll;
    private RoundedButton btnEliminar;

    public HorariosAdministrativoByC() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Consulta de Horarios Administrativos");
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
        btnBuscar.addActionListener(e -> buscarHorarioAdmin());
        panel.add(btnBuscar);

        // --- TABLA ---
        // La columna 0 es el ID, pero la ocultaremos visualmente
        String[] columnas = {"ID", "Matrícula", "Inicio", "Fin", "Días", "Checador"};
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
        
        // Ocultar la columna ID (índice 0)
        tablaResultados.getColumnModel().getColumn(0).setMinWidth(0);
        tablaResultados.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaResultados.getColumnModel().getColumn(0).setWidth(0);

        scroll = new JScrollPane(tablaResultados);
        scroll.setBounds(50, 100, 650, 300);
        scroll.setVisible(false);
        panel.add(scroll);

        btnEliminar = new RoundedButton("ELIMINAR SELECCIONADO", new Color(220, 79, 79));
        btnEliminar.setBounds(225, 420, 300, 45);
        btnEliminar.setVisible(false);
        btnEliminar.addActionListener(e -> eliminarRegistroSeleccionado());
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
            MenuAdministrativo ventanaAnterior = new MenuAdministrativo(); 
            ventanaAnterior.setVisible(true);

            // 2. Cierras por completo la ventana actual para que no se amontone
            this.dispose(); 
        });
        panel.add(btnVolver);
    }

    private void buscarHorarioAdmin() {
        String matricula = txtMatriculaBusqueda.getText().trim();
        if (matricula.isEmpty()) {
            CustomDialog.mostrar(this, "Ingrese una matrícula.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        modeloTabla.setRowCount(0);
        String sql = "SELECT id_horarioAdministrador, matricula, hora_inicio, hora_fin, dia_semana, id_checador FROM Horario_Administrador WHERE matricula = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(matricula));
            try (ResultSet rs = ps.executeQuery()) {
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    modeloTabla.addRow(new Object[]{
                        rs.getInt("id_horarioAdministrador"), // ID oculto
                        rs.getInt("matricula"), 
                        rs.getTime("hora_inicio"), 
                        rs.getTime("hora_fin"), 
                        rs.getString("dia_semana"), 
                        rs.getInt("id_checador")
                    });
                }

                if (encontrado) {
                    ajustarAnchoColumnas();
                    scroll.setVisible(true);
                    btnEliminar.setVisible(true);
                } else {
                    scroll.setVisible(false);
                    btnEliminar.setVisible(false);
                    CustomDialog.mostrar(this, "No se encontraron registros.", CustomDialog.Tipo.ADVERTENCIA);
                }
            }
        } catch (Exception e) {
            CustomDialog.mostrar(this, "Error: " + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    private void eliminarRegistroSeleccionado() {
        int fila = tablaResultados.getSelectedRow();
        if (fila == -1) {
            CustomDialog.mostrar(this, "Seleccione una fila de la tabla.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0); // Obtiene el ID oculto
        boolean confirm = CustomDialog.mostrarConfirmacion(this, "¿Eliminar este registro\nespecífico (ID: " + id + ")?", CustomDialog.Tipo.ADVERTENCIA);
        if (confirm) {
            String sql = "DELETE FROM Horario_Administrador WHERE id_horarioAdministrador = ?";
            try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                CustomDialog.mostrar(this, "Registro eliminado exitosamente.", CustomDialog.Tipo.EXITO);
                buscarHorarioAdmin(); // Refrescar tabla
            } catch (SQLException e) {
                CustomDialog.mostrar(this, "Error al eliminar: " + e.getMessage(), CustomDialog.Tipo.ERROR);
            }
        }
    }

    private void ajustarAnchoColumnas() {
        for (int i = 1; i < tablaResultados.getColumnCount(); i++) { // Empieza desde 1 para ignorar el ID
            int ancho = tablaResultados.getFontMetrics(tablaResultados.getTableHeader().getFont()).stringWidth(tablaResultados.getColumnName(i)) + 40;
            tablaResultados.getColumnModel().getColumn(i).setPreferredWidth(ancho);
        }
    }
}
