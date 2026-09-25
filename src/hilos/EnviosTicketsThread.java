package hilos;

import actividades.Actividad;
import modelo.EventoUniversitario;
import modelo.Inscripcion;

public class EnviosTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnviosTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("\n[HILO CONCURRENTE] Iniciando envío de tickets para el evento: " + evento.getTitulo());
        for (Actividad actividad : evento.getActividades()) {
            for (Inscripcion inscripcion : actividad.getInscripciones()) {
                if (inscripcion.getTicketDeAcceso() != null) {
                    try {
                        Thread.sleep(500); // Simula retardo en el envío
                        inscripcion.getTicketDeAcceso().enviarTicket();
                    } catch (InterruptedException e) {
                        System.err.println("El hilo de envío fue interrumpido.");
                    }
                }
            }
        }
        System.out.println("[HILO CONCURRENTE] Envío de tickets finalizado con éxito.\n");
    }
}