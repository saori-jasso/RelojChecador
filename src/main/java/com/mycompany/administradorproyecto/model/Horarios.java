package com.mycompany.administradorproyecto.model;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.ventanas.HorariosByC;
import com.mycompany.administradorproyecto.ventanas.MenuProfesor;
import com.mycompany.administradorproyecto.ventanas.MenuAdministrativo;
import com.mycompany.administradorproyecto.ventanas.Menu;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class Horarios extends JFrame{
    //Constructor 
    public Horarios() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
    // 1. Ajustar el panel al tamaño del JFrame (750x550)
    PanelDecorativo panel = new PanelDecorativo();
    panel.setBounds(0, 0, 750, 550); 
    panel.setLayout(null);
    setContentPane(panel);

    // Definimos el color
    Color azul = new Color(79, 190, 220);

    // 2. Título (Aumentamos ancho a 600 y centramos en x=75)
    JLabel lblTitulo = new JLabel("¿Qué tipo de empleado desea modificar?");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
    lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
    // (750 - 600) / 2 = 75
    lblTitulo.setBounds(75, 80, 600, 50); 
    panel.add(lblTitulo);
    
    // 3. Botón Profesores (Centrado en x: (750-200)/2 = 275)
    RoundedButton btnHorariosAltas = new RoundedButton("Profesor", azul);
    btnHorariosAltas.setBounds(275, 180, 200, 45);
    btnHorariosAltas.addActionListener(e -> {
        new MenuProfesor().setVisible(true);
        this.dispose();
    });
    panel.add(btnHorariosAltas);

    // 4. Botón Administrativos (Misma x para que estén alineados)
    RoundedButton btnHorariosByC = new RoundedButton("Administrativo", azul);
    btnHorariosByC.setBounds(275, 260, 200, 45);
    btnHorariosByC.addActionListener(e -> {
        new MenuAdministrativo().setVisible(true);
        this.dispose();
    });
    panel.add(btnHorariosByC);
    
    //Botones para regresar-------------------------------
    Color morado = new Color(130, 100, 210);
    Font fuenteNavegacion = new Font("Arial", Font.BOLD, 14);
    RoundedButton btnHome = new RoundedButton("⌂", morado);
    btnHome.setFont(new Font("Arial", Font.BOLD, 18)); // Un poco más grande para el símbolo de casa
    btnHome.setBounds(75, 20, 50, 35); // Al lado del botón volver
        btnHome.addActionListener(e -> {
        // 1. Abrir el menú principal
        new Menu().setVisible(true);
        // 2. Destruir la ventana actual
        this.dispose(); 
        });
    panel.add(btnHome);
    
    
    
}
}