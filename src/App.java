import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    public Scanner scanner = new Scanner(System.in);
    public List<Carrera> carreras = new ArrayList<>();

    public final String dbDir = System.getProperty("os.name").contains("Linux")
            ? System.getProperty("user.home") + File.separator + ".eli_maciel_programs"
            : System.getProperty("os.name").contains("Windows")
                    ? System.getenv("ProgramFiles") + File.separator + "eli_maciel_programs"
                    : "";
    public final String dbFile = dbDir + File.separator + "db.csv";

    public static void main(String[] args) {
        System.out.println();
        clearConsole();
        new App();
    }

    public App() {
        try {
            String linea;
            BufferedReader csv = new BufferedReader(new FileReader(dbFile));
            while ((linea = csv.readLine()) != null) {
                var valores = Arrays.asList(linea.split(",")).stream().map(String::trim).toArray(String[]::new);

                // check if alumno is not corrupted
                if (valores.length != 7 || Arrays.asList(valores).contains(null)
                        || Arrays.asList(valores).contains("")) {
                    continue;
                }

                Carrera carrera = Carrera.find(carreras, valores[2]);
                if (carrera == null)
                    this.carreras.add(carrera = new Carrera(valores[2]));

                Grupo grupo = Grupo.find(carrera.getGrupos(), valores[3]);
                if (grupo == null)
                    carrera.addGrupo(grupo = new Grupo(valores[3], carrera));

                Alumno alumno = new Alumno(valores[0], valores[1], carrera);
                alumno.calif_1 = Double.parseDouble(valores[4]);
                alumno.calif_2 = Double.parseDouble(valores[5]);
                alumno.calif_3 = Double.parseDouble(valores[6]);
                grupo.addAlumno(alumno);
            }
            csv.close();
        } catch (Exception e) {
        }

        if (this.carreras.size() == 0) {
            System.out.println(Colors.ANSI_RED + "No existen registros de alumnos");
            System.out.println(Colors.ANSI_RESET + "Favor de registrar al menos uno");
            agregarAlumno();
        }
        while (true) {
            int opcion = pedirOpcion();
            if (opcion == 6) {
                System.out.println("Saliendo...");
                updateDB();
                clearConsole();
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

    public int pedirOpcion() {
        clearConsole();
        System.out.println("1.- Mostrar promedio de alumnos (general)");
        System.out.println("2.- Mostrar detalle de alumnos (general)");
        System.out.println("3.- Mostrar promedio de alumnos (por carrera)");
        System.out.println("4.- Mostrar promedio de alumnos (por grupo)");
        System.out.println("5.- Agregar alumno");
        System.out.println("6.- Salir");

        int opcion = 0;
        try {
            System.out.print("> ");
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        if (opcion < 1 || opcion > 6) {
            clearConsole();
            System.out.println("Opcion no valida");
            opcion = pedirOpcion();
        }

        clearConsole();
        return opcion;
    }

    public void mostrarPromedioGeneral() {
        double promedio = 0;
        var w = getTerminalCharactersWidth();
        String t = "";
        System.out.println("#".repeat(w));
        for (var carrera : this.carreras) {
            for (var alumno : carrera.getAlumnos()) {
                System.out.println((t = "# Alumno: " + Colors.ANSI_CYAN + alumno.nombre) + Colors.ANSI_RESET
                        + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = ("#     - Grupo: " + Colors.ANSI_CYAN + alumno.getGrupo())) + Colors.ANSI_RESET
                        + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = "#     - Promedio: " + Colors.ANSI_CYAN + alumno.getPromedio())
                        + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println("#" + " ".repeat(w - 2) + "#");
            }
            promedio += carrera.promedio();
        }

        promedio = promedio / this.carreras.size();
        System.out.println((t = "# Promedio general: " + Colors.ANSI_GREEN + promedio) + Colors.ANSI_RESET
                + " ".repeat(w - t.length() + Colors.ANSI_GREEN.length() - 1) + "#");
        System.out.println("#".repeat(w));
        System.out.print("Presione enter para continuar...");
        scanner.nextLine();
    }

    public void mostrarDetalleGeneral() {
        clearConsole();
        int w = getTerminalCharactersWidth();
        System.out.println("#".repeat(w));
        System.out.println("#" + " ".repeat(w - 2) + "#");
        String t = "";
        for (var carrera : this.carreras)
            for (var alumno : carrera.getAlumnos()) {
                System.out.println((t = "# Alumno: " + Colors.ANSI_CYAN + alumno.nombre) + Colors.ANSI_RESET
                        + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = "#     Grupo: " + Colors.ANSI_CYAN + alumno.getGrupo()) + Colors.ANSI_RESET
                        + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = "#     Calificacion 1: " + Colors.ANSI_CYAN + alumno.calif_1)
                        + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = "#     Calificacion 2: " + Colors.ANSI_CYAN + alumno.calif_2)
                        + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = "#     Calificacion 3: " + Colors.ANSI_CYAN + alumno.calif_3)
                        + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println((t = "#     Promedio: " + Colors.ANSI_CYAN + alumno.getPromedio())
                        + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
                System.out.println("#" + " ".repeat(w - 2) + "#");
            }
        System.out.println("#".repeat(w));
        System.out.print("Presione enter para continuar...");
        scanner.nextLine();
    }

    public void mostrarPromedioPorCarrera() {
        int tn = 0;
        Carrera carrera;
        while (true) {
            clearConsole();
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
                System.out.println(Colors.ANSI_RED + "Opcion invalida" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED + "Opcion invalida" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            carrera = this.carreras.get(opcion - 1);
            break;
        }
        clearConsole();
        String t = "";
        var w = getTerminalCharactersWidth();
        System.out.println("#".repeat(w));
        for (var alumno : carrera.getAlumnos()) {
            System.out.println((t = "# Alumno: " + Colors.ANSI_CYAN + alumno.nombre) + Colors.ANSI_RESET
                    + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = ("#     - Grupo: " + Colors.ANSI_CYAN + alumno.getGrupo())) + Colors.ANSI_RESET
                    + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = "#     - Promedio: " + Colors.ANSI_CYAN + alumno.getPromedio()) + Colors.ANSI_RESET
                    + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println("#" + " ".repeat(w - 2) + "#");
        }
        System.out.println((t = "# Promedio de " + carrera + ": " + Colors.ANSI_GREEN + carrera.promedio())
                + Colors.ANSI_RESET + " ".repeat(w - t.length() - Colors.ANSI_CYAN.length() - 1) + "#");
        System.out.println("#".repeat(w));
        System.out.print("Presione enter para continuar...");
        scanner.nextLine();
    }

    public void mostrarPromedioPorGrupo() {

        int tn = 0;
        Carrera carrera;
        while (true) {
            clearConsole();
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
                System.out.println(Colors.ANSI_RED + "Opcion invalida" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED + "Opcion invalida" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            carrera = this.carreras.get(opcion - 1);
            break;
        }
        clearConsole();
        System.out.print("Ingresa el grupo: ");
        String sgrupo = scanner.nextLine();
        clearConsole();
        String t = "";
        var w = getTerminalCharactersWidth();
        System.out.println("#".repeat(w));
        Grupo grupo = Grupo.find(carrera.getGrupos(), sgrupo);
        for (var alumno : grupo.getAlumnos()) {
            System.out.println((t = "# Alumno: " + Colors.ANSI_CYAN + alumno.nombre) + Colors.ANSI_RESET
                    + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = ("#     - Grupo: " + Colors.ANSI_CYAN + alumno.getGrupo())) + Colors.ANSI_RESET
                    + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = "#     - Promedio: " + Colors.ANSI_CYAN + alumno.getPromedio()) + Colors.ANSI_RESET
                    + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println("#" + " ".repeat(w - 2) + "#");
        }
        if (grupo == null || grupo.getAlumnos().size() == 0) {
            System.out.println((t = "# No se encontraron alumnos de " + grupo) + " ".repeat(w - t.length() - 1) + "#");
            System.out.println("#" + " ".repeat(w - 2) + "#");
            System.out.println("Presione enter para continuar...");
            scanner.nextLine();
            return;
        }
        System.out.println((t = "Promedio de " + grupo + ": " + Colors.ANSI_GREEN + grupo.promedio())
                + Colors.ANSI_RESET + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
        System.out.println("#".repeat(w));
        System.out.println("Presione enter para continuar...");
        scanner.nextLine();
    }

    public void agregarAlumno() {
        // Pedir nombre
        var o = "";
        System.out.print("Ingresa el nombre: ");
        var nombre = scanner.nextLine();
        o += "Nombre: " + Colors.ANSI_CYAN + nombre + Colors.ANSI_RESET;
        List<Alumno> matchs = new ArrayList<>();
        for (var carrera : this.carreras)
            matchs.addAll(carrera.encontrarAlumnosPorNombre(nombre));
        if (matchs.size() > 0) {
            System.out.println("Alumnos encontrados");
            for (int i = 0; i < matchs.size(); i++)
                System.out.println((i + 1) + ".- (" + matchs.get(i).getNumero() + ") " + matchs.get(i).nombre);
            System.out.print("Desea continuar? (S/n) ");
            String respuesta = scanner.nextLine();
            if (respuesta.toLowerCase().equals("n"))
                return;
        }

        // Pedir numero de control
        BigInteger nn = new BigInteger("0");
        for (var carrera : this.carreras)
            for (Alumno alumno : carrera.getAlumnos()) {
                var n = new BigInteger(alumno.getNumero());
                if (n.compareTo(nn) > 0)
                    nn = n;
            }
        nn = nn.add(new BigInteger("1"));

        String numero;
        while (true) {
            clearConsole();
            System.out.print(o + "\nIngrese el numero de control: (" + nn + ") ");
            numero = scanner.nextLine().trim();
            if (numero.equals(""))
                numero = nn.toString();
            else {
                try {
                    new BigInteger(numero);
                } catch (Exception e) {
                    System.out.println(Colors.ANSI_RED + "Alumno existente" + Colors.ANSI_RESET);
                    System.out.println("Presione enter para continuar...");
                    scanner.nextLine();
                    continue;
                }
            }

            boolean ex = false;
            for (var carrera : this.carreras)
                for (Alumno alumno : carrera.getAlumnos()) {
                    var n = new BigInteger(alumno.getNumero());
                    if (n.compareTo(nn) == 0) {
                        ex = true;
                        break;
                    }
                }
            if (ex) {
                System.out.println(Colors.ANSI_RED + "Alumno existente" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            break;
        }
        o += "\nNumero de control: " + Colors.ANSI_CYAN + numero + Colors.ANSI_RESET;

        // Pedir carrera
        Carrera ncarrera;
        while (true) {
            clearConsole();
            System.out.println(o + "\nAhora ingresemos las carreras");
            int tn = 0;
            if (this.carreras.size() > 0) {
                System.out.println("Carreras disponibles");
                for (var carrera : this.carreras) {
                    System.out.println((++tn) + ".- " + carrera);
                }
            }
            System.out.println((++tn) + ".- Nueva carrera");
            System.out.print("> ");
            int opcion = 0;

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(Colors.ANSI_RED + "Opcion invalida" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED + "Opcion invalida" + Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }

            if (opcion == tn) {
                String carrera = "";
                while (true) {
                    clearConsole();
                    System.out.print(o + "\nIngrese el nombre de la carrera: ");
                    carrera = scanner.nextLine();
                    if (Carrera.contains(this.carreras, carrera)) {
                        System.out.println(Colors.ANSI_RED + "Carrera existente" + Colors.ANSI_RESET);
                        System.out.println("Presione enter para continuar...");
                        scanner.nextLine();
                        continue;
                    } else
                        break;
                }
                this.carreras.add(ncarrera = new Carrera(carrera));
                break;
            } else {
                ncarrera = this.carreras.get(opcion - 1);
                break;
            }
        }
        o += "\nCarrera: " + Colors.ANSI_CYAN + ncarrera + Colors.ANSI_RESET;

        // Pedir grupo
        clearConsole();
        System.out.println(o + "\nIngresa el grupo: ");
        String grupo = scanner.nextLine();
        o += "\nGrupo: " + Colors.ANSI_CYAN + grupo + Colors.ANSI_RESET;

        Alumno alumno = new Alumno(numero, nombre, ncarrera);
        Grupo newGrupo = Grupo.find(ncarrera.getGrupos(), grupo);
        if (newGrupo == null)
            ncarrera.addGrupo(newGrupo = new Grupo(grupo, ncarrera));
        newGrupo.addAlumno(alumno);

        // Pedir materias
        clearConsole();
        System.out.println(o + "\nIngrese la calificacion de la materia 1");
        alumno.calif_1 = (Double.parseDouble(scanner.nextLine()));
        o += "\nCalificacion 1: " + Colors.ANSI_CYAN + alumno.calif_1 + Colors.ANSI_RESET;
        clearConsole();
        System.out.println(o + "\nIngrese la calificacion de la materia 2");
        alumno.calif_2 = (Double.parseDouble(scanner.nextLine()));
        o += "\nCalificacion 2: " + Colors.ANSI_CYAN + alumno.calif_2 + Colors.ANSI_RESET;
        clearConsole();
        System.out.println(o + "\nIngrese la calificacion de la materia 3");
        alumno.calif_3 = (Double.parseDouble(scanner.nextLine()));
        o += "\nCalificacion 3: " + Colors.ANSI_CYAN + alumno.calif_3 + Colors.ANSI_RESET;

        clearConsole();
        System.out.println(o + "\n" + Colors.ANSI_GREEN + "Alumno agregado");
        System.out.println(Colors.ANSI_RESET + "Presione enter para continuar...");
        scanner.nextLine();
        updateDB();
    }

    private void updateDB() {
        try {
            var dir = new File(dbDir);
            dir.getParentFile().mkdirs(); // correct!
            dir.mkdirs();
            if (!dir.exists())
                dir.createNewFile();
            var db = new BufferedWriter(new FileWriter(dbFile));
            for (var carrera : this.carreras)
                for (var alumno : carrera.getAlumnos()) {
                    db.write(alumno.toCSV());
                    db.newLine();
                }
            db.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static void clearConsole() {
        try {
            String[] comando;
            if (System.getProperty("os.name").contains("Windows"))
                comando = "cmd /C cls".split(" ");
            else
                comando = "clear".split(" ");
            var process = new ProcessBuilder(comando).inheritIO().start();
            process.waitFor();
        } catch (Exception e) {
        }
    }

    public static int getTerminalCharactersWidth() {
        try {
            var colsProcess = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty").start();
            var colsReader = new BufferedReader(
                    new InputStreamReader(colsProcess.getInputStream(), Charset.forName("utf-8")));
            return Integer.parseInt(colsReader.readLine());
        } catch (IOException e) {
            return 0;
        }
    }
}
