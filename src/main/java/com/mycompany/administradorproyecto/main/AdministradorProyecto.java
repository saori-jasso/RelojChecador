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
        SwingUtilities.invokeLater(() -> {
            new Menu().setVisible(true);
        });
    }
}
