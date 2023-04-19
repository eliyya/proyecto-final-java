public class Alumno {
    public String numero;
    public String nombre;
    private String grupo;
    private String carrera;
    public double calif_1 = 0;
    public double calif_2 = 0;
    public double calif_3 = 0;

    public Alumno(String numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public Alumno(String numero, String nombre, String grupo, String carrera) {
        this.numero = numero;
        this.nombre = nombre;
        this.grupo = grupo;
        this.carrera = carrera;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getCarrera() {
        return carrera;
    }


    public double getPromedio() {
        return (calif_1 + calif_2 + calif_3) / 3;
    }

    public String toString() {
        return "Alumno: " + this.nombre +
                "\tNumero: " + this.numero +
                "\tCarrera: " + this.getCarrera() +
                "\tGrupo: " + this.getGrupo() +
                "\tCalificacion 1: " + this.calif_1 +
                "\tCalificacion 2: " + this.calif_2 +
                "\tCalificacion 3: " + this.calif_3 +
                "\tPromedio: " + this.getPromedio();
    }
}
