import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    // lista de alumnos
    public List<Alumno> alumnos = new ArrayList<>();
    public Scanner scanner = new Scanner(System.in);
    public List<String> carreras = new ArrayList<>(); 
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

                if (!this.carreras.contains(valores[2])) this.carreras.add(valores[2]);

                Alumno alumno = new Alumno(valores[0], valores[1], valores[2], valores[3]);
                alumno.calif_1 = Double.parseDouble(valores[4]);
                alumno.calif_2 = Double.parseDouble(valores[5]);
                alumno.calif_3 = Double.parseDouble(valores[6]);
                this.alumnos.add(alumno);          
            }
            csv.close();
        } catch (Exception e) {
            System.out.println("Error al leer la base de datos");
            e.printStackTrace();
            scanner.nextLine();
        }
        

        if (this.alumnos.size() == 0) {
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
        for (var alumno : this.alumnos) {
            System.out.println((t = "# Alumno: " + Colors.ANSI_CYAN + alumno.name) + Colors.ANSI_RESET + " ".repeat(w - t.length() +Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = ("#     - Grupo: " + Colors.ANSI_CYAN + alumno.getCarrera() + alumno.getGroup())) + Colors.ANSI_RESET  + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println((t = "#     - Promedio: " + Colors.ANSI_CYAN + alumno.getPromedio()) + Colors.ANSI_RESET  + " ".repeat(w - t.length() + Colors.ANSI_CYAN.length() - 1) + "#");
            System.out.println("#" + " ".repeat(w-1) + "#");
            promedio += alumno.getPromedio();
        }
        promedio = promedio / this.alumnos.size();
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
            carrera = this.carreras.get(opcion - 1);
            break;
        }

        double promedio = 0;
        int ca = 0;
        for (var alumno : this.alumnos) {
            if (alumno.getCarrera().equals(carrera)) {
                System.out.println("Alumno: " + alumno.name + " - Grupo: " + alumno.getCarrera() + alumno.getGroup() + " - Promedio: " + alumno.getPromedio());
                promedio += alumno.getPromedio();
                ca++;
            }
        }
        promedio = promedio / ca;
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
            carrera = this.carreras.get(opcion - 1);
            break;
        }

        System.out.print("Ingresa el grupo: ");
        String grupo = scanner.nextLine();
        double promedio = 0;
        int ca = 0;
        for (var alumno : this.alumnos) {
            if (alumno.getGroup().equals(grupo) && alumno.getCarrera().equals(carrera)) {
                System.out.println("Alumno: " + alumno.name + " - Grupo: " + alumno.getCarrera() + alumno.getGroup() + " - Promedio: " + alumno.getPromedio());
                promedio += alumno.getPromedio();
                ca++;
            }
        }
        promedio = promedio / ca;
        System.out.println("Promedio de " + carrera+grupo + ": " + promedio);
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
        var o = "";
        System.out.print("Ingresa el nombre: ");
        var nombre = scanner.nextLine();
        o += "Nombre: " + Colors.ANSI_CYAN + nombre + Colors.ANSI_RESET;
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
            clearConsole();
            System.out.print(o+"\nIngrese el numero de control: (" + nn + ") ");
            numero = scanner.nextLine().trim();
            if (numero.equals("")) numero = nn.toString();
            else {
                try {
                    new BigInteger(numero);
                } catch (Exception e) {
                System.out.println(Colors.ANSI_RED+"Alumno existente"+Colors.ANSI_RESET);
                    System.out.println("Presione enter para continuar...");
                    scanner.nextLine();
                    continue;
                }
            }

            Alumno match = null;
            for (var alumno : this.alumnos) 
                if (alumno.getNumero().equals(numero)) {
                    match = alumno;
                    break;
                }

            if (match != null) {
                System.out.println(Colors.ANSI_RED+"Alumno existente"+Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            break;
        }
        o += "\nNumero de control: " + Colors.ANSI_CYAN + numero + Colors.ANSI_RESET;

        // Pedir carrera
        String ncarrera;
        while (true) {
            clearConsole();
            System.out.println(o+"\nAhora ingresemos las carreras");
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
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }
            if (opcion < 1 || opcion > tn) {
                System.out.println(Colors.ANSI_RED+"Opcion invalida"+Colors.ANSI_RESET);
                System.out.println("Presione enter para continuar...");
                scanner.nextLine();
                continue;
            }

            if (opcion == tn) {
                String carrera = "";
                while (true) {
                    clearConsole();
                    System.out.print(o+"\nIngrese el nombre de la carrera: ");
                    carrera = scanner.nextLine();
                    if (this.carreras.contains(carrera)) {
                        System.out.println(Colors.ANSI_RED+"Carrera existente"+Colors.ANSI_RESET);
                        System.out.println("Presione enter para continuar...");
                        scanner.nextLine();
                        continue;
                    } else break;
                }
                this.carreras.add(carrera);
                ncarrera = carrera;
                break;
            } else {
                ncarrera = this.carreras.get(opcion - 1);
                break;
            }
        }
        o += "\nCarrera: " + Colors.ANSI_CYAN + ncarrera + Colors.ANSI_RESET;

        // Pedir grupo
        clearConsole();
        System.out.println(o+"\nIngresa el grupo: ");
        String grupo = scanner.nextLine();
        o += "\nGrupo: " + Colors.ANSI_CYAN + grupo + Colors.ANSI_RESET;

        Alumno alumno = new Alumno(numero, nombre, grupo, ncarrera);

        // Pedir materias
        clearConsole();
        System.out.println(o+"\nIngrese la calificacion de la materia 1");
        alumno.calif_1 = (Double.parseDouble(scanner.nextLine()));
        o += "\nCalificacion 1: " + Colors.ANSI_CYAN + alumno.calif_1 + Colors.ANSI_RESET;
        clearConsole();
        System.out.println(o+"\nIngrese la calificacion de la materia 2");
        alumno.calif_2 = (Double.parseDouble(scanner.nextLine()));
        o += "\nCalificacion 2: " + Colors.ANSI_CYAN + alumno.calif_2 + Colors.ANSI_RESET;
        clearConsole();
        System.out.println(o+"\nIngrese la calificacion de la materia 3");
        alumno.calif_3 = (Double.parseDouble(scanner.nextLine()));
        o += "\nCalificacion 3: " + Colors.ANSI_CYAN + alumno.calif_3 + Colors.ANSI_RESET;

        this.alumnos.add(alumno);
        
        clearConsole();
        System.out.println(o+"\nAlumno agregado");
        System.out.println("Presione enter para continuar...");
        scanner.nextLine();

        updateDB();
    }

    public void mostrarDetalleGeneral() {
        for (var alumno : this.alumnos) System.out.println("Alumno: " + alumno.name + "\n\tGrupo: " + alumno.getCarrera()+alumno.getGroup() + "\n\tCalificacion 1: " + alumno.calif_1 + "\n\tCalificacion 2: " + alumno.calif_2 + "\n\tCalificacion 3: " + alumno.calif_3 + "\n\tPromedio: " + alumno.getPromedio());
    }

    private void updateDB() {
        try {
            BufferedWriter db = new BufferedWriter(new FileWriter("db.csv"));
            for (var alumno : this.alumnos) {
                db.write(alumno.toCSV());
                db.newLine();
            }
            db.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
            scanner.nextLine();
        } catch (IOException e) {
            System.out.println("Error al actualizar la base de datos");
            e.printStackTrace();
            scanner.nextLine();
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
