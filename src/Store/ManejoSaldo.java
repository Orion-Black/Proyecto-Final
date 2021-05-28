package Store;

import java.io.*;
import java.util.Scanner;

public class ManejoSaldo {
    static File archivo = new File("saldo.txt");

    public static void establecerSaldo() throws FileNotFoundException {
        //Abrir archivo
        Scanner entradaArchivo = new Scanner(archivo);
        //Leer archivo
        while (entradaArchivo.hasNext()) {
            Globales.saldo = entradaArchivo.nextDouble();
        }
    }

    private static void escribirArchivo() {
        try {
            PrintWriter salidaArchivo = new PrintWriter(archivo);
            salidaArchivo.println(Globales.saldo);
            salidaArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

