package com.mycompany.administradorproyecto.model;

import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.ventanas.EmpleadosByC;
import com.mycompany.administradorproyecto.ventanas.EmpleadosAlta;
import com.mycompany.administradorproyecto.ventanas.*; // Importamos la nueva ventana
import java.awt.*;
import javax.swing.*;

public class Empleados extends JFrame {

    public Empleados() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Menú Empleados");
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

        JLabel lblTitulo = new JLabel("EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(225, 40, 300, 50);
        panel.add(lblTitulo);

        Color verde = new Color(46, 204, 113);
        Color naranja = new Color(243, 156, 18);
        Color azul = new Color(79, 190, 220); // Color para visualizar

        int botonX = 245;
        int botonAncho = 260;
        int botonAlto = 55;

        // Botón Altas
        RoundedButton btnHorariosAltas = new RoundedButton("Altas", verde);
        btnHorariosAltas.setBounds(botonX, 130, botonAncho, botonAlto);
        btnHorariosAltas.addActionListener(e -> {
            new EmpleadosAlta().setVisible(true);
            this.dispose();
        });
        panel.add(btnHorariosAltas);

        // Botón Visualizar Horarios (NUEVO)
        RoundedButton btnVisualizar = new RoundedButton("Visualizar Horarios", azul);
        btnVisualizar.setBounds(botonX, 215, botonAncho, botonAlto);
        btnVisualizar.addActionListener(e -> {
            new HorariosProfesoresByC().setVisible(true);
            this.dispose();
        });
        panel.add(btnVisualizar);

        // Botón Bajas y consultas
        RoundedButton btnHorariosByC = new RoundedButton("Bajas y consultas", naranja);
        btnHorariosByC.setBounds(botonX, 300, botonAncho, botonAlto);
        btnHorariosByC.addActionListener(e -> {
            new EmpleadosByC().setVisible(true);
            this.dispose();
        });
        panel.add(btnHorariosByC);

        // Botones para regresar
        Color morado = new Color(130, 100, 210);
        RoundedButton btnHome = new RoundedButton("⌂", morado);
        btnHome.setFont(new Font("Arial", Font.BOLD, 18));
        btnHome.setBounds(75, 20, 50, 35);
        btnHome.addActionListener(e -> {
            new com.mycompany.administradorproyecto.ventanas.Menu().setVisible(true);
            this.dispose();
        });
        panel.add(btnHome);
    }
}