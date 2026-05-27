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

            // --- CONSULTA ADAPTADA PARA STATUS 'completo' / 'incompleto' ---
            String sqlDiario = "SELECT c.matricula, e.nombre, e.apellidop, " +
                               "MAX(CASE WHEN c.tipo = 'Entrada' THEN c.hora END) AS hora_entrada, " +
                               "MAX(CASE WHEN c.tipo = 'Salida' THEN c.hora END) AS hora_salida, " +
                               "CASE WHEN COUNT(DISTINCT c.tipo) >= 2 THEN 'completo' ELSE 'incompleto' END AS estado_dia " +
                               "FROM Checadas c " +
                               "JOIN Empleados e ON c.matricula = e.matricula " +
                               "WHERE c.id_checador = ? AND c.fecha = ? " +
                               "GROUP BY c.matricula, e.nombre, e.apellidop " +
                               "ORDER BY hora_entrada ASC";

            String nombreArchivoPDF = "Reporte_Diario_" + fechaFormateada + ".pdf";
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivoPDF));
            
            documento.open();
            
            com.itextpdf.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new BaseColor(41, 128, 185));
            com.itextpdf.text.Font fontTextoMetadatos = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
            com.itextpdf.text.Font fontHeaderTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            com.itextpdf.text.Font fontCuerpoTabla = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            Paragraph tituloPDF = new Paragraph("REPORTE DIARIO DE ASISTENCIA", fontTitulo);
            tituloPDF.setAlignment(Element.ALIGN_CENTER);
            tituloPDF.setSpacingAfter(15);
            documento.add(tituloPDF);
            
            documento.add(new Paragraph("Origen / Checador: " + nombreChecador, fontTextoMetadatos));
            documento.add(new Paragraph("Fecha Consultada: " + fechaFormateada, fontTextoMetadatos));
            documento.add(new Paragraph("Generado el: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()), fontTextoMetadatos));
            
            Paragraph lineaEspacio = new Paragraph(" ");
            lineaEspacio.setSpacingAfter(10);
            documento.add(lineaEspacio);

            PdfPTable tablaPDF = new PdfPTable(5);
            tablaPDF.setWidthPercentage(100);
            tablaPDF.setWidths(new float[]{1.5f, 3.5f, 2.0f, 2.0f, 2.0f});

            BaseColor colorHeader = new BaseColor(41, 128, 185); 
            String[] encabezados = {"Matrícula", "Empleado", "Hora Entrada", "Hora Salida", "Estado"};
            
            for (String enc : encabezados) {
                PdfPCell celdaHeader = new PdfPCell(new Phrase(enc, fontHeaderTabla));
                celdaHeader.setBackgroundColor(colorHeader);
                celdaHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaHeader.setPadding(8);
                tablaPDF.addCell(celdaHeader);
            }

            System.out.println("\n--- ENCONTRADO REGISTROS DIARIOS EN CONSOLA ---");
            int totalRegistros = 0;

            try (Connection con = ConexionBD.conectar();
                 PreparedStatement ps = con.prepareStatement(sqlDiario)) {
                
                ps.setInt(1, idChecador);
                ps.setString(2, fechaFormateada);
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        totalRegistros++;
                        String mat = rs.getString("matricula");
                        String nom = rs.getString("nombre") + " " + rs.getString("apellidop");
                        String hIn = rs.getString("hora_entrada") != null ? rs.getString("hora_entrada") : "--:--:--";
                        String hOut = rs.getString("hora_salida") != null ? rs.getString("hora_salida") : "--:--:--";
                        
                        // Formatear el status ('completo' o 'incompleto') con mayúscula inicial para el PDF
                        String statRaw = rs.getString("estado_dia"); 
                        String statFormateado = "Incompleto";
                        if (statRaw != null && !statRaw.isEmpty()) {
                            statFormateado = statRaw.substring(0, 1).toUpperCase() + statRaw.substring(1);
                        }
                        
                        System.out.println("Matrícula: " + mat + " | Empleado: " + nom + " | Entrada: " + hIn + " | Salida: " + hOut + " | Estado: " + statRaw);
                        
                        PdfPCell c1 = new PdfPCell(new Phrase(mat, fontCuerpoTabla)); c1.setPadding(6); c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c2 = new PdfPCell(new Phrase(nom, fontCuerpoTabla)); c2.setPadding(6);
                        PdfPCell c3 = new PdfPCell(new Phrase(hIn, fontCuerpoTabla)); c3.setPadding(6); c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c4 = new PdfPCell(new Phrase(hOut, fontCuerpoTabla)); c4.setPadding(6); c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell c5 = new PdfPCell(new Phrase(statFormateado, fontCuerpoTabla)); c5.setPadding(6); c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                        
                        tablaPDF.addCell(c1);
                        tablaPDF.addCell(c2);
                        tablaPDF.addCell(c3);
                        tablaPDF.addCell(c4);
                        tablaPDF.addCell(c5);
                    }
                }
            }

            if (totalRegistros == 0) {
                documento.close();
                new File(nombreArchivoPDF).delete(); 
                JOptionPane.showMessageDialog(this, "No se encontraron checadas para la fecha " + fechaFormateada + " en el checador seleccionado.", "Sin Registros", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            documento.add(tablaPDF);
            documento.close();

            File archivoFinal = new File(nombreArchivoPDF);
            String mensajeExito = "Se encontraron " + totalRegistros + " registros.\n\n"
                    + "¡PDF Generado con éxito!\n\n"
                    + "Ubicación:\n" + archivoFinal.getAbsolutePath();
            
            CustomDialog.mostrar(this, mensajeExito, CustomDialog.Tipo.EXITO);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivoFinal);
            }

        } catch (Exception ex) {
            String mensajeError = "Error en el sistema:\n" + ex.getMessage();
            CustomDialog.mostrar(this, mensajeError, CustomDialog.Tipo.ERROR);
            ex.printStackTrace();
        }
    }

    private void accionReporteMensual() {
        try {
            int indexChecador = cmbChecador.getSelectedIndex();
            if (indexChecador == -1) {
                throw new Exception("Por favor, selecciona primero un origen en la lista de checadores.");
            }
            int idChecador = indexChecador + 1;

            String matriculaStr = txtMatricula.getText().trim();
            if (matriculaStr.isEmpty()) {
                throw new Exception("Por favor, ingrese la matrícula del empleado.");
            }
            int idEmp = Integer.parseInt(matriculaStr);

            int mesSeleccionado = cmbMesDesempenio.getSelectedIndex() + 1; 
            String nombreMes = cmbMesDesempenio.getSelectedItem().toString();
            String nombreChecador = cmbChecador.getSelectedItem().toString();

            String puestoEmpleado = "";
            String nombreEmpleado = "";
            
            String nombreArchivoPDF = "Expediente_Mensual_" + idEmp + "_" + nombreMes + ".pdf";
            Document documento = new Document();
            
            try (Connection con = ConexionBD.conectar()) {
                String sqlBuscar = "SELECT nombre, apellidop, puesto FROM Empleados WHERE matricula = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlBuscar)) {
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

                com.itextpdf.text.Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new BaseColor(142, 68, 173)); 
                com.itextpdf.text.Font fontTextoMetadatos = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);
                com.itextpdf.text.Font fontHeaderTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
                com.itextpdf.text.Font fontCuerpoTabla = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

                Paragraph tituloPDF = new Paragraph("EXPEDIENTE MENSUAL DE DESEMPEÑO", fontTitulo);
                tituloPDF.setAlignment(Element.ALIGN_CENTER);
                tituloPDF.setSpacingAfter(15);
                documento.add(tituloPDF);

                documento.add(new Paragraph("Matrícula: " + idEmp, fontTextoMetadatos));
                documento.add(new Paragraph("Empleado: " + nombreEmpleado, fontTextoMetadatos));
                documento.add(new Paragraph("Puesto: " + puestoEmpleado, fontTextoMetadatos));
                documento.add(new Paragraph("Mes Evaluado: " + nombreMes + " | Ubicación: " + nombreChecador, fontTextoMetadatos));
                documento.add(new Paragraph("Generado el: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()), fontTextoMetadatos));
                
                Paragraph lineaEspacio = new Paragraph(" ");
                lineaEspacio.setSpacingAfter(15);
                documento.add(lineaEspacio);

                int conteo = 0;

                System.out.println("\n== Analizando comportamiento mensual real ==");
                System.out.println("Empleado: " + nombreEmpleado + " | Puesto Detectado: " + puestoEmpleado);

                if (puestoEmpleado.equalsIgnoreCase("Profesor")) {
                    System.out.println("-> Extrayendo datos mensuales de la tabla: Horario_Clase");
                    
                    PdfPTable tablaPDF = new PdfPTable(5);
                    tablaPDF.setWidthPercentage(100);
                    tablaPDF.setWidths(new float[]{2.0f, 2.0f, 1.5f, 2.5f, 2.0f});

                    BaseColor colorHeaderMorado = new BaseColor(142, 68, 173);
                    String[] encProfesor = {"Fecha", "Materia", "Aula", "Hora de Checado", "Estado"};
                    
                    for (String enc : encProfesor) {
                        PdfPCell celdaHeader = new PdfPCell(new Phrase(enc, fontHeaderTabla));
                        celdaHeader.setBackgroundColor(colorHeaderMorado);
                        celdaHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celdaHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        celdaHeader.setPadding(8);
                        tablaPDF.addCell(celdaHeader);
                    }

                    // --- CONSULTA MENSUAL ADAPTADA A REQUISITOS ---
                    String sqlMensualProfesor = "SELECT c.fecha, h.clave_materia, h.aula, c.hora, c.status " +
                                                "FROM Checadas c " +
                                                "JOIN Horario_Clase h ON c.matricula = h.matricula AND c.id_checador = h.id_checador " +
                                                "WHERE c.matricula = ? AND MONTH(c.fecha) = ? AND c.id_checador = ? AND c.tipo = 'Entrada'";
                    
                    try (PreparedStatement psProf = con.prepareStatement(sqlMensualProfesor)) {
                        psProf.setInt(1, idEmp);
                        psProf.setInt(2, mesSeleccionado);
                        psProf.setInt(3, idChecador);
                        
                        try (ResultSet rsProf = psProf.executeQuery()) {
                            while (rsProf.next()) {
                                conteo++;
                                String fecha = rsProf.getString("fecha");
                                String materia = rsProf.getString("clave_materia");
                                String aula = rsProf.getString("aula");
                                String hora = rsProf.getString("hora") != null ? rsProf.getString("hora") : "--:--:--";
                                
                                // Formatear el status con mayúscula inicial ('Completo' / 'Incompleto')
                                String statusRaw = rsProf.getString("status") != null ? rsProf.getString("status") : "incompleto";
                                String statusFormateado = statusRaw.substring(0, 1).toUpperCase() + statusRaw.substring(1);

                                System.out.println("Fecha: " + fecha + " | Materia: " + materia + " | Aula: " + aula + " | Checó: " + hora + " | Status: " + statusRaw);
                                
                                PdfPCell c1 = new PdfPCell(new Phrase(fecha, fontCuerpoTabla)); c1.setPadding(6); c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell c2 = new PdfPCell(new Phrase(materia, fontCuerpoTabla)); c2.setPadding(6); c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell c3 = new PdfPCell(new Phrase(aula, fontCuerpoTabla)); c3.setPadding(6); c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell c4 = new PdfPCell(new Phrase(hora, fontCuerpoTabla)); c4.setPadding(6); c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                                PdfPCell c5 = new PdfPCell(new Phrase(statusFormateado, fontCuerpoTabla)); c5.setPadding(6); c5.setHorizontalAlignment(Element.ALIGN_CENTER);

                                tablaPDF.addCell(c1);
                                tablaPDF.addCell(c2);
                                tablaPDF.addCell(c3);
                                tablaPDF.addCell(c4);
                                tablaPDF.addCell(c5);
                            }
                        }
                    }
                    System.out.println("Total de asistencias en el mes evaluado: " + conteo);
                    
                    if (conteo > 0) {
                        documento.add(tablaPDF);
                    } else {
                        documento.add(new Paragraph("No se registran firmas de asistencia vinculadas a Horario_Clase en este mes.", fontCuerpoTabla));
                    }
                    
                } else if (puestoEmpleado.equalsIgnoreCase("Admin")) {
                    documento.add(new Paragraph("Área Administrativa: Historial de asistencia mensual en proceso de vinculación técnica.", fontCuerpoTabla));
                } else if (puestoEmpleado.equalsIgnoreCase("Ambos")) {
                    documento.add(new Paragraph("Perfil Mixto: Módulos de doble asistencia en fase de desarrollo.", fontCuerpoTabla));
                }
            } 
            
            documento.close();
            
            File archivoFinal = new File(nombreArchivoPDF);
            String mensajeExito = "Expediente Mensual obtenido.\n\n"
                    + "Empleado: " + nombreEmpleado + "\n"
                    + "Puesto: " + puestoEmpleado + "\n\n"
                    + "¡Expediente PDF Generado exitosamente!";
            
            CustomDialog.mostrar(this, mensajeExito, CustomDialog.Tipo.EXITO);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivoFinal);
            }

        } catch (NumberFormatException nfe) {
            CustomDialog.mostrar(this, "La matrícula debe ser un número entero válido.", CustomDialog.Tipo.ERROR);
        } catch (Exception ex) {
            String mensajeError = "Error en el sistema:\n" + ex.getMessage();
            CustomDialog.mostrar(this, mensajeError, CustomDialog.Tipo.ERROR);
            ex.printStackTrace();
        }
    }
    
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
