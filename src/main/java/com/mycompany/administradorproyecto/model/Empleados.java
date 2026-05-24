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
        setTitle("Menú Principal");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 640, 380);
        panel.setLayout(null);
        setContentPane(panel);
        
        //Título
        JLabel lblTitulo = new JLabel("EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(170, 40, 300, 50);
        panel.add(lblTitulo);

        Color azul   = new Color(79, 190, 220);//Color para los botones
        
        // Botón Altas
        RoundedButton btnHorariosAltas = new RoundedButton("Altas", azul);
        btnHorariosAltas.setBounds(220, 100, 200, 45);
        btnHorariosAltas.addActionListener(e -> {
            new EmpleadosAlta().setVisible(true);
        });
        panel.add(btnHorariosAltas);

        // Botón Bajas y consultas
        RoundedButton btnHorariosByC = new RoundedButton("Bajas y consultas", azul);
        btnHorariosByC.setBounds(220, 200, 200, 45);
        btnHorariosByC.addActionListener(e -> {
            new EmpleadosByC().setVisible(true);
        });
        panel.add(btnHorariosByC);

        
    }
}
