package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.bd.ConexionBD; 
import com.mycompany.administradorproyecto.disenio.CustomDialog;
import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.disenio.RoundedComboBox;
import com.mycompany.administradorproyecto.disenio.RoundedTextField;
import com.mycompany.administradorproyecto.model.Empleados;
import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

//para huella
import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.*;
import com.digitalpersona.onetouch.capture.event.*;
import com.digitalpersona.onetouch.processing.*;

public class EmpleadosAlta extends JFrame {
    
    // variables globales para usarlas en los botones
    private RoundedTextField txtNombre, txtApellidoP, txtApellidoM;
    private RoundedComboBox cmbPuesto;
    private JDateChooser jdFechaNac, jdFechaIng; 
    private RoundedButton btnRegistrar;
    //variables globales para lector de huellas
    private DPFPCapture lector;
    private DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPFeatureExtraction extractor =DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
    private String nombreTemp;
    private String apPTemp;
    private String apMTemp;
    private String puestoTemp;
    private JLabel lblEstadoHuella;
    private java.sql.Date fechaNacTemp;
    private java.sql.Date fechaIngTemp;

    public EmpleadosAlta() {
        configurarVentana();
        crearComponentes();
        inicializarLector();
    }
    
    // configuracion basica de la pantalla
    private void configurarVentana() {
        setTitle("Alta de Empleados");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        // fondo con las figuras azules
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 500);
        panel.setLayout(null);
        setContentPane(panel);

        // titulo de arriba
        JLabel lblTitulo = new JLabel("ALTA DE EMPLEADOS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(175, 40, 400, 40); 
        panel.add(lblTitulo);

        // =========================================================================
        // lado izquierdo
        // =========================================================================
        
        JLabel lblNombre = crearLabel("NOMBRE(S)");
        lblNombre.setBounds(50, 140, 140, 30);
        panel.add(lblNombre);

        txtNombre = new RoundedTextField();
        txtNombre.setBounds(200, 140, 160, 35);
        panel.add(txtNombre);

        JLabel lblApellidoP = crearLabel("APELLIDO P.");
        lblApellidoP.setBounds(50, 220, 140, 30);
        panel.add(lblApellidoP);

        txtApellidoP = new RoundedTextField();
        txtApellidoP.setBounds(200, 220, 160, 35);
        panel.add(txtApellidoP);

        JLabel lblApellidoM = crearLabel("APELLIDO M.");
        lblApellidoM.setBounds(50, 300, 140, 30);
        panel.add(lblApellidoM);

        txtApellidoM = new RoundedTextField();
        txtApellidoM.setBounds(200, 300, 160, 35);
        panel.add(txtApellidoM);

        // =========================================================================
        // lado derecho
        // =========================================================================
        
        JLabel lblPuesto = crearLabel("PUESTO");
        lblPuesto.setBounds(400, 140, 130, 30);
        panel.add(lblPuesto);

        cmbPuesto = new RoundedComboBox(new String[]{"Profesor", "Administrativo", "Ambos"});
        cmbPuesto.setBounds(540, 140, 160, 35);
        cmbPuesto.setSelectedIndex(-1); 
        panel.add(cmbPuesto);

        JLabel lblFechaNac = crearLabel("F. NACIMIENTO");
        lblFechaNac.setBounds(400, 220, 130, 30);
        panel.add(lblFechaNac);

        jdFechaNac = new JDateChooser();
        jdFechaNac.setDateFormatString("yyyy-MM-dd"); 
        jdFechaNac.setBounds(540, 220, 160, 35);
        aplicarEstiloCalendario(jdFechaNac); 
        panel.add(jdFechaNac);

        JLabel lblFechaIng = crearLabel("F. INGRESO");
        lblFechaIng.setBounds(400, 300, 130, 30);
        panel.add(lblFechaIng);

        jdFechaIng = new JDateChooser();
        jdFechaIng.setDateFormatString("yyyy-MM-dd");
        jdFechaIng.setBounds(540, 300, 160, 35);
        aplicarEstiloCalendario(jdFechaIng); 
        panel.add(jdFechaIng);
        
        //
        //  LABEL DE LA HUELLA
        //
        lblEstadoHuella = new JLabel("Presione Terminar Registro para registrar huella");
        lblEstadoHuella.setFont(new Font("Arial", Font.BOLD, 16));
        lblEstadoHuella.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstadoHuella.setBounds(180,350,400,30);
        panel.add(lblEstadoHuella);
        
        // boton para guardar
        Color rosa = new Color(255, 100, 130);
        btnRegistrar = new RoundedButton("Terminar registro", rosa);
        btnRegistrar.setBounds(265, 400, 220, 40);
        panel.add(btnRegistrar);

        // evento del boton
        btnRegistrar.addActionListener(e -> ejecutarAltaEmpleado());
        
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
            Empleados ventanaAnterior = new Empleados(); 
            ventanaAnterior.setVisible(true);

            // 2. Cierras por completo la ventana actual para que no se amontone
            this.dispose(); 
        });
        panel.add(btnVolver);
    }

    // metodo para crear labels mas rapido
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
    
    // logica para validar y guardar en la base de datos
    private void ejecutarAltaEmpleado() {
        // quitamos espacios en blanco
        String nombre = txtNombre.getText().trim();
        String apPaterno = txtApellidoP.getText().trim();
        String apMaterno = txtApellidoM.getText().trim();

        // revisamos que no esten vacios
        if (nombre.isEmpty() || apPaterno.isEmpty() || apMaterno.isEmpty()) {
            CustomDialog.mostrar(this, "Todos los campos de texto\nson obligatorios.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        // revisamos que elija un puesto
        if (cmbPuesto.getSelectedIndex() == -1) {
            CustomDialog.mostrar(this, "Por favor, seleccione un puesto\npara el empleado.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }
        String puesto = cmbPuesto.getSelectedItem().toString();

        // revisamos que si le den click a una fecha en el calendario
        if (jdFechaNac.getDate() == null || jdFechaIng.getDate() == null) {
            CustomDialog.mostrar(this, "Por favor, seleccione ambas\nfechas en el calendario.", CustomDialog.Tipo.ADVERTENCIA);
            return;
        }

        // --- VALIDACIONES DE EDAD Y FECHAS ---
        Date hoy = new Date(); 
        Date fNac = jdFechaNac.getDate();
        Date fIng = jdFechaIng.getDate();

        // 1. que no nazca en el futuro ni el dia de hoy
        if (fNac.after(hoy) || fNac.equals(hoy)) {
            CustomDialog.mostrar(this, "La fecha de nacimiento no puede\nser hoy ni una fecha futura.", CustomDialog.Tipo.ERROR);
            return;
        }

        // calculamos la fecha en la que cumplio 18 años
        Calendar calNacimiento = Calendar.getInstance();
        calNacimiento.setTime(fNac);
        calNacimiento.add(Calendar.YEAR, 18); 
        
        // 2. validamos que al dia de hoy ya tenga 18 años
        if (calNacimiento.getTime().after(hoy)) {
            CustomDialog.mostrar(this, "El empleado debe ser mayor\nde edad (18 años).", CustomDialog.Tipo.ERROR);
            return;
        }

        // 3. que no entre a jalar en el futuro 
        if (fIng.after(hoy)) {
            CustomDialog.mostrar(this, "La fecha de ingreso no puede\nser en el futuro.", CustomDialog.Tipo.ERROR);
            return;
        }

        // 4. que no haya entrado a trabajar siendo menor de edad
        if (fIng.before(calNacimiento.getTime())) {
            CustomDialog.mostrar(this, "La fecha de ingreso es inválida.\nEl empleado era menor de edad\ncuando ingresó.", CustomDialog.Tipo.ERROR);
            return;
        }

        // pasamos las fechas a formato de mysql
        java.sql.Date fechaNacimientoSQL = new java.sql.Date(fNac.getTime());
        java.sql.Date fechaIngresoSQL = new java.sql.Date(fIng.getTime());
        
        // guardar datos temporalmente
        nombreTemp = nombre;
        apPTemp = apPaterno;
        apMTemp = apMaterno;
        puestoTemp = puesto;

        fechaNacTemp = fechaNacimientoSQL;
        fechaIngTemp = fechaIngresoSQL;

        // iniciar enrolamiento biométrico
        iniciarCapturaHuella();
        lblEstadoHuella.setText("Coloque la huella en el sensor");
        // conexion a la bd
        /*try (Connection con = ConexionBD.conectar()) {
            if (con != null) {
                String sql = "INSERT INTO Empleados (nombre, apellidop, apellidom, fecha_nac, puesto, fecha_ingreso, huella) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, nombre);
                    ps.setString(2, apPaterno);
                    ps.setString(3, apMaterno);
                    ps.setDate(4, fechaNacimientoSQL); 
                    ps.setString(5, puesto);
                    ps.setDate(6, fechaIngresoSQL);
                    
                    // TODO: cambiar esto cuando hagamos lo del checador real
                    String huellaSimuladaStr = "01010101110011010101"; 
                    byte[] huellaBytes = huellaSimuladaStr.getBytes();
                    ps.setBytes(7, huellaBytes); 

                    ps.executeUpdate();
                    
                    // sacamos la matricula que nos da la bd y mostramos mensaje chido
                    try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            long matriculaGenerada = rs.getLong(1);
                            CustomDialog.mostrar(this, "¡Empleado guardado exitosamente!\n\nMATRÍCULA ASIGNADA: " + matriculaGenerada, CustomDialog.Tipo.EXITO);
                        }
                    }
                    
                    limpiarFormulario();
                }
            }
        } catch (SQLException ex) {
            CustomDialog.mostrar(this, "Error de base de datos:\n" + ex.getMessage(), CustomDialog.Tipo.ERROR);
        }*/
    }
    private void iniciarCapturaHuella() {
        lector.stopCapture();
        enrollment.clear();
        CustomDialog.mostrar(
            this,
            "Coloque la huella varias veces\nhasta completar el registro.",
            CustomDialog.Tipo.ADVERTENCIA
        );
        
        lector.addDataListener(
            new DPFPDataAdapter() {
                @Override
                public void dataAcquired(
                        DPFPDataEvent e
                ) {
                    procesarHuella(
                            e.getSample()
                    );
                    lblEstadoHuella.setText("Huella detectada");
                }
            }
        );
        lector.startCapture();
    }
    private void procesarHuella(DPFPSample sample) {
        try {
            DPFPFeatureSet features =
                extractor.createFeatureSet(sample,DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
            if(features != null) {
                enrollment.addFeatures(features );
                int restantes =enrollment.getFeaturesNeeded();
                lblEstadoHuella.setText("Faltan "+ restantes+ " lecturas");
                System.out.println("Muestras restantes: "+ restantes);

                if(restantes > 0) {
                    CustomDialog.mostrar(this,
                        "Huella capturada.\nFaltan "+ restantes+ " lecturas.",CustomDialog.Tipo.ADVERTENCIA );
                }
                // TEMPLATE COMPLETO
                if(restantes == 0) {
                    lector.stopCapture();
                    guardarEmpleadoConHuella(
                            enrollment.getTemplate()
                    );
                    lblEstadoHuella.setText("Huella registrada correctamente");
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            CustomDialog.mostrar(this,"Error procesando huella", CustomDialog.Tipo.ERROR);
            lblEstadoHuella.setText("Huella no válida, intente otra vez");
        }
    }
    
    private void guardarEmpleadoConHuella( DPFPTemplate template) {
        String sql =
            "INSERT INTO Empleados "
          + "(nombre, apellidop, apellidom, fecha_nac, puesto, fecha_ingreso, huella) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con =ConexionBD.conectar();
        PreparedStatement ps =con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){ 
            ps.setString(1, nombreTemp);
            ps.setString(2, apPTemp);
            ps.setString(3, apMTemp);
            ps.setDate(4, fechaNacTemp);
            ps.setString(5, puestoTemp);
            ps.setDate(6, fechaIngTemp);
            // AQUÍ ESTÁ LA PARTE IMPORTANTE PARA LA PLANTILLA DEL BLOB
            ps.setBytes( 7,template.serialize());
            ps.executeUpdate();
            try(ResultSet rs =ps.getGeneratedKeys()) {
                if(rs.next()) {
                    long matricula =rs.getLong(1);
                    
                    CustomDialog.mostrar(this,"Empleado registrado.\n"+ "Matrícula: "+ matricula,
                        CustomDialog.Tipo.EXITO);
                }
            }
            limpiarFormulario();
            enrollment.clear();
        } catch(Exception ex) {
            ex.printStackTrace();
            CustomDialog.mostrar(
                this,
                "Error guardando empleado",
                CustomDialog.Tipo.ERROR
            );
        }
    }
    
    // vacia los textfields y calendarios
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        cmbPuesto.setSelectedIndex(-1);
        jdFechaNac.setDate(null); 
        jdFechaIng.setDate(null); 
    }

    // esto hace que el calendario combine con nuestro diseño
    private void aplicarEstiloCalendario(JDateChooser jd) {
        javax.swing.JTextField cajaTexto = (javax.swing.JTextField) jd.getDateEditor().getUiComponent();
        cajaTexto.setBackground(Color.WHITE);
        cajaTexto.setForeground(Color.DARK_GRAY);
        cajaTexto.setFont(new Font("Arial", Font.BOLD, 14));
        
        Color azul = new Color(79, 190, 220);
        Color rosa = new Color(255, 100, 130);
        cajaTexto.setBorder(javax.swing.BorderFactory.createLineBorder(azul, 2));

        if (jd.getCalendarButton() != null) {
            jd.getCalendarButton().setBackground(azul);
            jd.getCalendarButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
        com.toedter.calendar.JCalendar calendarioInterno = jd.getJCalendar();
        Font fuenteModerna = new Font("Segoe UI", Font.PLAIN, 14); 
        calendarioInterno.setFont(fuenteModerna);
        Color textoOscuro = new Color(50, 50, 50);
        calendarioInterno.getDayChooser().getDayPanel().setBackground(Color.WHITE);
        calendarioInterno.getDayChooser().setDecorationBackgroundColor(Color.WHITE); 
        calendarioInterno.getDayChooser().setSundayForeground(rosa); 
        calendarioInterno.getDayChooser().setForeground(textoOscuro);
        calendarioInterno.getDayChooser().setWeekOfYearVisible(false);
        calendarioInterno.getMonthChooser().getComboBox().setFont(fuenteModerna);
        calendarioInterno.getMonthChooser().getComboBox().setBackground(Color.WHITE);
        calendarioInterno.getYearChooser().getSpinner().setFont(fuenteModerna);
        calendarioInterno.getYearChooser().getSpinner().setBackground(Color.WHITE);
    }
    private void inicializarLector() {
        try {
            lector =DPFPGlobal.getCaptureFactory().createCapture();
            System.out.println("Lector listo");
        } catch(Exception ex) {
            ex.printStackTrace();
            CustomDialog.mostrar(this,"Error inicializando lector",CustomDialog.Tipo.ERROR);
        }
    }
}