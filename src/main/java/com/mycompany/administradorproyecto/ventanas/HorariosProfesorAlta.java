package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD;
import com.mycompany.administradorproyecto.disenio.*;
import com.mycompany.administradorproyecto.model.Checador;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class HorariosProfesorAlta extends JFrame {

    private RoundedTextField txtIdEmp, txtIdGrupo, txtClaveMateria, txtAula;
    private RoundedComboBox cmbHrInicio, cmbHrFin, cmbChecador;
    private JLabel lblNombreMateria;
    private RoundedCheckBox[] chkDias;
    private final String[] nombresDias = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
    private List<Checador> listaChecadores = new ArrayList<>();

    public HorariosProfesorAlta() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Alta de Horarios Profesor");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 550);
        panel.setLayout(null);
        setContentPane(panel);

        JLabel lblTitulo = new JLabel("REGISTRO DE HORARIOS PROFESOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBounds(150, 20, 550, 40);
        panel.add(lblTitulo);

        txtIdEmp = crearCampo(panel, "MATRÍCULA EMPLEADO", 100, 100);
        txtIdGrupo = crearCampo(panel, "ID GRUPO", 100, 170);

        JLabel lblCh = new JLabel("CHECADOR");
        lblCh.setBounds(100, 215, 150, 20);
        panel.add(lblCh);
        
        cmbChecador = new RoundedComboBox(new String[]{""});
        cmbChecador.setBounds(100, 240, 200, 35);
        cargarChecadores();
        panel.add(cmbChecador);

        txtClaveMateria = crearCampo(panel, "CLAVE MATERIA", 100, 310);
        
        lblNombreMateria = new JLabel("Materia: -");
        lblNombreMateria.setFont(new Font("Arial", Font.ITALIC, 12));
        lblNombreMateria.setBounds(100, 345, 250, 20);
        panel.add(lblNombreMateria);

        txtClaveMateria.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizarNombreMateria(txtClaveMateria.getText().trim().toUpperCase());
            }
        });

        cmbHrInicio = crearComboHoraEntrada(panel, "INICIO", 400, 100);
        cmbHrFin = crearComboHoraSalida(panel, "FIN", 400, 170);
        txtAula = crearCampo(panel, "AULA", 400, 240);

        JLabel lblDias = new JLabel("DÍAS DE LA SEMANA");
        lblDias.setBounds(400, 310, 200, 30);
        panel.add(lblDias);

        chkDias = new RoundedCheckBox[6];
        for (int i = 0; i < chkDias.length; i++) {
            chkDias[i] = new RoundedCheckBox(nombresDias[i]);
            int fila = (i < 3) ? 0 : 1;
            int col = (i < 3) ? i : (i - 3);
            chkDias[i].setBounds(400 + (col * 65), 340 + (fila * 40), 60, 30);
            panel.add(chkDias[i]);
        }

        RoundedButton btnGuardar = new RoundedButton("Terminar Registro", new Color(79, 190, 220));
        btnGuardar.setBounds(400, 440, 250, 45);
        btnGuardar.addActionListener(e -> ejecutarRegistroHorario());
        panel.add(btnGuardar);
        
        Color morado = new Color(130, 100, 210);
        RoundedButton btnHome = new RoundedButton("⌂", morado);
        btnHome.setFont(new Font("Arial", Font.BOLD, 18)); 
        btnHome.setBounds(75, 20, 50, 35); 
        btnHome.addActionListener(e -> {
            new Menu().setVisible(true);
            this.dispose(); 
        });
        panel.add(btnHome);
        
        RoundedButton btnVolver = new RoundedButton("←", morado);
        btnVolver.setBounds(20, 20, 50, 35); 
        btnVolver.addActionListener(e -> {
            new MenuProfesor().setVisible(true);
            this.dispose(); 
        });
        panel.add(btnVolver);
    }

    private void cargarChecadores() {
        cmbChecador.removeAllItems();
        cmbChecador.addItem(""); 
        try (Connection con = ConexionBD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id_checador, nombre FROM Checador")) {
            while (rs.next()) {
                Checador c = new Checador(rs.getInt("id_checador"), rs.getString("nombre"));
                listaChecadores.add(c);
                cmbChecador.addItem(c.toString());
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void actualizarNombreMateria(String clave) {
        if (clave.isEmpty()) {
            lblNombreMateria.setText("Materia: -");
            return;
        }
        String sql = "SELECT nombre_materia FROM Materia WHERE clave_materia = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, clave);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lblNombreMateria.setText("Materia: " + rs.getString("nombre_materia"));
                    lblNombreMateria.setForeground(new Color(0, 100, 0));
                } else {
                    lblNombreMateria.setText("Materia no encontrada");
                    lblNombreMateria.setForeground(Color.RED);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    protected void ejecutarRegistroHorario() {
        try {
            String idEmp = txtIdEmp.getText().trim();
            String idGrupo = txtIdGrupo.getText().trim().toUpperCase();
            String claveMateria = txtClaveMateria.getText().trim().toUpperCase();
            String aula = txtAula.getText().trim().toUpperCase();

            if (idEmp.isEmpty() || idGrupo.isEmpty() || claveMateria.isEmpty() || aula.isEmpty() ||
                cmbChecador.getSelectedIndex() <= 0 || cmbHrInicio.getSelectedIndex() == 0 || cmbHrFin.getSelectedIndex() == 0) {
                CustomDialog.mostrar(this, "Completa todos los campos.", CustomDialog.Tipo.ADVERTENCIA);
                return;
            }

            // 1. Verificar existencia y Puesto
            if (!existeEnBD("Empleados", "matricula", idEmp)) {
                CustomDialog.mostrar(this, "La matrícula no existe.", CustomDialog.Tipo.ERROR);
                return;
            }
            String regexAula = "^(A[1-9][0-9]?|LC[1-4]|CC([1-9]|1[0-9]|20)|SVC[1-2])$";
        
        if (!aula.matches(regexAula)) {
            CustomDialog.mostrar(this, "Aula no válida.\nEjemplos permitidos:\n- A1 a A99\n- LC1 a LC4\n- CC1 a CC20\n- SVC1 o SVC2", CustomDialog.Tipo.ERROR);
            return;
        }

            String puesto = obtenerPuesto(idEmp);
            if (puesto == null || (!puesto.equalsIgnoreCase("Profesor") && !puesto.equalsIgnoreCase("Ambos"))) {
                CustomDialog.mostrar(this, "El empleado debe ser Profesor o Ambos.", CustomDialog.Tipo.ERROR);
                return;
            }

            // 2. Bloqueo de Grupo Duplicado
            if (verificarGrupoExistente(idGrupo)) {
                CustomDialog.mostrar(this, "El grupo " + idGrupo + " ya tiene un horario registrado.", CustomDialog.Tipo.ERROR);
                return;
            }

            Time nuevaInicio = Time.valueOf(cmbHrInicio.getSelectedItem().toString() + ":00");
            Time nuevaFin = Time.valueOf(cmbHrFin.getSelectedItem().toString() + ":50");

            try (Connection con = ConexionBD.conectar()) {
                con.setAutoCommit(false);
                
                boolean algunDia = false;
                for (int i = 0; i < chkDias.length; i++) {
                    if (chkDias[i].isSelected()) {
                        algunDia = true;
                        String dia = nombresDias[i];

                        // A) Empalme en tabla de Clases (Profesor u Aula)
                        if (existeEmpalmeClase(con, Integer.parseInt(idEmp), aula, dia, nuevaInicio, nuevaFin)) {
                            CustomDialog.mostrar(this, "Conflicto el " + dia + ": El profesor o el aula ya están ocupados.", CustomDialog.Tipo.ADVERTENCIA);
                            con.rollback();
                            return;
                        }

                        // B) SI ES "AMBOS", verificar empalme en tabla Administrativa
                        if (puesto.equalsIgnoreCase("Ambos")) {
                            if (existeEmpalmeAdministrativo(con, Integer.parseInt(idEmp), dia, nuevaInicio, nuevaFin)) {
                                CustomDialog.mostrar(this, "Conflicto el " + dia + ": El empleado tiene un horario ADMINISTRATIVO a esta hora.", CustomDialog.Tipo.ADVERTENCIA);
                                con.rollback();
                                return;
                            }
                        }
                    }
                }

                if (!algunDia) {
                    CustomDialog.mostrar(this, "Selecciona al menos un día.", CustomDialog.Tipo.ADVERTENCIA);
                    return;
                }

                // Registro
                String sqlInsert = "INSERT INTO Horario_Clase (matricula, clave_grupo, clave_materia, hora_inicio, hora_fin, aula, id_checador, dia_semana) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                    for (int i = 0; i < chkDias.length; i++) {
                        if (chkDias[i].isSelected()) {
                            ps.setInt(1, Integer.parseInt(idEmp));
                            ps.setString(2, idGrupo);
                            ps.setString(3, claveMateria);
                            ps.setTime(4, nuevaInicio);
                            ps.setTime(5, nuevaFin);
                            ps.setString(6, aula);
                            ps.setInt(7, listaChecadores.get(cmbChecador.getSelectedIndex() - 1).getId());
                            ps.setString(8, nombresDias[i]);
                            ps.executeUpdate();
                        }
                    }
                    con.commit();
                    CustomDialog.mostrar(this, "Horario registrado con éxito.", CustomDialog.Tipo.EXITO);
                }
            }
        } catch (Exception e) {
            CustomDialog.mostrar(this, "Error: " + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    private String obtenerPuesto(String mat) {
        try (Connection con = ConexionBD.conectar(); 
             PreparedStatement ps = con.prepareStatement("SELECT puesto FROM Empleados WHERE matricula = ?")) {
            ps.setString(1, mat);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("puesto");
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    private boolean verificarGrupoExistente(String grupo) {
        String sql = "SELECT 1 FROM Horario_Clase WHERE clave_grupo = ? LIMIT 1";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, grupo);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { return false; }
    }

    private boolean existeEmpalmeClase(Connection con, int mat, String aula, String dia, Time nI, Time nF) throws SQLException {
        String sql = "SELECT 1 FROM Horario_Clase WHERE dia_semana = ? AND (matricula = ? OR aula = ?) AND ((hora_inicio < ? AND hora_fin > ?) OR (hora_inicio = ?))";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dia); ps.setInt(2, mat); ps.setString(3, aula);
            ps.setTime(4, nF); ps.setTime(5, nI); ps.setTime(6, nI);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    // VALIDACIÓN CRUZADA: Revisa la tabla de administradores
    private boolean existeEmpalmeAdministrativo(Connection con, int mat, String dia, Time nI, Time nF) throws SQLException {
        String sql = "SELECT 1 FROM Horario_Administrador WHERE matricula = ? AND dia_semana = ? AND ((hora_inicio < ? AND hora_fin > ?) OR (hora_inicio = ?))";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mat); ps.setString(2, dia);
            ps.setTime(3, nF); ps.setTime(4, nI); ps.setTime(5, nI);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    private boolean existeEnBD(String tabla, String col, String val) {
        try (Connection con = ConexionBD.conectar(); 
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM " + tabla + " WHERE " + col + " = ?")) {
            ps.setString(1, val);
            return ps.executeQuery().next();
        } catch (SQLException e) { return false; }
    }

    private RoundedTextField crearCampo(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        RoundedTextField txt = new RoundedTextField(); txt.setBounds(x, y, 200, 35); p.add(txt); return txt;
    }

    private RoundedComboBox crearComboHoraEntrada(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        String[] h = new String[16]; h[0] = ""; for(int i=7; i<=21; i++) h[i-6] = String.format("%02d:00", i);
        RoundedComboBox cmb = new RoundedComboBox(h); cmb.setBounds(x, y, 200, 35); p.add(cmb); return cmb;
    }

    private RoundedComboBox crearComboHoraSalida(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        String[] h = new String[16]; h[0] = ""; for(int i=7; i<=21; i++) h[i-6] = String.format("%02d:50", i);
        RoundedComboBox cmb = new RoundedComboBox(h); cmb.setBounds(x, y, 200, 35); p.add(cmb); return cmb;
    }
}