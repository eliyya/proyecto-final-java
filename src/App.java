import java.math.BigInteger;
import java.util.Scanner;

public class App {
    public Alumno[] alumnos = new Alumno[0];
    public Scanner scanner = new Scanner(System.in);
    public String[] carreras = new String[0]; 
    public static void main(String[] args) {
        new App();
    }

    public App() {
        if (this.alumnos.length == 0) {
            System.out.println("No existen registros de alumnos");
            System.out.println("Favor de registrar al menos uno");
            agregarAlumno();
        }
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
        int tn = 0;
        String carrera = "";
        while (true) {
            System.out.println("Carreras disponibles");
            for (var carrerat : carreras) {
                System.out.println((++tn) + ".- " + carrerat);
            }
            System.out.println("Seleccione una opcion: ");
            int opcion = 0;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion invalida");
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println("Opcion invalida");
                continue;
            }
            carrera = this.carreras[opcion - 1];
            break;
        }

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
        System.out.println("Ingresa el grupo: ");
        String grupo = scanner.next();
        double promedio = 0;
        for (int i = 0; i < this.alumnos.length; i++) {
            if (this.alumnos[i].getGroup().equals(grupo)) {
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
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {}

        if (opcion < 1 || opcion > 6) {
            System.out.println("Opcion no valida");
            opcion = pedirOpcion();
        }

        return opcion;
    }

    public void agregarAlumno() {
        // Pedir nombre
        System.out.println("Ingresa el nombre: ");
        var nombre = scanner.nextLine();
        Alumno[] matchs = new Alumno[0];
        for (var alumno : this.alumnos) 
            if (alumno.name.toLowerCase().equals(nombre.toLowerCase())) {
                Alumno[] newMatch = new Alumno[matchs.length+1];
                newMatch[matchs.length] = alumno;
            }
        if (matchs.length > 0) {
            System.out.println("Alumnos encontrados");
            for (int i = 0; i < matchs.length; i++) {
                System.out.println((i+1) + ".- (" + matchs[i].getNumero() + ") " + matchs[i].name);
            }
            System.out.println("Desea continuar? (S/n)");
            String respuesta = scanner.nextLine();
            if (respuesta.toLowerCase().equals("n")) return;
        }

        // Pedir numero de control
        BigInteger nn = new BigInteger("0");
        for (Alumno alumno : this.alumnos) {
            var n = new BigInteger(alumno.getNumero());
            if (n.compareTo(nn) > 0) nn = n;
        }
        nn = nn.add(new BigInteger("1"));

        String numero;
        while (true) {
            System.out.print("Ingrese el numero de control: (" + nn + ") ");
            numero = scanner.nextLine().trim();
            if (numero.equals("")) numero = nn.toString();

            Alumno match = null;
            for (var alumno : this.alumnos) 
                if (alumno.getNumero().equals(numero)) {
                    match = alumno;
                    break;
                }

            if (match != null) {
                System.out.println("Alumno existente");
                System.out.println("Cambiar numero de control? (s/N)");
                String respuesta = scanner.nextLine();
                if (respuesta.toLowerCase().equals("s")) continue;
            }
            break;
        }

        // Pedir carrera
        int tn = 0;
        System.out.println("Ahora ingresemos las carreras");
        String ncarrera;
        while (true) {
            if (this.carreras.length > 0) {
                System.out.println("Carreras disponibles");
                for (var carrera : carreras) {
                    System.out.println((++tn) + ".- " + carrera);
                }
            }
            System.out.println((++tn) + ".- Nueva carrera");
            System.out.println("Seleccione una opcion: ");
            int opcion = 0;

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Opcion invalida");
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println("Opcion invalida");
                continue;
            }

            if (opcion == tn) {
                System.out.print("Ingrese el nombre de la carrera: ");
                String carrera = scanner.nextLine();
                String[] carreras = new String[this.carreras.length + 1];
                for (int i = 0; i < this.carreras.length; i++) carreras[i] = this.carreras[i];
                carreras[carreras.length - 1] = carrera;
                this.carreras = carreras;
                ncarrera = carrera;
                break;
            } else {
                ncarrera = this.carreras[opcion - 1];
                break;
            }
        }

        // Pedir grupo
        System.out.println("Ingresa el grupo: ");
        String grupo = scanner.nextLine();

        Alumno alumno = new Alumno(numero, nombre, grupo, ncarrera);
        Alumno[] alumnos = new Alumno[this.alumnos.length + 1];
        for (int i = 0; i < this.alumnos.length; i++) {
            alumnos[i] = this.alumnos[i];
        }
        alumnos[alumnos.length - 1] = alumno;
        this.alumnos = alumnos;
    }

    public void mostrarDetalleGeneral() {
        for (int i = 0; i < this.alumnos.length; i++) {
            System.out.println(this.alumnos[i]);
        }
    }

}
