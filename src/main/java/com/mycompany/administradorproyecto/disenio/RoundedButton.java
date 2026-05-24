package com.mycompany.administradorproyecto.disenio;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Sheccid
 */
// DISEÑO DE BOTONES 
public class RoundedButton extends JButton{
    private Color colorFondo; //Color que escogeremos en cada botón
    
    // Constructor: Recibe el texto y el color de fondo
    public RoundedButton(String texto, Color color) {
        super(texto);
        this.colorFondo = color;
        //elimina los estilos por defecto de Java
        setOpaque(false);              // Fondo transparente
        setFocusPainted(false);        // Quitar borde de foco
        setContentAreaFilled(false);   // Quitar fondo default de botón
        setBorderPainted(false);       // Quitar borde default
        //Diseño
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(Color.WHITE);    // Texto blanco
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }
    
    // Renderiza el botón 
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        // Cambiamos el color conforme queramos
        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}

