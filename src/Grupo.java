import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private String nombre;
    private Carrera carrera;
    private List<Alumno> alumnos = new ArrayList<>();
    public Grupo(String nombre, Carrera carrera) {
        this.nombre = nombre;
        this.carrera = carrera;
    }

    public void addAlumno(Alumno alumno) {
        if (!this.alumnos.contains(alumno)) this.alumnos.add(alumno);
        if (alumno.getGrupo() != this) alumno.setGrupo(this);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public double promedio() {
        return this.alumnos.stream().mapToDouble(Alumno::getPromedio).sum() / this.alumnos.size();
    }

    public String toString() {
        return this.carrera + "-" + this.nombre;
    }

    public static boolean contains(List<Grupo> grupos, String grupo) {
        for (var g : grupos) {
            if (g.getNombre().equals(grupo)) return true;
        }
        return false;
    }

    public static Grupo find(List<Grupo> grupos, String grupo) {
        for (var g : grupos) {
            if (g.getNombre().equals(grupo)) return g;
        }
        return null;
    }
}
