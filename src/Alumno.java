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
                "\n\tNumero: " + this.numero +
                "\n\tCarrera: " + this.getCarrera() +
                "\n\tGrupo: " + this.getGroup() +
                "\n\tCalificacion 1: " + this.calif_1 +
                "\n\tCalificacion 2: " + this.calif_2 +
                "\n\tCalificacion 3: " + this.calif_3 +
                "\n\tPromedio: " + this.getPromedio();
    }

    public String toCSV() {
        return this.numero + "," + this.name + "," + this.group + "," + this.carrera + "," + this.calif_1 + "," + this.calif_2 + "," + this.calif_3;
    }
}
