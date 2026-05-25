package com.mycompany.administradorproyecto.disenio;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;

public class RoundedCheckBox extends JCheckBox {
 
    private static final Color AZUL         = new Color(79, 190, 220);
    private static final Color AZUL_CLARO   = new Color(79, 190, 220, 60);   // fondo al seleccionar
    private static final Color BORDE_NORMAL = new Color(79, 190, 220, 120);  // borde sin seleccionar
    private static final int   ARCO         = 20;
    private static final int   BOX_SIZE     = 16;
 
    public RoundedCheckBox(String texto) {
        super(texto);
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setIcon(new javax.swing.Icon() {  // ← AGREGA ESTO
            public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {}
            public int getIconWidth()  { return 0; }
            public int getIconHeight() { return 0; }
        });
        setSelectedIcon(new javax.swing.Icon() {  // ← Y ESTO
            public void paintIcon(java.awt.Component c, Graphics g, int x, int y) {}
            public int getIconWidth()  { return 0; }
            public int getIconHeight() { return 0; }
        });
        setFont(new Font("Arial", Font.PLAIN, 14));
        setForeground(new Color(40, 40, 40));
        setBorder(new EmptyBorder(4, 8, 4, 10));
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
        int w = getWidth();
        int h = getHeight();
 
        // Fondo de la "pastilla" completa
        if (isSelected()) {
            g2.setColor(AZUL_CLARO);
            g2.fillRoundRect(0, 0, w, h, ARCO, ARCO);
        }
 
        // Borde de la pastilla
        g2.setColor(isSelected() ? AZUL : BORDE_NORMAL);
        g2.drawRoundRect(0, 0, w - 1, h - 1, ARCO, ARCO);
 
        // Caja del checkbox (esquina izquierda, centrada verticalmente)
        int boxX = 8;
        int boxY = (h - BOX_SIZE) / 2;
 
        if (isSelected()) {
            g2.setColor(AZUL);
            g2.fillRoundRect(boxX, boxY, BOX_SIZE, BOX_SIZE, 6, 6);
            // Tick blanco
            g2.setColor(Color.WHITE);
            g2.setStroke(new java.awt.BasicStroke(2.2f, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));
            g2.drawLine(boxX + 3,  boxY + 8,  boxX + 6,  boxY + 11);
            g2.drawLine(boxX + 6,  boxY + 11, boxX + 13, boxY + 4);
        } else {
            g2.setColor(new Color(255, 255, 255, 0)); // transparente
            g2.fillRoundRect(boxX, boxY, BOX_SIZE, BOX_SIZE, 6, 6);
            g2.setColor(AZUL);
            g2.setStroke(new java.awt.BasicStroke(1.8f));
            g2.drawRoundRect(boxX, boxY, BOX_SIZE, BOX_SIZE, 6, 6);
        }
 
        // Texto (desplazado para dejar espacio a la caja)
        g2.setFont(getFont());
        g2.setColor(getForeground());
        int textX = boxX + BOX_SIZE + 7;
        int textY = (h + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;
        g2.drawString(getText(), textX, textY);
 
        g2.dispose();
    }
}