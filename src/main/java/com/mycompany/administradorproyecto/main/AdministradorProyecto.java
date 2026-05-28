/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.administradorproyecto.main;
import com.mycompany.administradorproyecto.ventanas.Menu;
import javax.swing.*;

/**
 *
 * @author USUARIO
 */
public class AdministradorProyecto {
    //Hacemos visible la pantalla principal (Menú)
    public static void main(String[] args) {
        //Inicializamos el diseño de FlatLaf ANTES de abrir nada
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo iniciar FlatLaf: " + ex.getMessage());
        }

        // Hacemos visible la pantalla principal (Menú)
        SwingUtilities.invokeLater(() -> {
            ServidorChecador servidor = new ServidorChecador();
            servidor.start(); // Esto arranca el hilo servidor
            new Menu().setVisible(true);
        });
    }
}
