public class Alumno {
    private String numero;
    public String nombre;
    private Grupo grupo;
    private Carrera carrera;
    public double calif_1 = 0;
    public double calif_2 = 0;
    public double calif_3 = 0;

    public Alumno(String numero, String name) {
        this.numero = numero;
        this.nombre = name;
    }

    public Alumno(String numero, String name, Carrera carrera) {
        this.numero = numero;
        this.nombre = name;
        this.carrera = carrera;
    }

    public String getNumero() {
        return numero;
    }

    public void setGrupo(Grupo group) {
        this.grupo = group;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Carrera getCarrera() {
        return carrera;
    }


    public double getPromedio() {
        return (calif_1 + calif_2 + calif_3) / 3;
    }

    public String toString() {
        return "Alumno: " + this.nombre +
                "\n\tNumero: " + this.numero +
                "\n\tCarrera: " + this.getCarrera() +
                "\n\tGrupo: " + this.getGrupo() +
                "\n\tCalificacion 1: " + this.calif_1 +
                "\n\tCalificacion 2: " + this.calif_2 +
                "\n\tCalificacion 3: " + this.calif_3 +
                "\n\tPromedio: " + this.getPromedio();
    }

    public String toCSV() {
        return this.numero + "," + this.nombre + "," + this.carrera + "," + this.grupo.getNombre() + "," + this.calif_1 + "," + this.calif_2 + "," + this.calif_3;
    }
}
