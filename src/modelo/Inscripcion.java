package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private TicketDeAcceso ticketDeAcceso;

    public Inscripcion(LocalDate fecha, String estado) {
        this.fecha = fecha;
        this.estado = estado;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public String getEstado() {
        return estado;
    }

    public void generarTicket() {
        if ("CONFIRMADA".equalsIgnoreCase(estado)) {
            this.ticketDeAcceso = new TicketDeAcceso("TICKET-" + System.currentTimeMillis());
        }
    }

    public TicketDeAcceso getTicketDeAcceso() {
        return ticketDeAcceso;
    }

    // Clase anidada miembro
    public class TicketDeAcceso implements Serializable {
        private LocalDate fechaEmision;
        private String codigoTicket;

        public TicketDeAcceso(String codigoTicket) {
            this.fechaEmision = LocalDate.now();
            this.codigoTicket = codigoTicket;
        }

        public void enviarTicket() {
            System.out.println("Enviando ticket [Código: " + codigoTicket + "] al estudiante: " + estudiante.getNombre());
        }

        public String getCodigoTicket() {
            return codigoTicket;
        }
    }
}
