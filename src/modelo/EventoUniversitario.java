package modelo;

import actividades.Actividad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;

    private Sala sala;
    private List<Actividad> actividades;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.sala = otro.sala;
        this.actividades = new ArrayList<>(otro.actividades);
        cantidadEventos++;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void agregarActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }

    public double calcularCostoEstimado() {
        double total = costoBase;
        for (Actividad a : actividades) {
            total += a.calcularCostoMateriales();
        }
        return total;
    }

    // Ejercicio 3: Método parametrizado acotado
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> filtradas = new ArrayList<>();
        for (Actividad a : actividades) {
            if (tipo.isInstance(a)) {
                filtradas.add(tipo.cast(a));
            }
        }
        return filtradas;
    }

    // Ejercicio 3: Método con Wildcards
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double costoTotal = 0;
        for (Actividad a : listaActividades) {
            costoTotal += a.calcularCostoMateriales();
        }
        return costoTotal;
    }

    public void mostrarDatos() {
        System.out.println("=== Evento Universitario ===");
        System.out.println("ID: " + id + " | Título: " + titulo);
        System.out.println("Costo Base: " + costoBase + " | Gratuito: " + gratuito);
        System.out.println("Sala asignada: " + (sala != null ? sala.getNombre() : "Sin sala"));
        System.out.println("Cantidad de actividades: " + actividades.size());
        for (Actividad a : actividades) {
            System.out.println(" - [" + a.getTipo() + "] " + a.getTitulo());
        }
    }

    public boolean persistirEvento() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(id + "_datos.ser"))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            System.err.println("Error al persistir el evento: " + e.getMessage());
            return false;
        }
    }

    public static EventoUniversitario recuperarEvento(String id) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(id + "_datos.ser"))) {
            return (EventoUniversitario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al recuperar el evento: " + e.getMessage());
            return null;
        }
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public String getTitulo() {
        return titulo;
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }
}
