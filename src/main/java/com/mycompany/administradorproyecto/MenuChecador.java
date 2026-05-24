/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author soporte
 */
public class MenuChecador extends JFrame{
    
    public MenuChecador() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setSize(640, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // DISPOSE para no cerrar todo al cerrar esta ventana
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 640, 380);
        panel.setLayout(null);
        setContentPane(panel);

        // Título
        JLabel lblTitulo = new JLabel("<html><center>CHECADORES, UBICACIONES Y<br>DEPARTAMENTOS</center></html>");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 24)); //Cambiamos de fuente??
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(100, 20, 440, 70);
        panel.add(lblTitulo);

        // Línea separadora debajo del título
        JSeparator separador = new JSeparator();
        separador.setBounds(50, 95, 540, 2);
        separador.setForeground(new Color(130, 100, 210)); // morado como en la imagen
        panel.add(separador);

        // Color morado/azul de los botones (igual que en la imagen)
        Color morado = new Color(130, 130, 240);

        // Botón Altas
        RoundedButton btnAltas = new RoundedButton("Altas", morado);
        btnAltas.setBounds(220, 120, 200, 45);
        
        btnAltas.addActionListener(e -> {
            new AltasChecadores().setVisible(true);
        });
        
        panel.add(btnAltas);

        // Botón Bajas y Consultas
        RoundedButton btnBajasConsultas = new RoundedButton("<html><center>Bajas y<br>Consultas</center></html>", morado);
        btnBajasConsultas.setBounds(220, 195, 200, 55);
        
        btnBajasConsultas.addActionListener(e -> {
            new BajasYConsultas().setVisible(true);
        });
        
        panel.add(btnBajasConsultas);

        // Botón Checadas
        RoundedButton btnChecadas = new RoundedButton("Checadas", morado);
        btnChecadas.setBounds(220, 275, 200, 45);
        
        btnChecadas.addActionListener(e -> {
            new Checadas().setVisible(true);
        });
       
        panel.add(btnChecadas);
    }
    
}
