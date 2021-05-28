package Store;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Archivo {
    private String nameOfFile;

    public Archivo(String nameA){
        this.nameOfFile=nameA;
    }
    //Escribir en el archivo
    public void writeInFile(ArrayList<Cancion> listSongs){
        try {
            PrintWriter outFile = new PrintWriter("canciones.txt");
            for (Cancion songx:listSongs) {
                outFile.println( songx.formatoArchivo());
            }
            outFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }//fin escribir

    public ArrayList<Cancion> readFile(){
        ArrayList<Cancion> listSongs = new ArrayList<>();
        try {
            //Open the file
            File fileRead = new File(nameOfFile);
            Scanner readFile = new Scanner(fileRead);
            //Read the file
            while (readFile.hasNext()){
                String line = readFile.nextLine();
                String[] values = line.split(",");
                int key = Integer.parseInt(values[0]);
                String nameSong =values[1];
                String artist = values[2];
                String gender = values[3];
                String coverPage = values[4];
                String album = values[5];
                String file = values[6];
                double price= Double.parseDouble(values[7]);
                Cancion songx = new Cancion(key, nameSong, artist, gender,
                        coverPage, album, file, price);
                listSongs.add(songx);
            }
            //close the file
            readFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return listSongs;
    }
}
