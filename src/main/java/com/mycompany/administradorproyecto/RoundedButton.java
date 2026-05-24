/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author USUARIO
 */
public class RoundedButton extends JButton{
    private Color colorFondo;
    
    public RoundedButton(String texto, Color color) {
        super(texto);
        this.colorFondo = color;
        setOpaque(false);              // Fondo transparente
        setFocusPainted(false);        // Quitar borde de foco
        setContentAreaFilled(false);   // Quitar fondo default de botón
        setBorderPainted(false);       // Quitar borde default
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(Color.WHITE);    // Texto blanco
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        // Color azul igual al de los óvalos del PanelDecorativo
        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Sin borde visible, igual que RoundedTextField pero sin línea
    }
}

