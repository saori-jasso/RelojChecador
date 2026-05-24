package com.mycompany.administradorproyecto.disenio;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Andrea
 */
public class PanelDecorativo extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //SUAVIZADO
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        //FONDO PRINCIPAL
        g2.setColor(new Color(245, 245, 245));
        g2.fillRect(0, 0, getWidth(), getHeight());
        //DECORACIONES AZULES
        g2.setColor(new Color(79, 190, 220));
        g2.fillOval(-40, -40, 100, 100);// Superior izquierda
        g2.fillOval(500, -40, 160, 90);// Superior derecha
        g2.fillOval(-30, 300, 140, 100);// Inferior izquierda
        g2.fillOval(580, 260, 100, 100);// Inferior derecha
    }
}
