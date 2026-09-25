package actividades;

import modelo.Estudiante;
import modelo.Inscripcion;
import excepciones.CupoExcedidoException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    protected int id;
    protected String titulo;
    protected int cupoMaximo;
    public static final int CUPO_MINIMO = 2;
    protected List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
        this.inscripciones = new ArrayList<>();
    }

    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("Cupo excedido para la actividad: " + titulo);
        }
        Inscripcion inscripcion = new Inscripcion(LocalDate.now(), "CONFIRMADA");
        inscripcion.setEstudiante(estudiante);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    public void mostrarInscripciones() {
        System.out.println("Inscripciones para la actividad (" + getTipo() + "): " + titulo);
        for (Inscripcion insc : inscripciones) {
            System.out.println(" - " + insc.getEstudiante().getNombre() + " [Estado: " + insc.getEstado() + "]");
        }
    }

    public final void mostrarIdentificacion() {
        System.out.println("ID Actividad: " + id + " | Título: " + titulo);
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public String getTitulo() {
        return titulo;
    }
}
