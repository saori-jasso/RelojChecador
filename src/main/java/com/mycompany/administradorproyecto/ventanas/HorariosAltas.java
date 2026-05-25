package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD; // Importamos tu clase de conexión
import com.mycompany.administradorproyecto.disenio.*;
import com.mycompany.administradorproyecto.disenio.CustomDialog;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.disenio.RoundedComboBox;
import com.mycompany.administradorproyecto.disenio.RoundedTextField;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class HorariosAltas extends JFrame {
   /* // Variables de instancia globales (Campos de la interfaz)
    private RoundedTextField txtId, txtEmp, txtGrupo, txtAula;
    private RoundedComboBox cmbHrInicio, cmbHrFin;
    private RoundedCheckBox[] chkDias;
    private String[] nombresDias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

    // Constructor
    public HorariosAltas() {
        configurarVentana();
        crearComponentes();
    }

    // Vista general de la ventana
    private void configurarVentana() {
        setTitle("Horarios");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        // Diseño del fondo
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 500); 
        panel.setLayout(null);
        setContentPane(panel);

        // Horarios 
        JLabel lblTitulo = new JLabel("HORARIOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(150, 10, 400, 40);
        panel.add(lblTitulo);

        // FILA 1 — ID / Hora de inicio
        JLabel lblId = crearLabel("ID");
        lblId.setBounds(50, 65, 120, 30);
        panel.add(lblId);
        
        txtId = new RoundedTextField(); 
        txtId.setText("00000");
        txtId.setEditable(false); 
        txtId.setBounds(180, 65, 120, 35);
        panel.add(txtId);

        JLabel lblHrInicio = crearLabel("INICIO");
        lblHrInicio.setBounds(360, 65, 130, 30);
        panel.add(lblHrInicio);

        cmbHrInicio = new RoundedComboBox(new String[]{"07:00", "08:00", "09:00", "10:00", 
            "11:00", "12:00","13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00"});
        cmbHrInicio.setBounds(500, 65, 120, 35);
        panel.add(cmbHrInicio);

        // FILA 2 — ID Empleado / Hora de fin
        JLabel lblTipoEmp = crearLabel("ID EMPLEADO"); 
        lblTipoEmp.setBounds(50, 130, 130, 30);
        panel.add(lblTipoEmp);

        txtEmp = new RoundedTextField(); // CORREGIDO: Uso de la variable global adecuada
        txtEmp.setBounds(180, 130, 120, 35);
        panel.add(txtEmp);
        
        JLabel lblHrFin = crearLabel("FIN"); // CORREGIDO: Decía "INICIO"
        lblHrFin.setBounds(360, 130, 130, 30);
        panel.add(lblHrFin);

        cmbHrFin = new RoundedComboBox(new String[]{"07:50", "08:50", "09:50", "10:50", "11:50", "12:50", 
            "13:50", "14:50", "15:50", "16:50", "17:50", "18:50", "19:50", "20:50"});
        cmbHrFin.setBounds(500, 130, 120, 35);
        panel.add(cmbHrFin);
        
        // FILA 3 — Grupo / Aula
        JLabel lblGrupo = crearLabel("ID GRUPO"); 
        lblGrupo.setBounds(50, 200, 120, 30);
        panel.add(lblGrupo);

        txtGrupo = new RoundedTextField(); // CORREGIDO: Sin re-declarar tipo
        txtGrupo.setBounds(180, 200, 120, 35);
        panel.add(txtGrupo);
        
        JLabel lblAula = crearLabel("AULA");
        lblAula.setBounds(360, 200, 130, 30);
        panel.add(lblAula);

        txtAula = new RoundedTextField();
        txtAula.setText("");
        txtAula.setBounds(500, 200, 120, 35);
        panel.add(txtAula);

        // FILA 4 - Días de la semana
        JLabel lblDiasSem = crearLabel("DIAS DE LA SEMANA:");
        lblDiasSem.setBounds(50, 270, 250, 30);
        panel.add(lblDiasSem);
        
        chkDias = new RoundedCheckBox[6];
        int xOffset = 30;
        for (int i = 0; i < chkDias.length; i++) {
            chkDias[i] = new RoundedCheckBox(nombresDias[i]);
            chkDias[i].setBounds(xOffset, 310, 130, 35);
            xOffset += 112;
            panel.add(chkDias[i]);
        }

        // BOTONES
        Color azul = new Color(79, 190, 220);
        RoundedButton btnTerminarRegistro = new RoundedButton("Terminar registro", azul);
        btnTerminarRegistro.setBounds(270, 380, 170, 40);
        panel.add(btnTerminarRegistro);

        // Evento del botón
        btnTerminarRegistro.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarRegistroHorario();
            }
        });
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        return label;
    }

    // --- LOGICA REVISADA DE EXCEPCIONES
    private void ejecutarRegistroHorario() {
        try {
            // 1. Campos vacíos
            if (txtEmp.getText().trim().isEmpty() || txtGrupo.getText().trim().isEmpty() || txtAula.getText().trim().isEmpty()) {
                throw new HorarioException("Todos los campos (Empleado, Grupo y Aula) son obligatorios.");
            }

            // 2. Días seleccionados
            List<String> diasSeleccionados = new ArrayList<>();
            for (int i = 0; i < chkDias.length; i++) {
                if (chkDias[i].isSelected()) {
                    diasSeleccionados.add(nombresDias[i]);
                }
            }
            if (diasSeleccionados.isEmpty()) {
                throw new HorarioException("Debe seleccionar al menos un día de la semana.");
            }

            // 3. Conversión de tipos de ID numéricos
            int idEmp, idGrupo;
            try {
                idEmp = Integer.parseInt(txtEmp.getText().trim());
                idGrupo = Integer.parseInt(txtGrupo.getText().trim());
            } catch (NumberFormatException e) {
                throw new HorarioException("Los identificadores de Empleado y Grupo deben ser números enteros.");
            }

            // 4. Expresión regular para las aulas exigidas (A1-A99, CC1-CC20, LC1-LC40, SVC1, SVC2)
             String aula = txtAula.getText().trim().toUpperCase();
            if (!aula.matches("^(A[1-9][0-9]?|CC([1-9]|1[0-9]|20)|LC([1-9]|[1-3][0-9]|40)|SVC1|SVC2)$")) {
                throw new HorarioException("El aula no coincide con ningún espacio registrado.\nFormatos válidos: A1-A99, CC1-CC20, LC1-LC40, SVC1 o SVC2.");
            }

            // 5. Tiempos y duraciones usando java.time
            LocalTime horaInicio = LocalTime.parse(cmbHrInicio.getSelectedItem().toString());
            LocalTime horaFin = LocalTime.parse(cmbHrFin.getSelectedItem().toString());

            if (!horaInicio.isBefore(horaFin)) {
                throw new HorarioException("La hora de inicio no puede ser igual o posterior a la hora de fin.");
            }

            long minutosDuracion = Duration.between(horaInicio, horaFin).toMinutes();
            // Mínimo 50 min, Máximo 2h 50min (170 min)
            if (minutosDuracion < 50 || minutosDuracion > 170) {
                throw new HorarioException("Duración incorrecta (" + minutosDuracion + " min).\nLa clase debe durar entre 50 minutos y 2 horas con 50 minutos.");
            }

            // --- OPERACIONES DE BASE DE DATOS USANDO TU CLASE CONEXIONBD ---
            try (Connection con = ConexionBD.conectar()) { 
                if (con == null) {
                    throw new SQLException("No se pudo establecer conexión con el servidor local de base de datos.");
                }

                // Verificar existencia de Empleado
                String sqlEmp = "SELECT id_emp FROM empleados WHERE id_emp = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlEmp)) {
                    ps.setInt(1, idEmp);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new HorarioException("El ID de Empleado " + idEmp + " no existe.");
                    }
                }

                // Verificar existencia de Grupo
                String sqlGrupo = "SELECT id_group FROM grupo WHERE id_group = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlGrupo)) {
                    ps.setInt(1, idGrupo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new HorarioException("El ID de Grupo " + idGrupo + " no existe.");
                    }
                }

                // Verificar Colisiones (Empalmes de maestro, grupo o aula a esa hora y día)
                String sqlChoque = "SELECT dia_semana FROM horario_clase WHERE dia_semana = ? AND " +
                                   "((hora_inicio < ? AND hora_fin > ?) AND (id_emp = ? OR id_grupo = ? OR aula = ?))";
                for (String dia : diasSeleccionados) {
                    try (PreparedStatement ps = con.prepareStatement(sqlChoque)) {
                        ps.setString(1, dia);
                        ps.setTime(2, java.sql.Time.valueOf(horaFin));
                        ps.setTime(3, java.sql.Time.valueOf(horaInicio));
                        ps.setInt(4, idEmp);
                        ps.setInt(5, idGrupo);
                        ps.setString(6, aula);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                throw new HorarioException("¡Conflicto! El " + dia + " en ese horario ya está asignado al mismo empleado, grupo o aula.");
                            }
                        }
                    }
                }

                // Todo en orden -> Inserción por transacciones (Commit/Rollback)
                con.setAutoCommit(false);
                String sqlInsert = "INSERT INTO horario_clase (id_emp, id_grupo, dia_semana, hora_inicio, hora_fin, aula) VALUES (?, ?, ?, ?, ?, ?)";
                List<Long> idsAsignados = new ArrayList<>();

                try {
                    for (String dia : diasSeleccionados) {
                        try (PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                            ps.setInt(1, idEmp);
                            ps.setInt(2, idGrupo);
                            ps.setString(3, dia);
                            ps.setTime(4, java.sql.Time.valueOf(horaInicio));
                            ps.setTime(5, java.sql.Time.valueOf(horaFin));
                            ps.setString(6, aula);
                            ps.executeUpdate();

                            try (ResultSet rsKeys = ps.getGeneratedKeys()) {
                                if (rsKeys.next()) idsAsignados.add(rsKeys.getLong(1));
                            }
                        }
                    }
                    con.commit();
                    CustomDialog.mostrar(this, "¡Registro Exitoso!\nHorario guardado.\nIDs Asignados: " + idsAsignados.toString(), CustomDialog.Tipo.EXITO);
                    limpiarCampos();
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                }
            }
        } catch (HorarioException ex) {
            CustomDialog.mostrar(this, ex.getMessage(), CustomDialog.Tipo.ADVERTENCIA);
        } catch (SQLException ex) {
            CustomDialog.mostrar(this, "Error en Base de Datos:\n" + ex.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    private void limpiarCampos() {
        txtEmp.setText("");
        txtGrupo.setText("");
        txtAula.setText("");
        cmbHrInicio.setSelectedIndex(0);
        cmbHrFin.setSelectedIndex(0);
        for (javax.swing.JCheckBox chk : chkDias) {
            chk.setSelected(false);
        }
    }*/
}