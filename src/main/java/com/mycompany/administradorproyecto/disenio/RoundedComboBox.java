/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.disenio;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;

//Diseño del JComboBox
public class RoundedComboBox extends JComboBox<String> {
    // Constructor
    public RoundedComboBox(String[] opciones) {
        //Pasa las opciones al ComboBox original
        super(opciones);
        setOpaque(false);
        //Diseño
        setFont(new Font("Arial", Font.PLAIN, 16));
        setBackground(new Color(210, 210, 210));
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setFocusable(false);
    }
    // Dibuja el fondo del ComboBox con esquinas curvas antes de pintar el texto de las opciones
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2.setColor(new Color(180, 180, 180));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        g2.dispose();
    }
}
