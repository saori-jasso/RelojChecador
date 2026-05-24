/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.model;

import com.mycompany.administradorproyecto.disenio.RoundedTextField;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author USUARIO
 */
public class Checadas extends JFrame {
    //Constructor
    public Checadas() {
        configurarVentana();
        crearComponentes();
    }
    
    //Título
    private void configurarVentana() {
        setTitle("Checadas");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 420);
        panel.setLayout(null);
        setContentPane(panel);
        
        // Crea la tabla de datos, define sus columnas, centra el texto y aplica colores alternos a las filas
        JLabel lblTitulo = new JLabel("CHECADAS");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 36));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(200, 10, 350, 50);
        panel.add(lblTitulo);

        //FECHA + BUSCAR 
        JLabel lblFecha = crearLabel("FECHA");
        lblFecha.setBounds(30, 75, 80, 30);
        panel.add(lblFecha);

        RoundedTextField txtFecha = new RoundedTextField();
        txtFecha.setText("DD/MM/YYYY");
        txtFecha.setBounds(115, 75, 150, 35);
        panel.add(txtFecha);

        Color amarillo = new Color(255, 200, 80);
        RoundedButton btnBuscar = new RoundedButton("Buscar", amarillo);
        btnBuscar.setBounds(570, 75, 120, 35);
        /*
        btnBuscar.addActionListener(e -> { });
        */
        panel.add(btnBuscar);

        //TABLA 
        String[] columnas = {"id Checada", "id Empleado", "id Checador", "Hora", "id Horario"};

        Object[][] datos = {
            {"0000", "0000", "0000", "00:00", "0000"},
            {"0000", "0000", "0000", "00:00", "0000"},
            {"0000", "0000", "0000", "00:00", "0000"}
        };

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla de solo lectura
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(45);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(5, 5));

        // Color encabezado — naranja/amarillo
        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(255, 190, 80));
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setReorderingAllowed(false);

        // Renderer para alternar colores de filas (naranja claro)
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            Color filaNormal = new Color(255, 220, 160);
            Color filaAlterna = new Color(255, 235, 190);

            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                setBackground(row % 2 == 0 ? filaNormal : filaAlterna);
                setForeground(Color.BLACK);
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return this;
            }
        });

        // Centrar encabezados
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(new Color(255, 190, 80));
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 130, 680, 240);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(255, 220, 160));
        panel.add(scrollPane);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
}