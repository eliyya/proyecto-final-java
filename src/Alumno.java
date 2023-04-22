public class Alumno {
    private String numero;
    public String name;
    private String group;
    private String carrera;
    public double calif_1 = 0;
    public double calif_2 = 0;
    public double calif_3 = 0;

    public Alumno(String numero, String name) {
        this.numero = numero;
        this.name = name;
    }

    public Alumno(String numero, String name, String carrera, String group) {
        this.numero = numero;
        this.name = name;
        this.carrera = carrera;
        this.group = group;
    }

    public String getNumero() {
        return numero;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGroup() {
        return group;
    }

    public String getCarrera() {
        return carrera;
    }


    public double getPromedio() {
        return (calif_1 + calif_2 + calif_3) / 3;
    }

    public String toString() {
        return "Alumno: " + this.name +
                "\tNumero: " + this.numero +
                "\tCarrera: " + this.getCarrera() +
                "\tGrupo: " + this.getGroup() +
                "\tCalificacion 1: " + this.calif_1 +
                "\tCalificacion 2: " + this.calif_2 +
                "\tCalificacion 3: " + this.calif_3 +
                "\tPromedio: " + this.getPromedio();
    }
}
