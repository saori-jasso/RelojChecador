/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.ventanas;
import com.mycompany.administradorproyecto.model.Empleados;
import com.mycompany.administradorproyecto.model.GruposYMaterias;
import com.mycompany.administradorproyecto.model.Horarios;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class Menu extends JFrame{
    //Constructor
    public Menu() {
        configurarVentana();
        crearComponentes();
    }
    //Vista general de la ventana
    private void configurarVentana() {
        setTitle("Menú Principal");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 640, 380);
        panel.setLayout(null);
        setContentPane(panel);

        Color azul   = new Color(79, 190, 220);//Color del botón
        
        // Botón Horarios
        RoundedButton btnHorarios = new RoundedButton("Horarios", azul);
        btnHorarios.setBounds(220, 40, 200, 45);
        btnHorarios.addActionListener(e -> {
            new Horarios().setVisible(true);
        });
        panel.add(btnHorarios);

        // Botón Grupos y materias
        RoundedButton btnGyM = new RoundedButton("Grupos y Materias", azul);
        btnGyM.setBounds(220, 110, 200, 45);
        btnGyM.addActionListener(e -> {
            new GruposYMaterias().setVisible(true);
        });
        panel.add(btnGyM);

        // Botón Empleados
        RoundedButton btnEmpleados = new RoundedButton("Empleados", azul);
        btnEmpleados.setBounds(220, 180, 200, 45);
        
        btnEmpleados.addActionListener(e -> {
            new Empleados ().setVisible(true);
        });   
        panel.add(btnEmpleados);
        
        // Botón Checadores
        RoundedButton btnChecadores = new RoundedButton("Checadores", azul);
        btnChecadores.setBounds(220, 250, 200, 45);
        btnChecadores.addActionListener(e -> {
            new MenuChecador().setVisible(true);
        });
        panel.add(btnChecadores);
    }
}
