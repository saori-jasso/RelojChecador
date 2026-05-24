/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class Horarios extends JFrame{
    public Horarios() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        //setTitle("Menú Principal");
        setSize(640, 380);
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


        
        JLabel lblTitulo = new JLabel("HORARIOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(170, 40, 300, 50);
        panel.add(lblTitulo);

        Color azul   = new Color(79, 190, 220);//Creo que ese color querian
        
        // Botón Altas
        RoundedButton btnHorariosAltas = new RoundedButton("Altas", azul);
        btnHorariosAltas.setBounds(220, 100, 200, 45);
        btnHorariosAltas.addActionListener(e -> {
            new HorariosAltas().setVisible(true);
        });
        panel.add(btnHorariosAltas);

        // Botón Bajas y consultas
        RoundedButton btnHorariosByC = new RoundedButton("Bajas y consultas", azul);
        btnHorariosByC.setBounds(220, 200, 200, 45);
        btnHorariosByC.addActionListener(e -> {
            new HorariosByC().setVisible(true);
        });
        panel.add(btnHorariosByC);

        
    }
}
