package com.mycompany.administradorproyecto.ventanas;

import com.mycompany.administradorproyecto.disenio.PanelDecorativo;
import com.mycompany.administradorproyecto.disenio.RoundedButton;
import com.mycompany.administradorproyecto.disenio.RoundedComboBox;
import com.mycompany.administradorproyecto.disenio.RoundedTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import com.mycompany.administradorproyecto.bd.ConexionBD;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

// --- IMPORTS PARA CONEXIÓN REAL ---
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// --- IMPORTS PARA LA GENERACIÓN DE PDF (iText) ---
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.administradorproyecto.disenio.CustomDialog;
import java.io.FileOutputStream;
import java.io.File;
import java.awt.Desktop;

public class MenuChecador extends JFrame {
    
    // Componentes globales para poder leer sus datos al generar el PDF
    private RoundedComboBox cmbChecador;
    private JDateChooser jdFecha;
    private RoundedTextField txtMatricula;
    private RoundedComboBox cmbMesDesempenio;

    public MenuChecador() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Panel de Reportes y Estadísticas");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setResizable(false);
        setLayout(null);
    }

    private void crearComponentes() {
        // Panel con tamaño completo de la ventana
        PanelDecorativo panel = new PanelDecorativo();
        panel.setBounds(0, 0, 750, 550);
        panel.setLayout(null);
        setContentPane(panel);

        // Fuentes y Colores del diseño
        Font fuenteTitulo = new Font("Arial", Font.BOLD, 24);
        Font fuenteSubtitulos = new Font("Arial", Font.BOLD, 16);
        Font fuenteEtiquetas = new Font("Arial", Font.PLAIN, 14);
        Font fuenteBotones = new Font("Arial", Font.BOLD, 14);
        
        Color azul = new Color(79, 190, 220);
        Color morado = new Color(130, 100, 210);

        // --- TITULO PRINCIPAL Y SELECTOR DE CHECADOR ---
        JLabel lblTitulo = new JLabel("REPORTES Y ESTADÍSTICAS");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(50, 20, 650, 35);
        panel.add(lblTitulo);

        JLabel lblSeleccione = new JLabel("Selecciona el checador:");
        lblSeleccione.setFont(fuenteEtiquetas);
        lblSeleccione.setBounds(145, 75, 150, 30);
        panel.add(lblSeleccione);

        // JComboBox para seleccionar cuál de los 3 checadores auditar
        cmbChecador = new RoundedComboBox(new String[]{"1 - UAE1", "2 - CC", "3 - CNT"});
        cmbChecador.setBounds(330, 75, 240, 35);
        cmbChecador.setSelectedIndex(-1);
        panel.add(cmbChecador);

        JSeparator sepPrincipal = new JSeparator();
        sepPrincipal.setBounds(50, 130, 650, 2);
        sepPrincipal.setForeground(morado);
        panel.add(sepPrincipal);

        // REPORTE DIARIO DE ASISTENCIA
        // -----------------------------------------------------------------------
        JLabel lblSeccionA = new JLabel("REPORTE DIARIO DE ASISTENCIA");
        lblSeccionA.setFont(fuenteSubtitulos);
        lblSeccionA.setForeground(morado);
        lblSeccionA.setBounds(210, 150, 450, 25);
        panel.add(lblSeccionA);
 
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(fuenteEtiquetas);
        lblFecha.setBounds(70, 200, 50, 30);
        panel.add(lblFecha);

        jdFecha = new JDateChooser();
        jdFecha.setDateFormatString("yyyy-MM-dd"); 
        jdFecha.setBounds(150, 200, 160, 35);
        aplicarEstiloCalendario(jdFecha); 
        panel.add(jdFecha);
        
        RoundedButton btnPDFDiario = new RoundedButton("Generar PDF Diario", azul);
        btnPDFDiario.setFont(fuenteBotones);
        btnPDFDiario.setBounds(460, 200, 210, 35);
        panel.add(btnPDFDiario);

        JSeparator sepIntermedio = new JSeparator();
        sepIntermedio.setBounds(50, 270, 650, 2);
        sepIntermedio.setForeground(new Color(220, 220, 220));
        panel.add(sepIntermedio);

        // REPORTE MENSUAL DE DESEMPEÑO
        // ------------------------------------------------------------------
        JLabel lblSeccionB = new JLabel("REPORTE MENSUAL DE DESEMPEÑO");
        lblSeccionB.setFont(fuenteSubtitulos);
        lblSeccionB.setForeground(morado);
        lblSeccionB.setBounds(190, 290, 450, 25);
        panel.add(lblSeccionB);

        JLabel lblMatricula = new JLabel("Matrícula Empleado:");
        lblMatricula.setFont(fuenteEtiquetas);
        lblMatricula.setBounds(70, 340, 150, 30);
        panel.add(lblMatricula);

        txtMatricula = new RoundedTextField();
        txtMatricula.setBounds(220, 340, 120, 35);
        panel.add(txtMatricula);

        JLabel lblMes = new JLabel("Mes a evaluar:");
        lblMes.setFont(fuenteEtiquetas);
        lblMes.setBounds(370, 340, 100, 30);
        panel.add(lblMes);

        cmbMesDesempenio = new RoundedComboBox(new String[]{
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        cmbMesDesempenio.setBounds(480, 340, 140, 35);
        panel.add(cmbMesDesempenio);

        RoundedButton btnPDFMensual = new RoundedButton("Generar Expediente PDF", morado);
        btnPDFMensual.setFont(fuenteBotones);
        btnPDFMensual.setBounds(245, 420, 260, 45); // Centrado abajo
        panel.add(btnPDFMensual);

        // --- LOGICA DE DISPARO DE EVENTOS ---
        btnPDFDiario.addActionListener(e -> accionReporteDiario());
        btnPDFMensual.addActionListener(e -> accionReporteMensual());
        
        //Botones para regresar-------------------------------
        RoundedButton btnHome = new RoundedButton("⌂", morado);
        btnHome.setFont(new Font("Arial", Font.BOLD, 18)); 
        btnHome.setBounds(75, 20, 50, 35); 
        btnHome.addActionListener(e -> {
            new Menu().setVisible(true);
            this.dispose(); 
        });
        panel.add(btnHome);
    }

    // --- MANEJADORES DE LOGICA ---
    private void accionReporteDiario() {
        try {
            int indexChecador = cmbChecador.getSelectedIndex();
            if (indexChecador == -1) {
                throw new Exception("Por favor, selecciona primero un origen en la lista de checadores.");
            }
            int idChecador = indexChecador + 1;

            if (jdFecha.getDate() == null) {
                throw new Exception("Debe seleccionar una fecha válida usando el calendario.");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(jdFecha.getDate());
            String nombreChecador = cmbChecador.getSelectedItem().toString();

            // Por cada empleado que checó ese día en ese checador, tomamos:
            //   - hora_entrada: el primer registro del día (el menor)
            //   - hora_salida:  la última hora_salida no nula del día
            //   - estado_dia:   derivado del status real de la tabla
            //       · "completo"   → al menos una fila tiene hora_salida NOT NULL y status IN ('completo')
            //       · "incompleto" → hay hora_entrada pero hora_salida es NULL en todas las filas,
            //                        o el status registrado lo dice así
            //   La tabla real NO tiene columna "tipo"; el status distingue la entrada de la salida.
            //   Usamos MIN(hora_entrada) como la hora en que llegó y MAX(hora_salida) como salida.
            String sqlDiario =
                "SELECT c.matricula, " +
                "       e.nombre, e.apellidop, " +
                "       MIN(c.hora_entrada) AS hora_entrada, " +
                "       MAX(c.hora_salida)  AS hora_salida, " +
                "       MAX(c.status)       AS ultimo_status " +
                "FROM Checadas c " +
                "JOIN Empleados e ON c.matricula = e.matricula " +
                "WHERE c.id_checador = ? AND c.fecha = ? " +
                "GROUP BY c.matricula, e.nombre, e.apellidop " +
                "ORDER BY MIN(c.hora_entrada) ASC";

            String nombreArchivoPDF = "Reporte_Diario_" + fechaFormateada + ".pdf";
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivoPDF));
            documento.open();

            com.itextpdf.text.Font fontTitulo       = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new BaseColor(41, 128, 185));
            com.itextpdf.text.Font fontMeta         = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
            com.itextpdf.text.Font fontHeaderTabla  = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            com.itextpdf.text.Font fontCuerpoTabla  = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            Paragraph tituloPDF = new Paragraph("REPORTE DIARIO DE ASISTENCIA", fontTitulo);
            tituloPDF.setAlignment(Element.ALIGN_CENTER);
            tituloPDF.setSpacingAfter(15);
            documento.add(tituloPDF);

            documento.add(new Paragraph("Origen / Checador: " + nombreChecador, fontMeta));
            documento.add(new Paragraph("Fecha Consultada: " + fechaFormateada, fontMeta));
            documento.add(new Paragraph("Generado el: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()), fontMeta));

            Paragraph espacio = new Paragraph(" ");
            espacio.setSpacingAfter(10);
            documento.add(espacio);

            PdfPTable tablaPDF = new PdfPTable(5);
            tablaPDF.setWidthPercentage(100);
            tablaPDF.setWidths(new float[]{1.5f, 3.5f, 2.0f, 2.0f, 2.5f});

            BaseColor colorHeader = new BaseColor(41, 128, 185);
            for (String enc : new String[]{"Matrícula", "Empleado", "Hora Entrada", "Hora Salida", "Estado"}) {
                PdfPCell h = new PdfPCell(new Phrase(enc, fontHeaderTabla));
                h.setBackgroundColor(colorHeader);
                h.setHorizontalAlignment(Element.ALIGN_CENTER);
                h.setVerticalAlignment(Element.ALIGN_MIDDLE);
                h.setPadding(8);
                tablaPDF.addCell(h);
            }

            int totalRegistros = 0;

            try (Connection con = ConexionBD.conectar();
                 PreparedStatement ps = con.prepareStatement(sqlDiario)) {

                ps.setInt(1, idChecador);
                ps.setString(2, fechaFormateada);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        totalRegistros++;
                        String mat    = rs.getString("matricula");
                        String nom    = rs.getString("nombre") + " " + rs.getString("apellidop");
                        String hIn    = rs.getString("hora_entrada") != null ? rs.getString("hora_entrada") : "--:--:--";
                        String hOut   = rs.getString("hora_salida")  != null ? rs.getString("hora_salida")  : "--:--:--";

                        // Traducir el status al texto del PDF
                        // status posibles: "a tiempo", "completo", "retardo", "incompleto"
                        // Para el reporte diario mostramos el estado final del día:
                        //   completo   → ASISTENCIA
                        //   a tiempo   → OMISIÓN DE SALIDA (entró a tiempo pero no hay salida)
                        //   retardo    → OMISIÓN DE SALIDA (entró tarde y no hay salida)
                        //   incompleto → INCOMPLETO (entró tarde y registró salida)
                        String statusRaw = rs.getString("ultimo_status");
                        if (statusRaw == null) statusRaw = "incompleto";
                        String estadoFinal;
                        switch (statusRaw.toLowerCase()) {
                            case "completo":   estadoFinal = "ASISTENCIA";       break;
                            case "a tiempo":   estadoFinal = "OMISIÓN DE SALIDA"; break;
                            case "retardo":    estadoFinal = "OMISIÓN DE SALIDA"; break;
                            case "incompleto": estadoFinal = "INCOMPLETO";        break;
                            default:           estadoFinal = statusRaw.toUpperCase();
                        }

                        PdfPCell c1 = new PdfPCell(new Phrase(mat, fontCuerpoTabla));    c1.setPadding(6); c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c2 = new PdfPCell(new Phrase(nom, fontCuerpoTabla));    c2.setPadding(6);
                        PdfPCell c3 = new PdfPCell(new Phrase(hIn, fontCuerpoTabla));    c3.setPadding(6); c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c4 = new PdfPCell(new Phrase(hOut, fontCuerpoTabla));   c4.setPadding(6); c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c5 = new PdfPCell(new Phrase(estadoFinal, fontCuerpoTabla)); c5.setPadding(6); c5.setHorizontalAlignment(Element.ALIGN_CENTER);

                        tablaPDF.addCell(c1); tablaPDF.addCell(c2); tablaPDF.addCell(c3);
                        tablaPDF.addCell(c4); tablaPDF.addCell(c5);
                    }
                }
            }

            if (totalRegistros == 0) {
                documento.close();
                new File(nombreArchivoPDF).delete();
                JOptionPane.showMessageDialog(this,
                    "No se encontraron checadas para la fecha " + fechaFormateada + " en el checador seleccionado.",
                    "Sin Registros", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            documento.add(tablaPDF);
            documento.close();

            File archivoFinal = new File(nombreArchivoPDF);
            CustomDialog.mostrar(this,
                "Se encontraron " + totalRegistros + " registros.\n\n¡PDF Generado con éxito!\n\nUbicación:\n" + archivoFinal.getAbsolutePath(),
                CustomDialog.Tipo.EXITO);

            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(archivoFinal);

        } catch (Exception ex) {
            CustomDialog.mostrar(this, "Error en el sistema:\n" + ex.getMessage(), CustomDialog.Tipo.ERROR);
            ex.printStackTrace();
        }
    }

    private void accionReporteMensual() {
        try {
            int indexChecador = cmbChecador.getSelectedIndex();
            if (indexChecador == -1) throw new Exception("Por favor, selecciona primero un origen en la lista de checadores.");
            int idChecador = indexChecador + 1;

            String matriculaStr = txtMatricula.getText().trim();
            if (matriculaStr.isEmpty()) throw new Exception("Por favor, ingrese la matrícula del empleado.");
            int idEmp = Integer.parseInt(matriculaStr);

            int mesSeleccionado = cmbMesDesempenio.getSelectedIndex() + 1;
            String nombreMes    = cmbMesDesempenio.getSelectedItem().toString();
            String nombreChecador = cmbChecador.getSelectedItem().toString();

            String puestoEmpleado = "";
            String nombreEmpleado = "";

            String nombreArchivoPDF = "Expediente_Mensual_" + idEmp + "_" + nombreMes + ".pdf";
            Document documento = new Document();

            try (Connection con = ConexionBD.conectar()) {

                // 1. Validar que el empleado exista
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT nombre, apellidop, puesto FROM Empleados WHERE matricula = ?")) {
                    ps.setInt(1, idEmp);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            nombreEmpleado = rs.getString("nombre") + " " + rs.getString("apellidop");
                            puestoEmpleado = rs.getString("puesto");
                        } else {
                            throw new Exception("La matrícula " + idEmp + " no corresponde a ningún empleado registrado.");
                        }
                    }
                }

                PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivoPDF));
                documento.open();

                com.itextpdf.text.Font fontTitulo      = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new BaseColor(142, 68, 173));
                com.itextpdf.text.Font fontMeta        = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
                com.itextpdf.text.Font fontHeader      = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
                com.itextpdf.text.Font fontCuerpo      = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
                com.itextpdf.text.Font fontResumen     = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.DARK_GRAY);
                BaseColor colorMorado = new BaseColor(142, 68, 173);

                Paragraph titulo = new Paragraph("EXPEDIENTE MENSUAL DE DESEMPEÑO", fontTitulo);
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.setSpacingAfter(15);
                documento.add(titulo);

                documento.add(new Paragraph("Matrícula: " + idEmp, fontMeta));
                documento.add(new Paragraph("Empleado: " + nombreEmpleado, fontMeta));
                documento.add(new Paragraph("Puesto: " + puestoEmpleado, fontMeta));
                documento.add(new Paragraph("Mes Evaluado: " + nombreMes + " | Ubicación: " + nombreChecador, fontMeta));
                documento.add(new Paragraph("Generado el: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()), fontMeta));
                Paragraph esp = new Paragraph(" "); esp.setSpacingAfter(15); documento.add(esp);

                // ---------------------------------------------------------------
                // 2. Construir la consulta según puesto
                // La tabla Checadas tiene: id_checadas, fecha, matricula,
                //   id_checador, hora_entrada, hora_salida, status,
                //   id_horarioClase, id_horarioAdmin
                //
                // Reglas de status (ya registradas en BD):
                //   "a tiempo"  → llegó a tiempo, aún sin salida
                //   "completo"  → llegó a tiempo y registró salida
                //   "retardo"   → llegó tarde, aún sin salida
                //   "incompleto"→ llegó tarde y registró salida
                //
                // Para el expediente mostramos cada fila de Checadas del mes.
                // Si tiene horario de clase → mostramos materia/aula.
                // Si tiene horario admin    → mostramos "-" en materia/aula.
                // ---------------------------------------------------------------

                // Contadores para el resumen final
                int cntCompleto   = 0; // "completo"
                int cntAtiempo    = 0; // "a tiempo"  (omisión de salida, llegó bien)
                int cntRetardo    = 0; // "retardo"   (omisión de salida, llegó tarde)
                int cntIncompleto = 0; // "incompleto" (retardo + registró salida)
                int cntFalta      = 0; // días de horario sin ninguna checada

                // ---- BLOQUE PROFESOR / AMBOS: tabla de clases ----
                boolean tieneClases = puestoEmpleado.equalsIgnoreCase("Profesor")
                                   || puestoEmpleado.equalsIgnoreCase("Ambos");
                boolean tieneAdmin  = puestoEmpleado.equalsIgnoreCase("Administrativo")
                                   || puestoEmpleado.equalsIgnoreCase("Ambos");

                if (tieneClases) {
                    documento.add(new Paragraph("▸ Horarios de Clase", fontResumen));
                    Paragraph espSec = new Paragraph(" "); espSec.setSpacingAfter(5); documento.add(espSec);

                    PdfPTable tabla = new PdfPTable(5);
                    tabla.setWidthPercentage(100);
                    tabla.setWidths(new float[]{2.0f, 2.0f, 1.5f, 2.5f, 2.5f});
                    for (String enc : new String[]{"Fecha", "Materia", "Aula", "Hora de Checado", "Estado"}) {
                        PdfPCell h = new PdfPCell(new Phrase(enc, fontHeader));
                        h.setBackgroundColor(colorMorado); h.setHorizontalAlignment(Element.ALIGN_CENTER);
                        h.setVerticalAlignment(Element.ALIGN_MIDDLE); h.setPadding(8);
                        tabla.addCell(h);
                    }

                    // Primero: todas las filas de Checadas con id_horarioClase NOT NULL
                    String sqlClase =
                        "SELECT c.fecha, hc.clave_materia, hc.aula, " +
                        "       c.hora_entrada, c.hora_salida, c.status " +
                        "FROM Checadas c " +
                        "JOIN Horario_Clase hc ON c.id_horarioClase = hc.id_horarioClase " +
                        "WHERE c.matricula = ? AND c.id_checador = ? AND MONTH(c.fecha) = ? " +
                        "ORDER BY c.fecha ASC, c.hora_entrada ASC";

                    // Después: días del mes con horario de clase donde NO hubo ninguna checada → FALTA
                    String sqlFaltasClase =
                        "SELECT DISTINCT hc.dia_semana, hc.clave_materia, hc.aula " +
                        "FROM Horario_Clase hc " +
                        "WHERE hc.matricula = ? AND hc.id_checador = ? " +
                        "  AND NOT EXISTS ( " +
                        "      SELECT 1 FROM Checadas c2 " +
                        "      WHERE c2.matricula = hc.matricula " +
                        "        AND c2.id_checador = hc.id_checador " +
                        "        AND c2.id_horarioClase = hc.id_horarioClase " +
                        "        AND MONTH(c2.fecha) = ? " +
                        "  )";

                    int filasClase = 0;
                    try (PreparedStatement ps = con.prepareStatement(sqlClase)) {
                        ps.setInt(1, idEmp); ps.setInt(2, idChecador); ps.setInt(3, mesSeleccionado);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                filasClase++;
                                String fecha    = rs.getString("fecha");
                                String materia  = rs.getString("clave_materia");
                                String aula     = rs.getString("aula");
                                String hEntrada = rs.getString("hora_entrada") != null ? rs.getString("hora_entrada") : "--:--:--";
                                String hSalida  = rs.getString("hora_salida")  != null ? rs.getString("hora_salida")  : "--:--:--";
                                String status   = rs.getString("status") != null ? rs.getString("status").toLowerCase() : "incompleto";

                                String estadoLabel = traducirStatus(status);
                                // Hora de checado: mostramos entrada; si hay salida la sumamos con guión
                                String horaChecado = hEntrada;
                                if (!hSalida.equals("--:--:--")) horaChecado += " / " + hSalida;

                                contarStatus(status, new int[]{cntCompleto, cntAtiempo, cntRetardo, cntIncompleto});
                                switch (status) {
                                    case "completo":    cntCompleto++;   break;
                                    case "a tiempo":    cntAtiempo++;    break;
                                    case "retardo":     cntRetardo++;    break;
                                    case "incompleto":  cntIncompleto++; break;
                                }

                                agregarFilaTabla(tabla, fontCuerpo,
                                    fecha, materia, aula, horaChecado, estadoLabel);
                            }
                        }
                    }

                    // Marcar faltas (días del mes con horario pero sin checada)
                    try (PreparedStatement ps = con.prepareStatement(sqlFaltasClase)) {
                        ps.setInt(1, idEmp); ps.setInt(2, idChecador); ps.setInt(3, mesSeleccionado);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                filasClase++;
                                cntFalta++;
                                agregarFilaTabla(tabla, fontCuerpo,
                                    nombreMes + " (" + rs.getString("dia_semana") + ")",
                                    rs.getString("clave_materia"),
                                    rs.getString("aula"),
                                    "--:--:--", "FALTA");
                            }
                        }
                    }

                    if (filasClase > 0) documento.add(tabla);
                    else documento.add(new Paragraph("Sin registros de clase para este mes en el checador seleccionado.", fontCuerpo));

                    Paragraph espAfter = new Paragraph(" "); espAfter.setSpacingAfter(12); documento.add(espAfter);
                }

                // ---- BLOQUE ADMINISTRATIVO / AMBOS: tabla de admin ----
                if (tieneAdmin) {
                    documento.add(new Paragraph("▸ Horarios Administrativos", fontResumen));
                    Paragraph espSec = new Paragraph(" "); espSec.setSpacingAfter(5); documento.add(espSec);

                    PdfPTable tablaAdmin = new PdfPTable(4);
                    tablaAdmin.setWidthPercentage(100);
                    tablaAdmin.setWidths(new float[]{2.0f, 3.0f, 2.5f, 2.5f});
                    for (String enc : new String[]{"Fecha", "Hora de Checado", "Estado", "Día"}) {
                        PdfPCell h = new PdfPCell(new Phrase(enc, fontHeader));
                        h.setBackgroundColor(new BaseColor(41, 128, 185));
                        h.setHorizontalAlignment(Element.ALIGN_CENTER);
                        h.setVerticalAlignment(Element.ALIGN_MIDDLE); h.setPadding(8);
                        tablaAdmin.addCell(h);
                    }

                    String sqlAdmin =
                        "SELECT c.fecha, c.hora_entrada, c.hora_salida, c.status, " +
                        "       ha.dia_semana " +
                        "FROM Checadas c " +
                        "JOIN Horario_Administrador ha ON c.id_horarioAdmin = ha.id_horarioAdministrador " +
                        "WHERE c.matricula = ? AND c.id_checador = ? AND MONTH(c.fecha) = ? " +
                        "ORDER BY c.fecha ASC, c.hora_entrada ASC";

                    String sqlFaltasAdmin =
                        "SELECT DISTINCT ha.dia_semana " +
                        "FROM Horario_Administrador ha " +
                        "WHERE ha.matricula = ? AND ha.id_checador = ? " +
                        "  AND NOT EXISTS ( " +
                        "      SELECT 1 FROM Checadas c2 " +
                        "      WHERE c2.matricula = ha.matricula " +
                        "        AND c2.id_checador = ha.id_checador " +
                        "        AND c2.id_horarioAdmin = ha.id_horarioAdministrador " +
                        "        AND MONTH(c2.fecha) = ? " +
                        "  )";

                    int filasAdmin = 0;
                    try (PreparedStatement ps = con.prepareStatement(sqlAdmin)) {
                        ps.setInt(1, idEmp); ps.setInt(2, idChecador); ps.setInt(3, mesSeleccionado);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                filasAdmin++;
                                String fecha    = rs.getString("fecha");
                                String hEntrada = rs.getString("hora_entrada") != null ? rs.getString("hora_entrada") : "--:--:--";
                                String hSalida  = rs.getString("hora_salida")  != null ? rs.getString("hora_salida")  : "--:--:--";
                                String status   = rs.getString("status") != null ? rs.getString("status").toLowerCase() : "incompleto";
                                String diaSemana = rs.getString("dia_semana");

                                String horaChecado = hEntrada;
                                if (!hSalida.equals("--:--:--")) horaChecado += " / " + hSalida;

                                switch (status) {
                                    case "completo":    cntCompleto++;   break;
                                    case "a tiempo":    cntAtiempo++;    break;
                                    case "retardo":     cntRetardo++;    break;
                                    case "incompleto":  cntIncompleto++; break;
                                }

                                PdfPCell ca = new PdfPCell(new Phrase(fecha, fontCuerpo));       ca.setPadding(6); ca.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell cb = new PdfPCell(new Phrase(horaChecado, fontCuerpo)); cb.setPadding(6); cb.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell cc = new PdfPCell(new Phrase(traducirStatus(status), fontCuerpo)); cc.setPadding(6); cc.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell cd = new PdfPCell(new Phrase(diaSemana, fontCuerpo));   cd.setPadding(6); cd.setHorizontalAlignment(Element.ALIGN_CENTER);
                                tablaAdmin.addCell(ca); tablaAdmin.addCell(cb);
                                tablaAdmin.addCell(cc); tablaAdmin.addCell(cd);
                            }
                        }
                    }

                    try (PreparedStatement ps = con.prepareStatement(sqlFaltasAdmin)) {
                        ps.setInt(1, idEmp); ps.setInt(2, idChecador); ps.setInt(3, mesSeleccionado);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                filasAdmin++;
                                cntFalta++;
                                String dia = rs.getString("dia_semana");
                                PdfPCell ca = new PdfPCell(new Phrase(nombreMes, fontCuerpo));  ca.setPadding(6); ca.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell cb = new PdfPCell(new Phrase("--:--:--", fontCuerpo)); cb.setPadding(6); cb.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell cc = new PdfPCell(new Phrase("FALTA", fontCuerpo));    cc.setPadding(6); cc.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell cd = new PdfPCell(new Phrase(dia, fontCuerpo));        cd.setPadding(6); cd.setHorizontalAlignment(Element.ALIGN_CENTER);
                                tablaAdmin.addCell(ca); tablaAdmin.addCell(cb);
                                tablaAdmin.addCell(cc); tablaAdmin.addCell(cd);
                            }
                        }
                    }

                    if (filasAdmin > 0) documento.add(tablaAdmin);
                    else documento.add(new Paragraph("Sin registros administrativos para este mes en el checador seleccionado.", fontCuerpo));

                    Paragraph espAfter = new Paragraph(" "); espAfter.setSpacingAfter(12); documento.add(espAfter);
                }

                // ---- RESUMEN FINAL ----
                Paragraph espResumen = new Paragraph(" "); espResumen.setSpacingAfter(10); documento.add(espResumen);

                com.itextpdf.text.Font fontTituloRes = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, colorMorado);
                Paragraph tituloRes = new Paragraph("RESUMEN DEL MES", fontTituloRes);
                tituloRes.setSpacingAfter(8);
                documento.add(tituloRes);

                PdfPTable tablaResumen = new PdfPTable(2);
                tablaResumen.setWidthPercentage(60);
                tablaResumen.setHorizontalAlignment(Element.ALIGN_LEFT);
                tablaResumen.setWidths(new float[]{3.5f, 1.5f});

                agregarFilaResumen(tablaResumen, fontResumen, fontCuerpo, "✔ Asistencias completas",       String.valueOf(cntCompleto));
                agregarFilaResumen(tablaResumen, fontResumen, fontCuerpo, "⚠ Omisión de salida (a tiempo)", String.valueOf(cntAtiempo));
                agregarFilaResumen(tablaResumen, fontResumen, fontCuerpo, "⏰ Retardos con salida (incompleto)", String.valueOf(cntIncompleto));
                agregarFilaResumen(tablaResumen, fontResumen, fontCuerpo, "⏰ Omisión de salida (retardo)",  String.valueOf(cntRetardo));
                agregarFilaResumen(tablaResumen, fontResumen, fontCuerpo, "✘ Faltas",                        String.valueOf(cntFalta));
                agregarFilaResumen(tablaResumen, fontResumen, fontCuerpo, "TOTAL REGISTROS",
                    String.valueOf(cntCompleto + cntAtiempo + cntIncompleto + cntRetardo + cntFalta));

                documento.add(tablaResumen);
            }

            documento.close();

            File archivoFinal = new File(nombreArchivoPDF);
            CustomDialog.mostrar(this,
                "Expediente generado para: " + nombreEmpleado + "\n\n¡PDF creado exitosamente!\n\nUbicación:\n" + archivoFinal.getAbsolutePath(),
                CustomDialog.Tipo.EXITO);

            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(archivoFinal);

        } catch (NumberFormatException nfe) {
            CustomDialog.mostrar(this, "La matrícula debe ser un número entero válido.", CustomDialog.Tipo.ERROR);
        } catch (Exception ex) {
            CustomDialog.mostrar(this, "Error en el sistema:\n" + ex.getMessage(), CustomDialog.Tipo.ERROR);
            ex.printStackTrace();
        }
    }

    // ---------- Helpers privados ----------

    /** Traduce el status de BD al texto legible del PDF */
    private String traducirStatus(String status) {
        if (status == null) return "INCOMPLETO";
        switch (status.toLowerCase()) {
            case "completo":    return "ASISTENCIA";
            case "a tiempo":    return "OMISIÓN DE SALIDA";
            case "retardo":     return "RETARDO";
            case "incompleto":  return "INCOMPLETO";
            default:            return status.toUpperCase();
        }
    }

    /** Agrega una fila de 5 celdas a la tabla de clases */
    private void agregarFilaTabla(PdfPTable t, com.itextpdf.text.Font f,
                                   String fecha, String materia, String aula,
                                   String hora, String estado) {
        PdfPCell c1 = new PdfPCell(new Phrase(fecha,    f)); c1.setPadding(6); c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell c2 = new PdfPCell(new Phrase(materia,  f)); c2.setPadding(6); c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell c3 = new PdfPCell(new Phrase(aula,     f)); c3.setPadding(6); c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell c4 = new PdfPCell(new Phrase(hora,     f)); c4.setPadding(6); c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell c5 = new PdfPCell(new Phrase(estado,   f)); c5.setPadding(6); c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        t.addCell(c1); t.addCell(c2); t.addCell(c3); t.addCell(c4); t.addCell(c5);
    }

    /** Agrega una fila de 2 celdas al resumen */
    private void agregarFilaResumen(PdfPTable t,
                                     com.itextpdf.text.Font fLabel,
                                     com.itextpdf.text.Font fVal,
                                     String label, String valor) {
        PdfPCell cl = new PdfPCell(new Phrase(label, fLabel)); cl.setPadding(6); cl.setBorderColor(BaseColor.LIGHT_GRAY);
        PdfPCell cv = new PdfPCell(new Phrase(valor, fVal));   cv.setPadding(6); cv.setHorizontalAlignment(Element.ALIGN_CENTER); cv.setBorderColor(BaseColor.LIGHT_GRAY);
        t.addCell(cl); t.addCell(cv);
    }

    /** Stub vacío — los contadores se incrementan inline con switch en el cuerpo del método */
    private void contarStatus(String s, int[] arr) { /* no-op, se cuenta inline */ }
    
    private void aplicarEstiloCalendario(JDateChooser jd) {
        javax.swing.JTextField cajaTexto = (javax.swing.JTextField) jd.getDateEditor().getUiComponent();
        cajaTexto.setBackground(Color.WHITE);
        cajaTexto.setForeground(Color.DARK_GRAY);
        cajaTexto.setFont(new Font("Arial", Font.BOLD, 14));
        
        Color azul = new Color(79, 190, 220);
        cajaTexto.setBorder(javax.swing.BorderFactory.createLineBorder(azul, 2));

        if (jd.getCalendarButton() != null) {
            jd.getCalendarButton().setBackground(azul);
            jd.getCalendarButton().setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }

        com.toedter.calendar.JCalendar calendarioInterno = jd.getJCalendar();
        Font fuenteModerna = new Font("Segoe UI", Font.PLAIN, 14); 
        calendarioInterno.setFont(fuenteModerna);
        
        calendarioInterno.getDayChooser().getDayPanel().setBackground(Color.WHITE);
        calendarioInterno.getDayChooser().setDecorationBackgroundColor(Color.WHITE); 
        calendarioInterno.getDayChooser().setSundayForeground(new Color(255, 100, 130)); 
        calendarioInterno.getDayChooser().setForeground(new Color(50, 50, 50));
        calendarioInterno.getDayChooser().setWeekOfYearVisible(false);
        
        calendarioInterno.getMonthChooser().getComboBox().setFont(fuenteModerna);
        calendarioInterno.getMonthChooser().getComboBox().setBackground(Color.WHITE);
        calendarioInterno.getYearChooser().getSpinner().setFont(fuenteModerna);
        calendarioInterno.getYearChooser().getSpinner().setBackground(Color.WHITE);
    }
}