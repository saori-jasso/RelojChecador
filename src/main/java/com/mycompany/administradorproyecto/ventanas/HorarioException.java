/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.ventanas;

/**
 *
 * @author USUARIO
 */
public class HorarioException extends Exception {
    private final String explicacion;

    // Constructor que recibe el mensaje de error
    public HorarioException(String explicacion) {
        super(explicacion);
        this.explicacion = explicacion;
    }

    // Método para obtener el mensaje detallado en los JOptionPanes
    public String getExplicacion() {
        return explicacion;
    }
}
