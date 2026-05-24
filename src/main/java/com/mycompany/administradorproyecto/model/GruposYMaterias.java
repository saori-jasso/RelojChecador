/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto.model;

import com.mycompany.administradorproyecto.disenio.RoundedTextField;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedComboBox;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GruposYMaterias extends JFrame {
    //Constructor
    public GruposYMaterias() {
        configurarVentana();
        crearComponentes();
    }
    //Vista en general de la ventana
    private void configurarVentana() {
        setTitle("Grupos y Materias");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 700, 450);
        panel.setLayout(null);
        setContentPane(panel);

        // Título - Grupos y materias 
        JLabel lblTitulo = new JLabel("GRUPOS Y MATERIAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(150, 10, 400, 40);
        panel.add(lblTitulo);

        // FILA 1 — ID GRUPO / ID MATERIA
        panel.add(crearLabel("ID GRUPO"));
        JLabel lblIdGrupo = crearLabel("ID GRUPO");
        lblIdGrupo.setBounds(50, 65, 120, 30);
        panel.add(lblIdGrupo);

        RoundedTextField txtIdGrupo = new RoundedTextField();
        txtIdGrupo.setText("000000");
        txtIdGrupo.setBounds(180, 65, 120, 35);
        panel.add(txtIdGrupo);

        JLabel lblIdMateria = crearLabel("ID MATERIA");
        lblIdMateria.setBounds(360, 65, 130, 30);
        panel.add(lblIdMateria);

        RoundedTextField txtIdMateria = new RoundedTextField();
        txtIdMateria.setText("0000");
        txtIdMateria.setBounds(500, 65, 120, 35);
        panel.add(txtIdMateria);

        // FILA 2 — NOMBRE GRUPO / NOMBRE MATERIA
        JLabel lblNombreGrupo = crearLabel("NOMBRE");
        lblNombreGrupo.setBounds(50, 120, 120, 30);
        panel.add(lblNombreGrupo);

        RoundedTextField txtNombreGrupo = new RoundedTextField();
        txtNombreGrupo.setText("LOREM");
        txtNombreGrupo.setBounds(180, 120, 120, 35);
        panel.add(txtNombreGrupo);

        JLabel lblNombreMateria = crearLabel("NOMBRE");
        lblNombreMateria.setBounds(360, 120, 120, 30);
        panel.add(lblNombreMateria);

        RoundedTextField txtNombreMateria = new RoundedTextField();
        txtNombreMateria.setText("LOREM");
        txtNombreMateria.setBounds(500, 120, 120, 35);
        panel.add(txtNombreMateria);

        // FILA 3 — MATERIA / DEP. 
        JLabel lblMateria = crearLabel("MATERIA");
        lblMateria.setBounds(50, 175, 120, 30);
        panel.add(lblMateria);

        RoundedComboBox cmbMateria = new RoundedComboBox(new String[]{"LOREM", "Opción 1", "Opción 2"});
        cmbMateria.setBounds(180, 175, 120, 35);
        panel.add(cmbMateria);

        JLabel lblDep = crearLabel("DEP.");
        lblDep.setBounds(360, 175, 120, 30);
        panel.add(lblDep);

        RoundedComboBox cmbDep = new RoundedComboBox(new String[]{"LOREM", "Dep. 1", "Dep. 2"});
        cmbDep.setBounds(500, 175, 120, 35);
        panel.add(cmbDep);

        // Colores para los botones
        Color azul   = new Color(79, 190, 220);
        Color amarillo = new Color(255, 200, 80);
        Color rosa   = new Color(255, 100, 130);

        // Fila botones izquierda
        RoundedButton btnRegistrarGrupo = new RoundedButton("Registrar grupo", azul);
        btnRegistrarGrupo.setBounds(130, 240, 170, 40);
        panel.add(btnRegistrarGrupo);

        RoundedButton btnBuscarGrupo = new RoundedButton("Buscar grupo", amarillo);
        btnBuscarGrupo.setBounds(130, 295, 170, 40);
        panel.add(btnBuscarGrupo);

        RoundedButton btnEliminarGrupo = new RoundedButton("Eliminar grupo", rosa);
        btnEliminarGrupo.setBounds(130, 350, 170, 40);
        panel.add(btnEliminarGrupo);

        // Fila botones derecha
        RoundedButton btnRegistrarMateria = new RoundedButton("Registrar materia", azul);
        btnRegistrarMateria.setBounds(390, 240, 170, 40);
        panel.add(btnRegistrarMateria);

        RoundedButton btnBuscarMateria = new RoundedButton("Buscar materia", amarillo);
        btnBuscarMateria.setBounds(390, 295, 170, 40);
        panel.add(btnBuscarMateria);

        RoundedButton btnEliminarMateria = new RoundedButton("Eliminar materia", rosa);
        btnEliminarMateria.setBounds(390, 350, 170, 40);
        panel.add(btnEliminarMateria);
    }
    //La hacemos visible
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }
}
