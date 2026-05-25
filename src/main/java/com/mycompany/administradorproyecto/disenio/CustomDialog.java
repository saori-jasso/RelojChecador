package com.mycompany.administradorproyecto.disenio;
 
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class CustomDialog extends JDialog {
 
    public enum Tipo {
        EXITO,       
        ADVERTENCIA, 
        ERROR        
    }
 
    // Colores del proyecto
    private static final Color AZUL     = new Color(79, 190, 220);
    private static final Color ROSA     = new Color(255, 100, 130);
    private static final Color FONDO    = new Color(245, 245, 245);
 
    private CustomDialog(Frame owner, String mensaje, Tipo tipo) {
        super(owner, true);
        setUndecorated(true);
        setSize(340, 220);
        setLocationRelativeTo(owner);
        setBackground(new Color(0, 0, 0, 0));
 
        // Panel decorativo con círculos (mismo estilo que PanelDecorativo)
        JPanel contenido = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FONDO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(AZUL);
                g2.fillOval(-25, -25, 80, 80);
                g2.fillOval(getWidth() - 55, -15, 70, 70);
                g2.fillOval(-20, getHeight() - 55, 70, 70);
                g2.fillOval(getWidth() - 45, getHeight() - 45, 70, 70);
            }
        };
        contenido.setOpaque(false);
        contenido.setPreferredSize(new Dimension(340, 220));
 
        // Ícono
        String icoTexto;
        Color icoColor;
        Color btnColor;
        switch (tipo) {
            case EXITO:
                icoTexto = "OK"; icoColor = AZUL;   btnColor = AZUL;  break;
            case ADVERTENCIA:
                icoTexto = "!"; icoColor = ROSA;   btnColor = ROSA;  break;
            default:
                icoTexto = "X"; icoColor = ROSA.darker(); btnColor = ROSA.darker(); break;
        }
 
        // Círculo con ícono
        JPanel circulo = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillOval(0, 0, 44, 44);
                g2.setColor(icoColor);
                g2.setStroke(new java.awt.BasicStroke(2f));
                g2.drawOval(1, 1, 42, 42);
            }
        };
        circulo.setOpaque(false);
        circulo.setLayout(new BorderLayout());
        JLabel lblIco = new JLabel(icoTexto, SwingConstants.CENTER);
        lblIco.setFont(new Font("Arial", Font.BOLD, tipo == Tipo.EXITO ? 13 : 20));
        lblIco.setForeground(icoColor);
        circulo.add(lblIco, BorderLayout.CENTER);
        circulo.setBounds(148, 18, 44, 44);
        contenido.add(circulo);
 
        // Mensaje (puede tener \n)
        String htmlMensaje = "<html><div style='text-align:center;'>" +
                mensaje.replace("\n", "<br>") + "</div></html>";
        JLabel lblMensaje = new JLabel(htmlMensaje, SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 15));
        lblMensaje.setForeground(new Color(40, 40, 40));
        lblMensaje.setBounds(20, 72, 300, 70);
        contenido.add(lblMensaje);
 
        // Botón Aceptar
        RoundedButton btnAceptar = new RoundedButton("Aceptar", btnColor);
        btnAceptar.setBounds(110, 158, 120, 38);
        btnAceptar.addActionListener(e -> dispose());
        contenido.add(btnAceptar);
 
        setContentPane(contenido);
    }
 
    public static void mostrar(java.awt.Component parent, String mensaje, Tipo tipo) {
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(parent);
        CustomDialog dlg = new CustomDialog(frame, mensaje, tipo);
        dlg.setVisible(true);
    }
}