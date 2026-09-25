package actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends Actividad implements Certificable {
    private double costoExtra;

    public Curso(int id, String titulo, int cupoMaximo, double costoExtra) {
        super(id, titulo, cupoMaximo);
        this.costoExtra = costoExtra;
    }

    @Override
    public double calcularCostoMateriales() {
        return 2000.0 + costoExtra;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "Certificado de Curso emitido por " + ENTIDAD_EMISORA + " para " + estudiante.getNombre() + " en el curso: " + titulo;
    }
}