/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author USUARIO
 */
public class BajasYConsultas extends JFrame {

    public BajasYConsultas() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Bajas y Consultas");
        setSize(700, 530);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 700, 530);
        panel.setLayout(null);
        setContentPane(panel);

        // ── TÍTULO ──────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("BAJAS Y CONSULTAS");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 34));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(100, 10, 500, 50);
        panel.add(lblTitulo);

        Color amarillo = new Color(255, 200, 80);
        Color rosa     = new Color(255, 100, 130);

        // ── SECCIÓN IZQUIERDA: DEPARTAMENTO ─────────────────────

        // Fila 1 izq — ID DPT.
        JLabel lblIdDpt = crearLabel("ID DPT.");
        lblIdDpt.setBounds(20, 75, 100, 30);
        panel.add(lblIdDpt);

        RoundedTextField txtIdDpt = new RoundedTextField();
        txtIdDpt.setText("000000");
        txtIdDpt.setBounds(125, 75, 120, 35);
        panel.add(txtIdDpt);

        // Fila 2 izq — NOMBRE
        JLabel lblNombreDpt = crearLabel("NOMBRE");
        lblNombreDpt.setBounds(20, 130, 100, 30);
        panel.add(lblNombreDpt);

        RoundedTextField txtNombreDpt = new RoundedTextField();
        txtNombreDpt.setText("LOREM");
        txtNombreDpt.setBounds(125, 130, 120, 35);
        panel.add(txtNombreDpt);

        // Botones izq — Buscar / Eliminar departamento
        RoundedButton btnBuscarDpt = new RoundedButton("Buscar", amarillo);
        btnBuscarDpt.setBounds(50, 185, 100, 40);
        /*
        btnBuscarDpt.addActionListener(e -> { });
        */
        panel.add(btnBuscarDpt);

        RoundedButton btnEliminarDpt = new RoundedButton("Eliminar", rosa);
        btnEliminarDpt.setBounds(165, 185, 100, 40);
        /*
        btnEliminarDpt.addActionListener(e -> { });
        */
        panel.add(btnEliminarDpt);

        // ── SECCIÓN DERECHA: UBICACIÓN ──────────────────────────

        // Fila 1 der — ID UBICACIÓN
        JLabel lblIdUbicacion = crearLabel("ID UBICACIÓN");
        lblIdUbicacion.setBounds(330, 75, 150, 30);
        panel.add(lblIdUbicacion);

        RoundedTextField txtIdUbicacion = new RoundedTextField();
        txtIdUbicacion.setText("000000");
        txtIdUbicacion.setBounds(490, 75, 120, 35);
        panel.add(txtIdUbicacion);

        // Fila 2 der — NOMBRE
        JLabel lblNombreUbicacion = crearLabel("NOMBRE");
        lblNombreUbicacion.setBounds(360, 130, 100, 30);
        panel.add(lblNombreUbicacion);

        RoundedTextField txtNombreUbicacion = new RoundedTextField();
        txtNombreUbicacion.setText("LOREM");
        txtNombreUbicacion.setBounds(490, 130, 120, 35);
        panel.add(txtNombreUbicacion);

        // Botones der — Buscar / Eliminar ubicación
        RoundedButton btnBuscarUbicacion = new RoundedButton("Buscar", amarillo);
        btnBuscarUbicacion.setBounds(390, 185, 100, 40);
        /*
        btnBuscarUbicacion.addActionListener(e -> { });
        */
        panel.add(btnBuscarUbicacion);

        RoundedButton btnEliminarUbicacion = new RoundedButton("Eliminar", rosa);
        btnEliminarUbicacion.setBounds(505, 185, 100, 40);
        /*
        btnEliminarUbicacion.addActionListener(e -> { });
        */
        panel.add(btnEliminarUbicacion);

        // ── SECCIÓN CENTRAL: CHECADOR ───────────────────────────

        // Fila 1 centro — ID CHECADOR
        JLabel lblIdChecador = crearLabel("ID CHECADOR");
        lblIdChecador.setBounds(170, 260, 160, 30);
        panel.add(lblIdChecador);

        RoundedTextField txtIdChecador = new RoundedTextField();
        txtIdChecador.setText("000000");
        txtIdChecador.setBounds(340, 260, 120, 35);
        panel.add(txtIdChecador);

        // Fila 2 centro — NOMBRE
        JLabel lblNombreChecador = crearLabel("NOMBRE");
        lblNombreChecador.setBounds(200, 315, 120, 30);
        panel.add(lblNombreChecador);

        RoundedTextField txtNombreChecador = new RoundedTextField();
        txtNombreChecador.setText("LOREM");
        txtNombreChecador.setBounds(340, 315, 120, 35);
        panel.add(txtNombreChecador);

        // Fila 3 centro — UBICACIÓN
        JLabel lblUbicacionChecador = crearLabel("UBICACIÓN");
        lblUbicacionChecador.setBounds(190, 370, 130, 30);
        panel.add(lblUbicacionChecador);

        RoundedTextField txtUbicacionChecador = new RoundedTextField();
        txtUbicacionChecador.setText("LOREM");
        txtUbicacionChecador.setBounds(340, 370, 120, 35);
        panel.add(txtUbicacionChecador);

        // Botones centro — Buscar / Eliminar checador
        RoundedButton btnBuscarChecador = new RoundedButton("Buscar", amarillo);
        btnBuscarChecador.setBounds(240, 425, 100, 40);
        /*
        btnBuscarChecador.addActionListener(e -> { });
        */
        panel.add(btnBuscarChecador);

        RoundedButton btnEliminarChecador = new RoundedButton("Eliminar", rosa);
        btnEliminarChecador.setBounds(360, 425, 100, 40);
        /*
        btnEliminarChecador.addActionListener(e -> { });
        */
        panel.add(btnEliminarChecador);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
}