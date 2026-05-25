/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.ventanas;
import com.mycompany.administradorproyecto.model.*;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class Menu extends JFrame {
    
    // Constructor
    public Menu() {
        configurarVentana();
        crearComponentes();
    }
    
    // Vista general de la ventana
    private void configurarVentana() {
        setTitle("Menú Principal");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        // Panel ajustado para cubrir las dimensiones de la ventana (750x550)
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 550);
        panel.setLayout(null);
        setContentPane(panel);

        Color azul = new Color(79, 190, 220); 
        Font fuenteBotones = new Font("Arial", Font.BOLD, 18); 
        
        int botonX = 245;
        int botonAncho = 260;
        int botonAlto = 55;

        // 1. Botón Horarios
        RoundedButton btnHorarios = new RoundedButton("Horarios", azul);
        btnHorarios.setFont(fuenteBotones);
        btnHorarios.setBounds(botonX, 100, botonAncho, botonAlto);
        btnHorarios.addActionListener(e -> {
            new Horarios().setVisible(true);
        });
        panel.add(btnHorarios);

        // 2. Botón Empleados
        RoundedButton btnEmpleados = new RoundedButton("Empleados", azul);
        btnEmpleados.setFont(fuenteBotones);
        btnEmpleados.setBounds(botonX, 210, botonAncho, botonAlto);
        btnEmpleados.addActionListener(e -> {
            new Empleados().setVisible(true);
        });   
        panel.add(btnEmpleados);
        
        // 3. Botón Checadores
        RoundedButton btnChecadores = new RoundedButton("Checadores", azul);
        btnChecadores.setFont(fuenteBotones);
        btnChecadores.setBounds(botonX, 320, botonAncho, botonAlto);
        btnChecadores.addActionListener(e -> {
            new MenuChecador().setVisible(true);
        });
        panel.add(btnChecadores);
    }
}