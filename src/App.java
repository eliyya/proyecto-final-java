import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Scanner;

public class App {
    public Alumno[] alumnos = new Alumno[0];
    public Scanner scanner = new Scanner(System.in);
    public String[] carreras = new String[0]; 
    public static void main(String[] args) {
        clearConsole();
        new App();
    }

    public App() {
        try {
            String linea;
            BufferedReader csv = new BufferedReader(new FileReader("db.csv"));
            while ((linea = csv.readLine()) != null) {
                String[] valores = linea.split(",");

                boolean existe = false;
                for (int i = 0; i < this.carreras.length; i++) {
                    if (this.carreras[i].equals(valores[2])) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    String[] nuevasCarreras = new String[this.carreras.length + 1];
                    for (int i = 0; i < this.carreras.length; i++) nuevasCarreras[i] = this.carreras[i];
                    nuevasCarreras[this.carreras.length] = valores[2];
                    this.carreras = nuevasCarreras;
                }

                Alumno alumno = new Alumno(valores[0], valores[1], valores[2], valores[3]);
                alumno.calif_1 = Double.parseDouble(valores[4]);
                alumno.calif_2 = Double.parseDouble(valores[5]);
                alumno.calif_3 = Double.parseDouble(valores[6]);
                Alumno[] nuevosAlumnos = new Alumno[this.alumnos.length + 1];
                for (int i = 0; i < this.alumnos.length; i++) {
                    nuevosAlumnos[i] = this.alumnos[i];
                }
                nuevosAlumnos[alumnos.length] = alumno;
                this.alumnos = nuevosAlumnos;                
            }
            csv.close();
        } catch (Exception e) {}
        

        if (this.alumnos.length == 0) {
            System.out.println(Colors.ANSI_RED+"No existen registros de alumnos");
            System.out.println(Colors.ANSI_RESET+"Favor de registrar al menos uno");
            agregarAlumno();
        }
        while (true) {
            int opcion = pedirOpcion();
            if (opcion == 6) {
                System.out.println("Saliendo...");
                System.exit(0);
            }
            if (opcion == 1) {
                this.mostrarPromedioGeneral();
            } else if (opcion == 2) {
                this.mostrarDetalleGeneral();
            } else if (opcion == 3) {
                this.mostrarPromedioPorCarrera();
            } else if (opcion == 4) {
                this.mostrarPromedioPorGrupo();
            } else if (opcion == 5) {
                this.agregarAlumno();
            }
        }
    }

    public void mostrarPromedioGeneral() {
        double promedio = 0;
        var w = getTerminalCharactersWidth();
        String t = "";
        System.out.println("#".repeat(w));
        for (int i = 0; i < this.alumnos.length; i++) {
            System.out.println((t = "# Alumno: " + Colors.ANSI_CYAN + this.alumnos[i].name) + Colors.ANSI_RESET + " ".repeat(w - t.length() +Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = ("#     - Grupo: " + Colors.ANSI_CYAN + alumnos[i].getCarrera() + this.alumnos[i].getGroup())) + Colors.ANSI_RESET  + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = "#     - Promedio: " + Colors.ANSI_CYAN + this.alumnos[i].getPromedio()) + Colors.ANSI_RESET  + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            promedio += this.alumnos[i].getPromedio();
        }
        promedio = promedio / this.alumnos.length;
        System.out.println((t = "# Promedio general: " + Colors.ANSI_BLUE + promedio) + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_BLUE.length() - 1) + "#");
        System.out.println("#".repeat(w));
        System.out.print("Presione enter para continuar...");
        scanner.nextLine();
    }
    
    public void mostrarPromedioPorCarrera() {
        int tn = 0;
        String carrera = "";
        while (true) {
            System.out.println("Carreras disponibles");
            for (var carrerat : this.carreras) {
                System.out.println((++tn) + ".- " + carrerat);
            }
            System.out.println("Seleccione una opcion: ");
            int opcion = 0;
            try {
            System.out.print("> ");
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                continue;
            }
            carrera = this.carreras[opcion - 1];
            break;
        }

        double promedio = 0;
        for (int i = 0; i < this.alumnos.length; i++) {
            if (this.alumnos[i].getCarrera().equals(carrera)) {
                System.out.println("Alumno: " + this.alumnos[i].name + " - Grupo: " + this.alumnos[i].getGroup()+alumnos[i].getCarrera() + " - Promedio: " + this.alumnos[i].getPromedio());
                promedio += this.alumnos[i].getPromedio();
            }
        }
        promedio = promedio / this.alumnos.length;
        System.out.println("Promedio de " + carrera + ": " + promedio);
    }

    public void mostrarPromedioPorGrupo() {

        int tn = 0;
        String carrera = "";
        while (true) {
            System.out.println("Carreras disponibles");
            for (var carrerat : this.carreras) {
                System.out.println((++tn) + ".- " + carrerat);
            }
            System.out.println("Seleccione una opcion: ");
            int opcion = 0;
            try {
            System.out.print("> ");
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                continue;
            }
            carrera = this.carreras[opcion - 1];
            break;
        }

        System.out.print("Ingresa el grupo: ");
        String grupo = scanner.nextLine();
        double promedio = 0;
        for (int i = 0; i < this.alumnos.length; i++) {
            if (this.alumnos[i].getGroup().equals(grupo) && this.alumnos[i].getCarrera().equals(carrera)) {
                System.out.println("Alumno: " + this.alumnos[i].name + " - Grupo: " + this.alumnos[i].getGroup()+alumnos[i].getCarrera() + " - Promedio: " + this.alumnos[i].getPromedio());
                promedio += this.alumnos[i].getPromedio();
            }
        }
        promedio = promedio / this.alumnos.length;
        System.out.println("Promedio de " + carrera+grupo + ": " + promedio);
    }

    public int pedirOpcion() {
        clearConsole();
        System.out.println("1.- Mostrar promedio de alumnos (general)");
        System.out.println("2.- Mostrar detalle de alumnos (general))");
        System.out.println("3.- Mostrar promedio de alumnos (por carrera)");
        System.out.println("4.- Mostrar promedio de alumnos (por grupo)");
        System.out.println("5.- Agregar alumno");
        System.out.println("6.- Salir");

        int opcion = 0;
        try {
            System.out.print("> ");
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {}

        if (opcion < 1 || opcion > 6) {
            clearConsole();
            System.out.println("Opcion no valida");
            opcion = pedirOpcion();
        }

        clearConsole();
        return opcion;
    }

    public void agregarAlumno() {
        // Pedir nombre
        System.out.print("Ingresa el nombre: ");
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
            System.out.print("Desea continuar? (S/n) ");
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
                System.out.print("Cambiar numero de control? (s/N)");
                String respuesta = scanner.nextLine();
                if (respuesta.toLowerCase().equals("s")) continue;
            }
            break;
        }

        // Pedir carrera
        System.out.println("Ahora ingresemos las carreras");
        String ncarrera;
        while (true) {
            int tn = 0;
            if (this.carreras.length > 0) {
                System.out.println("Carreras disponibles");
                for (var carrera : this.carreras) {
                    System.out.println((++tn) + ".- " + carrera);
                }
            }
            System.out.println((++tn) + ".- Nueva carrera");
            System.out.print("Seleccione una opcion: ");
            int opcion = 0;

            try { 
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
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

        // Pedir materias
        System.out.println("Ingrese la calificacion de la materia 1");
        alumno.calif_1 = (Double.parseDouble(scanner.nextLine()));
        System.out.println("Ingrese la calificacion de la materia 2");
        alumno.calif_2 = (Double.parseDouble(scanner.nextLine()));
        System.out.println("Ingrese la calificacion de la materia 3");
        alumno.calif_3 = (Double.parseDouble(scanner.nextLine()));

        Alumno[] alumnos = new Alumno[this.alumnos.length + 1];
        for (int i = 0; i < this.alumnos.length; i++) {
            this.alumnos[i] = this.alumnos[i];
        }
        this.alumnos = alumnos;
        this.alumnos[this.alumnos.length - 1] = alumno;
        updateDB();
    }

    public void mostrarDetalleGeneral() {
        for (var alumno : this.alumnos) System.out.println("Alumno: " + alumno.name + "\n\tGrupo: " + alumno.getCarrera()+alumno.getGroup() + "\n\tCalificacion 1: " + alumno.calif_1 + "\n\tCalificacion 2: " + alumno.calif_2 + "\n\tCalificacion 3: " + alumno.calif_3 + "\n\tPromedio: " + alumno.getPromedio());
    }

    private void updateDB() {
        var filedir = System.getProperty("os.name").contains("Linux") ? "~/.eli_maciel_program/db.csv" : "db.csv";
        try {
            BufferedWriter db = new BufferedWriter(new FileWriter(filedir));
            for (var alumno : this.alumnos) {
                db.write(alumno.toCSV());
                db.newLine();
            }
            db.close();
        } catch (Exception e) {
            System.out.println("Error al actualizar la base de datos");
        }
    }

    public static void clearConsole(){
        try {
            String[] comando;
            if(System.getProperty("os.name").contains("Windows")) comando = "cmd /C cls".split(" ");
            else comando = "clear".split(" ");            
            var process = new ProcessBuilder(comando).inheritIO().start();
            process.waitFor();
        } catch (Exception e) {}
    }

    public static int getTerminalCharactersWidth() {
        try {
            var colsProcess = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty").start();
            var colsReader = new BufferedReader(new InputStreamReader(colsProcess.getInputStream(), Charset.forName("utf-8")));
            return Integer.parseInt(colsReader.readLine());
        } catch (IOException e) {
            return 0;
        }

    }
}
