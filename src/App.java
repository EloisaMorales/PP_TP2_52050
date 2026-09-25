import actividades.Actividad;
import actividades.Charla;
import actividades.Curso;
import actividades.Taller;
import certificacion.Certificable;
import excepciones.CupoExcedidoException;
import hilos.EnviosTicketsThread;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("--- INICIO DE LA APLICACIÓN - TP2 PARADIGMAS ---");

        // a. Crear estudiantes
        Estudiante est1 = new Estudiante("51440", "Valentin Morales");
        Estudiante est2 = new Estudiante("51441", "Sofia Sales");
        Estudiante est3 = new Estudiante("51442", "Isabella Sales");

        // b, c. Crear evento, sala y asignarla
        EventoUniversitario evento = new EventoUniversitario("EV-2026", "Jornadas de Tecnologías Avanzadas", 5000.0, false);
        Sala sala1 = new Sala(101, "Aula Magna FRM");
        evento.asignarSala(sala1);

        // d. Construir actividades (Charla, Taller y Curso)
        Charla charla1 = new Charla(1, "Inteligencia Artificial en la Industria", 2, "Dr. Juan Perez");
        Taller taller1 = new Taller(2, "Programación en Java Avanzado", 2, true);
        Curso curso1 = new Curso(3, "Arquitectura de Software", 2, 1000.0);

        evento.agregarActividad(charla1);
        evento.agregarActividad(taller1);
        evento.agregarActividad(curso1);

        // e. Inscribir alumnos y probar control de excepciones (Cupo Excedido)
        try {
            System.out.println("\nIntentando inscripciones a Charla...");
            charla1.inscribir(est1);
            charla1.inscribir(est2);

            // Esta línea lanza CupoExcedidoException porque el cupo máximo es 2
            charla1.inscribir(est3);

        } catch (CupoExcedidoException e) {
            System.err.println("[EXCEPCIÓN CONTROLADA]: " + e.getMessage());
        }

        // Inscripciones válidas para taller y curso, generando sus tickets (Ej. 4)
        try {
            Inscripcion insTaller = taller1.inscribir(est1);
            insTaller.generarTicket();

            Inscripcion insCurso = curso1.inscribir(est2);
            insCurso.generarTicket();
        } catch (CupoExcedidoException e) {
            System.err.println("[EXCEPCIÓN CONTROLADA]: " + e.getMessage());
        }

        // f. Emitir certificados de asistencia (talleres y cursos, charlas no)
        System.out.println("\n--- EMISIÓN DE CERTIFICADOS ---");
        for (Actividad act : evento.getActividades()) {
            if (act instanceof Certificable) {
                Certificable certAct = (Certificable) act;
                for (Inscripcion ins : act.getInscripciones()) {
                    System.out.println(certAct.generarCertificado(ins.getEstudiante()));
                }
            }
        }

        // g. Filtrar actividades por tipo (Genéricos) y calcular costos (Wildcards)
        System.out.println("\n--- FILTRADO Y COSTOS ---");
        List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
        System.out.println("Talleres filtrados encontrados: " + talleres.size());

        double costoTalleres = evento.calcularCostoMateriales(talleres);
        System.out.println("Costo total de materiales de talleres: $" + costoTalleres);

        // h. Concurrencia: Iniciar hilo de envío de tickets y continuar mostrando datos en paralelo
        EnviosTicketsThread hiloTickets = new EnviosTicketsThread(evento);
        hiloTickets.start();

        System.out.println("\n[HILO PRINCIPAL] Mostrando datos del evento concurrentemente...");
        evento.mostrarDatos();

        // Esperar a que el hilo secundario termine de enviar los tickets
        try {
            hiloTickets.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Persistencia y recuperación de objetos
        System.out.println("\n--- PERSISTENCIA ---");
        boolean guardado = evento.persistirEvento();
        if (guardado) {
            System.out.println("Evento persistido correctamente.");
            EventoUniversitario eventoRecuperado = EventoUniversitario.recuperarEvento("EV-2026");
            if (eventoRecuperado != null) {
                System.out.println("Evento recuperado con éxito. Título: " + eventoRecuperado.getTitulo());
            }
        }

        System.out.println("\n--- FIN DE LA APLICACIÓN ---");
    }
}