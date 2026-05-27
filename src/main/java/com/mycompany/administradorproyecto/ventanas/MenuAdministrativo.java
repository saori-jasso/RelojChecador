/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.ventanas;
import com.mycompany.administradorproyecto.model.Horarios;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.ventanas.HorariosAdministrativoAlta;
import com.mycompany.administradorproyecto.ventanas.HorariosByC;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class MenuAdministrativo extends JFrame{
    //Constructor 
    public MenuAdministrativo() {
        setTitle("Menú Horarios Administrativo");
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
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 640, 380);
        panel.setLayout(null);
        setContentPane(panel);

        //Titulo 
        JLabel lblTitulo = new JLabel("HORARIOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(225, 40, 300, 50);
        panel.add(lblTitulo);
        
        //Definimos el color que queremos
        Color verde = new Color(46, 204, 113);//Color para los botones
        Color naranja = new Color(243, 156, 18);//Color para los botones
        
        
        // Botón Profesores
        RoundedButton btnHorariosAltas = new RoundedButton("Altas", verde);
        btnHorariosAltas.setBounds(275, 150, 200, 45);
        btnHorariosAltas.addActionListener(e -> {
            new HorariosAdministrativoAlta().setVisible(true);
            this.dispose(); 
        });
        panel.add(btnHorariosAltas);

        // Botón Administrativos
        RoundedButton btnHorariosByC = new RoundedButton("Bajas y consultas", naranja);
        btnHorariosByC.setBounds(275, 250, 200, 45);
        btnHorariosByC.addActionListener(e -> {
            new HorariosAdministrativoByC().setVisible(true);
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
        
        // ⏪️ Botón Volver Atrás (Flecha izquierda)
        RoundedButton btnVolver = new RoundedButton("←", morado);
        btnVolver.setFont(fuenteNavegacion);
        btnVolver.setBounds(20, 20, 50, 35); // Esquina superior izquierda
        btnVolver.addActionListener(e -> {
            // 1. Instancias la ventana a la que quieres regresar (o usas una referencia)
            // Supongamos que venías de una ventana llamada "GestionHorarios"
            Horarios ventanaAnterior = new Horarios(); 
            ventanaAnterior.setVisible(true);

            // 2. Cierras por completo la ventana actual para que no se amontone
            this.dispose(); 
        });
        panel.add(btnVolver);
    }
}
