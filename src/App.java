import java.util.Scanner;

public class App {
    public Alumno[] alumnos = new Alumno[0];
    public Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        new App();
    }

    public App() {
        while (true) {
            int opcion = pedirOpcion();
            if (opcion == 6) {
                System.out.println("Saliendo...");
                System.exit(0);
            }
            if (opcion == 1) {
                mostrarPromedioGeneral();
            } else if (opcion == 2) {
                mostrarDetalleGeneral();
            } else if (opcion == 3) {
                mostrarPromedioPorCarrera();
            } else if (opcion == 4) {
                mostrarPromedioPorGrupo();
            } else if (opcion == 5) {
                agregarAlumno();
            }
        }
    }

    public void mostrarPromedioGeneral() {
        double promedio = 0;
        for (int i = 0; i < alumnos.length; i++) {
            promedio += alumnos[i].getPromedio();
        }
        promedio = promedio / alumnos.length;
        System.out.println("Promedio general: " + promedio);
    }
    
    public void mostrarPromedioPorCarrera() {
        String carrera = pedirCarrera();
        double promedio = 0;
        for (int i = 0; i < this.alumnos.length; i++) {
            if (this.alumnos[i].getCarrera().equals(carrera)) {
                promedio += this.alumnos[i].getPromedio();
            }
        }
        promedio = promedio / this.alumnos.length;
        System.out.println("Promedio de " + carrera + ": " + promedio);
    }

    public void mostrarPromedioPorGrupo() {
        String grupo = pedirGrupo();
        double promedio = 0;
        for (int i = 0; i < this.alumnos.length; i++) {
            if (this.alumnos[i].getGrupo().equals(grupo)) {
                promedio += this.alumnos[i].getPromedio();
            }
        }
        promedio = promedio / this.alumnos.length;
        System.out.println("Promedio de " + grupo + ": " + promedio);
    }

    public int pedirOpcion() {
        System.out.println("1.- Mostrar promedio de alumnos (general)");
        System.out.println("2.- Mostrar detalle de alumnos (general))");
        System.out.println("3.- Mostrar promedio de alumnos (por carrera)");
        System.out.println("4.- Mostrar promedio de alumnos (por grupo)");
        System.out.println("5.- Agregar alumno");
        System.out.println("6.- Salir");

        int opcion = 0;
        try {
            opcion = scanner.nextInt();
        } catch (Exception e) {}

        if (opcion < 1 || opcion > 6) {
            System.out.println("Opcion no valida");
            opcion = pedirOpcion();
        }

        return opcion;
    }

    public void agregarAlumno() {
        String nombre = pedirNombre();
        String numero = pedirNumero();
        String carrera = pedirCarrera();
        String grupo = pedirGrupo();

        Alumno alumno = new Alumno(numero, nombre, grupo, carrera);
        Alumno[] alumnos = new Alumno[this.alumnos.length + 1];
        for (int i = 0; i < this.alumnos.length; i++) {
            alumnos[i] = this.alumnos[i];
        }
        alumnos[alumnos.length - 1] = alumno;
        this.alumnos = alumnos;
    }

    public String pedirNombre() {
        System.out.println("Ingresa el nombre: ");
        String nombre = scanner.next();
        return nombre;
    }

    public String pedirNumero() {
        System.out.println("Ingresa el numero: ");
        String numero = scanner.next();
        return numero;
    }

    public String pedirCarrera() {
        System.out.println("Ingresa la carrera: ");
        String carrera = scanner.next();
        return carrera;
    }

    public String pedirGrupo() {
        System.out.println("Ingresa el grupo: ");
        String grupo = scanner.next();
        return grupo;
    }

    public void mostrarDetalleGeneral() {
        for (int i = 0; i < this.alumnos.length; i++) {
            System.out.println(this.alumnos[i]);
        }
    }

}
