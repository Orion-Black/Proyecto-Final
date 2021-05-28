package Store;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class operacionesCliente {
    //recuperar las canciones nuestro archivo de texto
    private Archivo filex = new Archivo("canciones.txt");
    private ArrayList<Cancion> listaCanciones;


    //Lista para guardar las canciones compradas
    private ArrayList<Cancion> listaCancionesCompradas = new ArrayList<>();

    public operacionesCliente(){
        listaCanciones= filex.readFile();
    }
    public int searchSong(int clavex){
        for (int i = 0; i < listaCanciones.size(); i++) {
            if (listaCanciones.get(i).getKeyS()== clavex) {
                return i;
            }
        }
        return -1;
    }
    public int searchPurchasedSong(int clavex){
        for (int i = 0; i < listaCancionesCompradas.size(); i++) {
            if (listaCancionesCompradas.get(i).getKeyS()==clavex){
                return i;
            }
        }
        return -1;
    }
    public void buySong(int index){
        Cancion songx = listaCanciones.get(index);
        listaCancionesCompradas.add(songx);
        writePurchasedSongs(listaCancionesCompradas);
    }
    public static void writePurchasedSongs(ArrayList<Cancion> listPurchased){
        try {
            PrintWriter outFile = new PrintWriter("cancionesCompradas.txt");
            for (Cancion songx:listPurchased) {
                outFile.println( songx.formatoArchivo());
            }
            outFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void seeBuySongs(){
        verCanciones(listaCancionesCompradas);
    }
    public void seeCatalogueSongs(){
        verCanciones(listaCanciones);
    }
    private void verCanciones(ArrayList<Cancion> lista){
        //Imprimimos lista de canciones
        System.out.printf("%-8s|%-30s|%-30s|%-8s\n",
                "Clave", "Nombre Cancion", "Artista", "Precio");
        System.out.println("--------|------------------------------|" +
                "------------------------------|--------");
        for (Cancion aux: lista) {
            System.out.printf("%-8d|%-30s|%-30s|%-6.2f\n",
                    aux.getKeyS(), aux.getNameSong(), aux.getNameAuthor(), aux.getPrice());
        }

    }

}
