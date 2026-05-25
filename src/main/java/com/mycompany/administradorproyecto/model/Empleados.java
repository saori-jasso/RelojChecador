/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.model;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.ventanas.EmpleadosByC;
import com.mycompany.administradorproyecto.ventanas.EmpleadosAlta;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class Empleados extends JFrame{
    //Constructor
    public Empleados() {
        configurarVentana();
        crearComponentes();
    }
    //Vista general de la ventana
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
        
        //Título
        JLabel lblTitulo = new JLabel("EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(225, 40, 300, 50);
        panel.add(lblTitulo);

       Color verde = new Color(46, 204, 113);//Color para los botones
        Color naranja = new Color(243, 156, 18);//Color para los botones
        
        //Constantes posiciones de botones
        int botonX = 245;
        int botonAncho = 260;
        int botonAlto = 55;

        
        // Botón Altas
        RoundedButton btnHorariosAltas = new RoundedButton("Altas", verde);
        btnHorariosAltas.setBounds(botonX, 150, botonAncho, botonAlto);
        btnHorariosAltas.addActionListener(e -> {
            new EmpleadosAlta().setVisible(true);
        });
        panel.add(btnHorariosAltas);

        // Botón Bajas y consultas
        RoundedButton btnHorariosByC = new RoundedButton("Bajas y consultas", naranja);
        btnHorariosByC.setBounds(botonX, 300, botonAncho, botonAlto);
        btnHorariosByC.addActionListener(e -> {
            new EmpleadosByC().setVisible(true);
        });
        panel.add(btnHorariosByC);

        
    }
}
