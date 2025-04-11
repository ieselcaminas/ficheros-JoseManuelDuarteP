import java.io.File;
import java.util.Scanner;

public class Ejercicio1 {
    static File f1 = new File("/");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        boolean ban1 = true;

        do {
            System.out.println("\n");
            System.out.println("Ficheros y Directorios en: " + f1.getAbsolutePath());
            System.out.println("-----------------------------------------");
            System.out.println("0.- Directorio padre");

            for(int i = 1 ; i < f1.listFiles().length ; i++) {
                System.out.print(i + ".- ");
                imprimirFicheros(f1.listFiles()[i]);
            }

            do {
                System.out.println("Introduce una opción (-1 para salir):");
                opcion = sc.nextInt();

                if(opcion == -1) {
                    break;
                } else if (opcion == 0) {
                    if (f1.getParent() != null)
                        f1 = new File(f1.getParent());
                    break;
                } else if (f1.listFiles()[opcion].isDirectory()) {
                    ban1 = false;
                    f1 = new File(f1.listFiles()[opcion].getAbsolutePath());
                } else if (f1.listFiles()[opcion].isFile()) {
                    ban1 = true;
                }

            } while (ban1);



        } while (opcion != -1);
    }

    private static void imprimirFicheros(File fichero) {
        if (!fichero.isHidden()) {
            if (fichero.isFile()) {
                System.out.println(fichero.getName() + " " + fichero.length() + " bytes");
            } else if (fichero.isDirectory()) {
                System.out.println(fichero.getName() + " <Directorio>");
            }
        }
    }
}
