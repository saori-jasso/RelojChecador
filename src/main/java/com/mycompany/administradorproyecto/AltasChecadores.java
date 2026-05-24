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
public class AltasChecadores extends JFrame {

    public AltasChecadores() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Altas");
         setSize(700, 530);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 700, 480);
        panel.setLayout(null);
        setContentPane(panel);

        // ── TÍTULO ──────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("ALTAS");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 36));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(200, 10, 300, 50);
        panel.add(lblTitulo);

        Color morado = new Color(130, 130, 240);

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

        // Botón Registrar departamento
        RoundedButton btnRegistrarDpt = new RoundedButton(
                "<html><center>Registrar<br>departamento</center></html>", morado);
        btnRegistrarDpt.setBounds(50, 185, 170, 55);
        /*
        btnRegistrarDpt.addActionListener(e -> {
            // lógica de registro de departamento
        });
        */
        panel.add(btnRegistrarDpt);

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

        // Botón Registrar ubicación
        RoundedButton btnRegistrarUbicacion = new RoundedButton("Registrar ubicación", morado);
        btnRegistrarUbicacion.setBounds(390, 195, 170, 40);
        /*
        btnRegistrarUbicacion.addActionListener(e -> {
            // lógica de registro de ubicación
        });
        */
        panel.add(btnRegistrarUbicacion);

        // ── SECCIÓN CENTRAL: CHECADOR ───────────────────────────

        // Fila 1 centro — ID CHECADOR
        JLabel lblIdChecador = crearLabel("ID CHECADOR");
        lblIdChecador.setBounds(170, 270, 160, 30);
        panel.add(lblIdChecador);

        RoundedTextField txtIdChecador = new RoundedTextField();
        txtIdChecador.setText("000000");
        txtIdChecador.setBounds(340, 270, 120, 35);
        panel.add(txtIdChecador);

        // Fila 2 centro — NOMBRE
        JLabel lblNombreChecador = crearLabel("NOMBRE");
        lblNombreChecador.setBounds(200, 320, 120, 30);
        panel.add(lblNombreChecador);

        RoundedTextField txtNombreChecador = new RoundedTextField();
        txtNombreChecador.setText("LOREM");
        txtNombreChecador.setBounds(340, 320, 120, 35);
        panel.add(txtNombreChecador);

        // Fila 3 centro — UBICACIÓN (combo)
        JLabel lblUbicacionChecador = crearLabel("UBICACIÓN");
        lblUbicacionChecador.setBounds(190, 370, 130, 30);
        panel.add(lblUbicacionChecador);

        RoundedComboBox cmbUbicacion = new RoundedComboBox(
                new String[]{"LOREM", "Ubicación 1", "Ubicación 2"});
        cmbUbicacion.setBounds(340, 370, 120, 35);
        panel.add(cmbUbicacion);

        // Botón Registrar checador
        RoundedButton btnRegistrarChecador = new RoundedButton("Registrar checador", morado);
        btnRegistrarChecador.setBounds(250, 420, 190, 40);
        /*
        btnRegistrarChecador.addActionListener(e -> {
            // lógica de registro de checador
        });
        */
        panel.add(btnRegistrarChecador);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
}