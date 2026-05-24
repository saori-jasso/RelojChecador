/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorproyecto;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HorariosAltas extends JFrame {

    public HorariosAltas() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Horarios");
        setSize(700, 450);
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

        // Horarios 
        JLabel lblTitulo = new JLabel("HORARIOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(150, 10, 400, 40);
        panel.add(lblTitulo);

        // FILA 1 — ID  / Hora de inicio
        panel.add(crearLabel("ID"));//ID
        JLabel lblId = crearLabel("ID");
        lblId.setBounds(50, 65, 120, 30);
        panel.add(lblId);
        
        RoundedTextField txtId = new RoundedTextField();
        txtId.setText("000000");
        txtId.setBounds(180, 65, 120, 35);
        panel.add(txtId);

        JLabel lblHrInicio = crearLabel("INICIO");//hora de inicio
        lblHrInicio.setBounds(360, 65, 130, 30);
        panel.add(lblHrInicio);

        RoundedTextField txtHrInicio = new RoundedTextField();
        txtHrInicio.setText("0000");
        txtHrInicio.setBounds(500, 65, 120, 35);
        panel.add(txtHrInicio);

        // FILA 2 — Tipo de empleado / Hora de fin
        
        JLabel lblTipoEmp = crearLabel("EMPLEADO"); //Tipo de Empleado
        lblTipoEmp.setBounds(50, 130, 120, 30);
        panel.add(lblTipoEmp);

        RoundedComboBox cmbTipoEmp = new RoundedComboBox(new String[]{"Profesor", "Laboratorista", "Noob"});
        cmbTipoEmp.setBounds(180, 130, 120, 35);
        panel.add(cmbTipoEmp);
        
        JLabel lblHrFin = crearLabel("INICIO");//hora de fin
        lblHrFin.setBounds(360, 130, 130, 30);
        panel.add(lblHrFin);

        RoundedTextField txtHrFin = new RoundedTextField();
        txtHrFin.setText("0000");
        txtHrFin.setBounds(500, 130, 120, 35);
        panel.add(txtHrFin);
        
        // FILA 3 — Grupo / Aula
        JLabel lblGrupo = crearLabel("GRUPO"); //Grupo
        lblGrupo.setBounds(50, 200, 120, 30);
        panel.add(lblGrupo);

        RoundedComboBox cmbGrupo = new RoundedComboBox(new String[]{"sheccid", "chechid", "chesid"});
        cmbGrupo.setBounds(180, 200, 120, 35);
        panel.add(cmbGrupo);
        
        JLabel lblAula = crearLabel("AULA");//Aula
        lblAula.setBounds(360, 200, 130, 30);
        panel.add(lblAula);

        RoundedTextField txtAula = new RoundedTextField();
        txtAula.setText("0000");
        txtAula.setBounds(500, 200, 120, 35);
        panel.add(txtAula);

        //FILA 4 - Días de la semana / Botón terminar registro
        JLabel lblDiasSem = crearLabel("DIAS DE LA SEMANA");//Aula
        lblDiasSem.setBounds(50, 270, 200, 30);
        panel.add(lblDiasSem);

        RoundedTextField txtDiasSem = new RoundedTextField();
        txtDiasSem.setText("0000");
        txtDiasSem.setBounds(270, 270, 120, 35);
        panel.add(txtDiasSem);

        // BOTONES — colores del diseño
        Color azul   = new Color(79, 190, 220);
        Color amarillo = new Color(255, 200, 80);
        Color rosa   = new Color(255, 100, 130);

        RoundedButton btnTerminarRegistro = new RoundedButton("Terminar registro", azul);
        btnTerminarRegistro.setBounds(270, 320, 170, 40);
        panel.add(btnTerminarRegistro);

    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }
}
