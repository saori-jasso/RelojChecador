package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD;
import com.mycompany.administradorproyecto.disenio.*;
import com.mycompany.administradorproyecto.model.Checador;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class HorariosAdministrativoAlta extends JFrame {
    private RoundedTextField txtMatricula;
    private RoundedComboBox cmbHrInicio, cmbHrFin, cmbChecador;
    private RoundedCheckBox[] chkDias;
    private final String[] nombresDias = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab"};
    private List<Checador> listaChecadores = new ArrayList<>();

    public HorariosAdministrativoAlta() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Alta Horario Administrativo");
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

        JLabel lblTitulo = new JLabel("REGISTRO ADMINISTRATIVO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBounds(180, 20, 400, 40);
        panel.add(lblTitulo);

        txtMatricula = crearCampo(panel, "MATRÍCULA", 100, 120);
        cmbChecador = crearComboChecador(panel, "CHECADOR", 100, 210);
        cmbHrInicio = crearComboHoraEntrada(panel, "INICIO", 400, 120);
        cmbHrFin = crearComboHoraSalida(panel, "FIN", 400, 210);

        JLabel lblDias = new JLabel("DÍAS DE LA SEMANA");
        lblDias.setBounds(100, 300, 200, 30);
        panel.add(lblDias);
        
        chkDias = new RoundedCheckBox[6];
        for (int i = 0; i < chkDias.length; i++) {
            chkDias[i] = new RoundedCheckBox(nombresDias[i]);
            int fila = (i < 3) ? 0 : 1;
            int col = (i < 3) ? i : (i - 3);
            chkDias[i].setBounds(100 + (col * 65), 330 + (fila * 40), 60, 30);
            panel.add(chkDias[i]);
        }

        RoundedButton btnGuardar = new RoundedButton("Terminar Registro", new Color(79, 190, 220));
        btnGuardar.setBounds(250, 440, 250, 45);
        btnGuardar.addActionListener(e -> ejecutarRegistroAdmin());
        panel.add(btnGuardar);
        
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
            MenuAdministrativo ventanaAnterior = new MenuAdministrativo(); 
            ventanaAnterior.setVisible(true);

            // 2. Cierras por completo la ventana actual para que no se amontone
            this.dispose(); 
        });
        panel.add(btnVolver);
    }

    protected void ejecutarRegistroAdmin() {
        try {
            String matricula = txtMatricula.getText().trim();
            if (matricula.isEmpty() || cmbChecador.getSelectedIndex() == -1 || cmbHrInicio.getSelectedIndex() == 0 || cmbHrFin.getSelectedIndex() == 0) {
                CustomDialog.mostrar(this, "Completa todos los campos.", CustomDialog.Tipo.ADVERTENCIA);
                return;
            }

            // 1. Validar existencia y PUESTO
            if (!existeEnBD("Empleados", "matricula", matricula)) {
                CustomDialog.mostrar(this, "La matrícula no existe en la base de datos.", CustomDialog.Tipo.ERROR);
                return;
            }
            if (!esPuestoValido(matricula, "Administrativo")) {
                CustomDialog.mostrar(this, "Esta matrícula no tiene permiso de Administrativo.", CustomDialog.Tipo.ERROR);
                return;
            }

            Time nuevaInicio = Time.valueOf(cmbHrInicio.getSelectedItem().toString() + ":00");
            Time nuevaFin = Time.valueOf(cmbHrFin.getSelectedItem().toString() + ":50");

            if (nuevaInicio.after(nuevaFin) || nuevaInicio.equals(nuevaFin)) {
                CustomDialog.mostrar(this, "Hora de inicio inválida.", CustomDialog.Tipo.ADVERTENCIA);
                return;
            }

            int idChecador = listaChecadores.get(cmbChecador.getSelectedIndex()).getId();
            
            try (Connection con = ConexionBD.conectar()) {
                con.setAutoCommit(false);
                
                // 2. Validación global de empalme
                for (int i = 0; i < chkDias.length; i++) {
                    if (chkDias[i].isSelected()) {
                        if (existeEmpalme(con, Integer.parseInt(matricula), nombresDias[i], nuevaInicio, nuevaFin)) {
                            CustomDialog.mostrar(this, "Empalme detectado el " + nombresDias[i], CustomDialog.Tipo.ADVERTENCIA);
                            con.rollback();
                            return;
                        }
                    }
                }

                String sql = "INSERT INTO Horario_Administrador (matricula, hora_inicio, hora_fin, dia_semana, id_checador) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    for (int i = 0; i < chkDias.length; i++) {
                        if (chkDias[i].isSelected()) {
                            ps.setInt(1, Integer.parseInt(matricula));
                            ps.setTime(2, nuevaInicio);
                            ps.setTime(3, nuevaFin);
                            ps.setString(4, nombresDias[i]);
                            ps.setInt(5, idChecador);
                            ps.executeUpdate();
                        }
                    }
                    con.commit();
                    CustomDialog.mostrar(this, "Registro administrativo guardado.", CustomDialog.Tipo.EXITO);
                    //this.dispose();
                }
            }
        } catch (Exception e) {
            CustomDialog.mostrar(this, "Error: " + e.getMessage(), CustomDialog.Tipo.ERROR);
        }
    }

    private boolean existeEnBD(String tabla, String columna, String valor) {
        String sql = "SELECT 1 FROM " + tabla + " WHERE " + columna + " = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { return false; }
    }

    private boolean esPuestoValido(String matricula, String puestoRequerido) {
        String sql = "SELECT puesto FROM Empleados WHERE matricula = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String puesto = rs.getString("puesto");
                    return puesto.equalsIgnoreCase(puestoRequerido) || puesto.equalsIgnoreCase("Ambos");
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private boolean existeEmpalme(Connection con, int mat, String dia, Time nI, Time nF) throws SQLException {
        String sql = "SELECT hora_inicio, hora_fin FROM Horario_Administrador WHERE matricula = ? AND dia_semana = ? " +
                     "UNION ALL SELECT hora_inicio, hora_fin FROM Horario_Clase WHERE matricula = ? AND dia_semana = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, mat); ps.setString(2, dia); ps.setInt(3, mat); ps.setString(4, dia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (nI.before(rs.getTime("hora_fin")) && nF.after(rs.getTime("hora_inicio"))) return true;
                }
            }
        }
        return false;
    }

    private RoundedComboBox crearComboChecador(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        try (Connection con = ConexionBD.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT id_checador, nombre FROM Checador")) {
            while (rs.next()) { listaChecadores.add(new Checador(rs.getInt("id_checador"), rs.getString("nombre"))); }
        } catch (SQLException e) { e.printStackTrace(); }
        RoundedComboBox cmb = new RoundedComboBox(listaChecadores.stream().map(Checador::toString).toArray(String[]::new));
        cmb.setBounds(x, y, 200, 35); cmb.setSelectedIndex(-1); p.add(cmb); return cmb;
    }

    private RoundedTextField crearCampo(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        RoundedTextField txt = new RoundedTextField(); txt.setBounds(x, y, 200, 35); p.add(txt); return txt;
    }

    private RoundedComboBox crearComboHoraEntrada(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        // Horas regulares: 07:00 a 21:00
        List<String> listaH = new ArrayList<>();
        listaH.add("");
        for (int i = 7; i <= 21; i++) listaH.add(String.format("%02d:00", i));
        // Horas extra para pruebas: 23:00 a 03:45 en intervalos de 15 min
        int[] horasExtra = {23, 0, 1, 2, 3};
        for (int h : horasExtra) {
            for (int min : new int[]{0, 15, 30, 45}) {
                if (h == 3 && min == 45) break; // última es 03:45
                listaH.add(String.format("%02d:%02d", h, min));
            }
        }
        RoundedComboBox cmb = new RoundedComboBox(listaH.toArray(new String[0]));
        cmb.setBounds(x, y, 200, 35); p.add(cmb); return cmb;
    }

    private RoundedComboBox crearComboHoraSalida(PanelDecorativo p, String label, int x, int y) {
        JLabel lbl = new JLabel(label); lbl.setBounds(x, y - 25, 150, 20); p.add(lbl);
        // Horas regulares: 07:50 a 21:50
        List<String> listaH = new ArrayList<>();
        listaH.add("");
        for (int i = 7; i <= 21; i++) listaH.add(String.format("%02d:50", i));
        // Horas extra para pruebas: 23:15 a 04:00 en intervalos de 15 min
        String[] extras = {"23:15","23:30","23:45","00:00","00:15","00:30","00:45",
                           "01:00","01:15","01:30","01:45","02:00","02:15","02:30","02:45",
                           "03:00","03:15","03:30","03:45","04:00"};
        for (String e : extras) listaH.add(e);
        RoundedComboBox cmb = new RoundedComboBox(listaH.toArray(new String[0]));
        cmb.setBounds(x, y, 200, 35); p.add(cmb); return cmb;
    }
}