package WEB;

import javax.swing.*;

public class CalculadorasMercadoLibre {
    public static void main(String[] args) {

        int valorSombrero;
        double cargoXsombrero;
        double resultado;
        double cargoXenvio;


        valorSombrero= Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el valor del sombrero"));
        cargoXsombrero = Double.parseDouble(JOptionPane.showInputDialog(null, "¿Cuanto es el cargo por sombrero?"));
        cargoXenvio = Double.parseDouble(JOptionPane.showInputDialog(null, "¿Cuanto es el cargo por envio?"));
        double IVA = (float)6.896875*valorSombrero/100;
        double ISR = (float)0.8625*valorSombrero/100;
        resultado= (valorSombrero-cargoXsombrero-cargoXenvio-IVA-ISR);

        System.out.println("El valor total de la venta es: "+valorSombrero);
        System.out.println("El cargo por publicacion es de: "+cargoXsombrero );
        System.out.println("Los cargos por envio son: "+cargoXenvio);
        System.out.println("El IVA y el ISR son "+IVA+"/"+ISR+" respectivamente");

        System.out.println("El dinero restante es igual a : "+ resultado);


    }
}
