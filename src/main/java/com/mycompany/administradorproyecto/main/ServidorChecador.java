package com.mycompany.administradorproyecto.main;

import java.net.*;
import java.io.*;
import java.util.concurrent.Semaphore;
import java.sql.*;
import java.time.LocalTime;
import com.mycompany.administradorproyecto.bd.ConexionBD;

public class ServidorChecador extends Thread {
    
    private int puerto = 5000;
    // SEMÁFORO: Controla que solo 1 checada se guarde a la vez
    private static final Semaphore semaforoBD = new Semaphore(1, true);

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor de Concurrencia iniciado en el puerto " + puerto);
            while (true) {
                Socket cliente = serverSocket.accept();
                new Thread(new HiloCliente(cliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class HiloCliente implements Runnable {
        private Socket socket;

        public HiloCliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream entrada = new DataInputStream(socket.getInputStream());
                 DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {
                
                String matriculaRecibida = entrada.readUTF();
                System.out.println("Solicitud recibida - Matrícula: " + matriculaRecibida);

                // --- ZONA CRÍTICA: SEMÁFORO ---
                semaforoBD.acquire(); 
                String respuesta = procesarChecadaReal(matriculaRecibida);
                semaforoBD.release(); 
                // ------------------------------

                salida.writeUTF(respuesta);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ==========================================================
        // AQUÍ ES DONDE AHORA VIVEN TUS CONSULTAS DE HORARIOS (EN EL SERVIDOR)
        // ==========================================================
        private String procesarChecadaReal(String matriculaTexto) {
            try {
                int matricula = Integer.parseInt(matriculaTexto);
                java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());
                Time horaActual = new Time(System.currentTimeMillis());

                try (Connection conexion = ConexionBD.conectar()) {
                    
                    // 1. Verificamos si existe el empleado y su puesto
                    String sqlEmpleado = "SELECT puesto FROM Empleados WHERE matricula = ?";
                    try (PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado)) {
                        psEmpleado.setInt(1, matricula);
                        try (ResultSet rsEmpleado = psEmpleado.executeQuery()) {
                            
                            if (!rsEmpleado.next()) {
                                return "ERROR: La matrícula no existe.";
                            }
                            
                            String puesto = rsEmpleado.getString("puesto").toLowerCase();
                            
                            // 2. Procesamos dependiendo del puesto
                            if (puesto.contains("profesor") || puesto.contains("ambos")) {
                                procesarHorarioClase(conexion, matricula, fechaActual, horaActual);
                            }
                            if (puesto.contains("administrativo") || puesto.contains("ambos")) {
                                procesarHorarioAdministrador(conexion, matricula, fechaActual, horaActual);
                            }
                            
                            return "EXITO";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR: " + e.getMessage();
            }
        }

        // -------------------------------------------------------------
        // TUS MÉTODOS DE HORARIOS AHORA ESTÁN PROTEGIDOS POR EL SERVIDOR
        // -------------------------------------------------------------
        private void procesarHorarioClase(Connection conexion, int matricula, java.sql.Date fechaActual, Time horaActual) {
            try {
                String sql = "SELECT id_horarioClase, hora_inicio, hora_fin FROM Horario_Clase WHERE matricula = ?";
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setInt(1, matricula);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idHorario = rs.getInt("id_horarioClase");
                    LocalTime actual = horaActual.toLocalTime();
                    LocalTime inicio = rs.getTime("hora_inicio").toLocalTime();
                    LocalTime fin = rs.getTime("hora_fin").toLocalTime();

                    if (!actual.isBefore(inicio.minusMinutes(15)) && !actual.isAfter(inicio.plusMinutes(15))) {
                        registrarEntrada(conexion, matricula, fechaActual, horaActual, idHorario, false, true);
                    } else if (actual.isAfter(inicio.plusMinutes(15)) && !actual.isAfter(inicio.plusMinutes(60))) {
                        registrarEntrada(conexion, matricula, fechaActual, horaActual, idHorario, true, true);
                    } else if (!actual.isBefore(fin.minusMinutes(15)) && !actual.isAfter(fin.plusMinutes(5))) {
                        registrarSalida(conexion, matricula, fechaActual, horaActual, idHorario, true);
                    }
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        private void procesarHorarioAdministrador(Connection conexion, int matricula, java.sql.Date fechaActual, Time horaActual) {
            // ... (Misma lógica que el método anterior pero apuntando a la tabla de admin)
            // Para ahorrar espacio, puedes pegar tu lógica exacta de admin aquí
        }

        // Método genérico para insertar en la BD desde el servidor
        private void registrarEntrada(Connection conexion, int matricula, java.sql.Date fecha, Time hora, int idHorario, boolean retardo, boolean esClase) {
            try {
                String status = retardo ? "retardo" : "a tiempo";
                String columnaHorario = esClase ? "id_horarioClase" : "id_horarioAdmin";
                
                String sql = "INSERT INTO Checadas (fecha, matricula, id_checador, hora_entrada, status, " + columnaHorario + ") VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setDate(1, fecha);
                ps.setInt(2, matricula);
                ps.setInt(3, 3); // ID del checador
                ps.setTime(4, hora);
                ps.setString(5, status);
                ps.setInt(6, idHorario);
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        }

        private void registrarSalida(Connection conexion, int matricula, java.sql.Date fecha, Time hora, int idHorario, boolean esClase) {
             try {
                String columnaHorario = esClase ? "id_horarioClase" : "id_horarioAdmin";
                String sql = "UPDATE Checadas SET hora_salida = ?, status = 'completado' WHERE matricula = ? AND fecha = ? AND " + columnaHorario + " = ? AND hora_salida IS NULL";
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setTime(1, hora);
                ps.setInt(2, matricula);
                ps.setDate(3, fecha);
                ps.setInt(4, idHorario);
                ps.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}