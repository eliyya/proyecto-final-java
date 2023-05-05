
import java.util.ArrayList;
import java.util.List;

public class Carrera {
    private String nombre;
    private List<Grupo> grupos = new ArrayList<>();
    public Carrera(String nombre) {
        this.nombre = nombre;
    }

    public void addGrupo(Grupo grupo) {
        if (!this.grupos.contains(grupo)) this.grupos.add(grupo);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public double promedio() {
        return this.grupos.stream().mapToDouble(Grupo::promedio).sum() / this.grupos.size();
    }

    public List<Alumno> getAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        for (var g : this.grupos) {
            alumnos.addAll(g.getAlumnos());
        }
        return alumnos;
    }

    public String toString() {
        return this.nombre;
    }

    public static boolean contains(List<Carrera> carreras, String carrera) {
        for (var c : carreras) {
            if (c.getNombre().equals(carrera)) return true;
        }
        return false;
    }

    public static Carrera find(List<Carrera> carreras, String carrera) {
        for (var c : carreras) {
            if (c.getNombre().equals(carrera)) return c;
        }
        return null;
    }

    public List<Alumno> encontrarAlumnos(String numero) {
        List<Alumno> alumnos = new ArrayList<>();
        for (var alumno : this.getAlumnos()) {
            if (alumno.getNumero().equals(numero)) alumnos.add(alumno);
        }
        return alumnos;
    }
    
    public List<Alumno> encontrarAlumnosPorNombre(String nombre) {
        List<Alumno> alumnos = new ArrayList<>();
        for (var alumno : this.getAlumnos()) {
            if (alumno.nombre.equals(nombre)) alumnos.add(alumno);
        }
        return alumnos;
    }
}
