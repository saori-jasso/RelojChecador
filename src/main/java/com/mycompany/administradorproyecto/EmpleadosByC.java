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

public class EmpleadosByC extends JFrame {

    public EmpleadosByC() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Bajas y consultas de empleados");
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
        JLabel lblTitulo = new JLabel("BAJAS Y CONSULTAS DE EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 10, 600, 40);
        panel.add(lblTitulo);

        // FILA 1 — ID  / fecha nac
        panel.add(crearLabel("ID"));//ID
        JLabel lblIdemp = crearLabel("ID");
        lblIdemp.setBounds(50, 65, 120, 30);
        panel.add(lblIdemp);
        
        RoundedTextField txtIdemp = new RoundedTextField();
        txtIdemp.setText("000000");
        txtIdemp.setBounds(180, 65, 120, 35);
        panel.add(txtIdemp);

        JLabel lblFechaNac = crearLabel("FECHA DE NACIMIENTO");//hora de inicio
        lblFechaNac.setBounds(360, 65, 130, 30);
        panel.add(lblFechaNac);

        RoundedTextField txtFechaNac = new RoundedTextField();
        txtFechaNac.setText("0000");
        txtFechaNac.setBounds(500, 65, 120, 35);
        panel.add(txtFechaNac);

        // FILA 2 — Nombre / Puesto
        
        JLabel lblNombreEmp = crearLabel("NOMBRE");
        lblNombreEmp.setBounds(50, 130, 130, 30);
        panel.add(lblNombreEmp);

        RoundedTextField txtNombreEmp = new RoundedTextField();
        txtNombreEmp.setText("0000");
        txtNombreEmp.setBounds(180, 130, 120, 35);
        panel.add(txtNombreEmp);
        
        JLabel lblPuestoEmp = crearLabel("PUESTO");//hora de fin
        lblPuestoEmp.setBounds(360, 130, 130, 30);
        panel.add(lblPuestoEmp);

        RoundedTextField txtPuestoEmp = new RoundedTextField();
        txtPuestoEmp.setText("0000");
        txtPuestoEmp.setBounds(500, 130, 120, 35);
        panel.add(txtPuestoEmp);
        
        // FILA 3 — Apellido pat / Dep
        JLabel lblApellidoP = crearLabel("APELLIDO P");//Apellido paterno
        lblApellidoP.setBounds(50, 200, 130, 30);
        panel.add(lblApellidoP);

        RoundedTextField txtApellidoP = new RoundedTextField();
        txtApellidoP.setText("0000");
        txtApellidoP.setBounds(180, 200, 120, 35);
        panel.add(txtApellidoP);
        
        JLabel lblDep = crearLabel("DEPARTAMENTO"); //Departamento
        lblDep.setBounds(360, 200, 120, 30);
        panel.add(lblDep);

        RoundedComboBox cmbDep = new RoundedComboBox(new String[]{"sheccid", "chechid", "chesid"});
        cmbDep.setBounds(500, 200, 120, 35);
        panel.add(cmbDep);
        
        
        //FILA 4 - Apellido materno / fecha de ingreso
        JLabel lblApellidoM = crearLabel("APELLIDO M");//Apellido M
        lblApellidoM.setBounds(50, 270, 200, 30);
        panel.add(lblApellidoM);

        RoundedTextField txtApellidoM = new RoundedTextField();
        txtApellidoM.setText("0000");
        txtApellidoM.setBounds(180, 270, 120, 35);
        panel.add(txtApellidoM);
        
        JLabel lblFechaIng = crearLabel("FECHA DE INGRESO");//Fecha de ingreso
        lblFechaIng.setBounds(360, 270, 140, 30);
        panel.add(lblFechaIng);

        RoundedTextField txtFechaIng = new RoundedTextField();
        txtFechaIng.setText("0000");
        txtFechaIng.setBounds(500, 270, 120, 35);
        panel.add(txtFechaIng);

        // BOTONES — colores del diseño
        Color azul   = new Color(79, 190, 220);
        Color amarillo = new Color(255, 200, 80);
        Color rosa   = new Color(255, 100, 130);

        //Botones de busqueda y eliminación
        RoundedButton btnConsulta = new RoundedButton("Buscar", azul);
        btnConsulta.setBounds(160, 340, 170, 40);
        panel.add(btnConsulta);
        
        RoundedButton btnBaja = new RoundedButton("Eliminar", rosa);
        btnBaja.setBounds(360, 340, 170, 40);
        panel.add(btnBaja);

    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }
}
