import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ejercicio4 {
    public static void main(String[] args) {
        String filePath = "lista_alumnos.csv";
        String informePath = "informe-clase.txt";
        List<Estudiante> estudiantes = new ArrayList<>();
        double sumaEdades = 0;
        double sumaNotas = 0;
        double notaMax = Double.MIN_VALUE;
        double notaMin = Double.MAX_VALUE;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            boolean isFirstLine = true;
            while ((linea = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] partes = linea.split(",");
                if (partes.length < 5) {
                    System.err.println("Linea malformada: " + linea);
                    continue;
                }

                try {
                    String nombre = partes[0].trim();
                    String apellido = partes[1].trim();
                    String sexo = partes[2].trim();
                    int edad = Integer.parseInt(partes[3].trim());
                    double nota = Double.parseDouble(partes[4].trim());

                    estudiantes.add(new Estudiante(nombre, apellido, sexo, edad, nota));
                    sumaEdades += edad;
                    sumaNotas += nota;
                    if (nota > notaMax) notaMax = nota;
                    if (nota < notaMin) notaMin = nota;
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato en la linea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        estudiantes.forEach(System.out::println);

        int numeroEstudiantes = estudiantes.size();
        double edadMedia = numeroEstudiantes > 0 ? Math.round((double) sumaEdades / numeroEstudiantes) : 0;
        double notaMedia = numeroEstudiantes > 0 ? Math.round((sumaNotas / numeroEstudiantes) * 100.0) / 100.0 : 0;

        System.out.println("Esta clase tiene " + numeroEstudiantes + " estudiantes.");
        System.out.println("Edad media: " + (int) edadMedia);
        System.out.println("Nota media: " + String.format("%.2f", notaMedia));
        System.out.println("Nota maxima: " + String.format("%.2f", notaMax));
        System.out.println("Nota minima: " + String.format("%.2f", notaMin));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.newLine();
            bw.write(" ");
            bw.newLine();
            bw.write("Numero de estudiantes: " + numeroEstudiantes);
            bw.newLine();
            bw.write("Edad media: " + (int) edadMedia);
            bw.newLine();
            bw.write("Nota media: " + String.format("%.2f", notaMedia));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(informePath))) {
            bw.write("Numero de estudiantes: " + numeroEstudiantes);
            bw.newLine();
            bw.write("Edad media: " + (int) edadMedia);
            bw.newLine();
            bw.write("Nota media: " + String.format("%.2f", notaMedia));
            bw.newLine();
            bw.write("Nota maxima: " + String.format("%.2f", notaMax));
            bw.newLine();
            bw.write("Nota minima: " + String.format("%.2f", notaMin));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}

class Estudiante {
    String nombre;
    String apellido;
    String sexo;
    int edad;
    double nota;

    Estudiante(String nombre, String apellido, String sexo, int edad, double nota) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.edad = edad;
        this.nota = nota;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + ", " + sexo + ", " + edad + ", " + nota;
    }
}