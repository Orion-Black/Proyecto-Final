package Store;

import java.util.ArrayList;
import java.util.Iterator;

public class Catalogue {
    private ArrayList<Cancion> listOfSongs;

    public Catalogue() {
        listOfSongs = new ArrayList<>();
    }

    public void setListOfSongs(ArrayList<Cancion> list) {
        this.listOfSongs = list;
    }

    public ArrayList<Cancion> getListOfSongs() {
        return listOfSongs;
    }

    public void insertSong(Cancion songx) {
        listOfSongs.add(songx);
    }

    public void seeCatalogue() {
        System.out.printf("%-8s|%-30s|%-30s|%-8s\n",
                "Clave", "Nombre Cancion", "Artista", "Precio");
        System.out.println("--------|------------------------------|" +
                "------------------------------|--------");
        for (Cancion aux : this.getListOfSongs()) {
            System.out.printf("%-8d|%-30s|%-30s|%-6.2f\n",
                    aux.getKeyS(), aux.getNameSong(), aux.getNameAuthor(), aux.getPrice());
        }
    }

    public int searchSongForKey(int keySearch) {
        for (int i = 0; i < listOfSongs.size(); i++) {
            if (listOfSongs.get(i).getKeyS() == keySearch) {
                return i;
            }
        }
        return -1;
    }

    public void renameSong(int index, String newName) {
        Cancion songAux = listOfSongs.get(index);
        songAux.setNameSong(newName);
    }

    public void deleteSong(int key) {
        Iterator<Cancion> cancionIterator = listOfSongs.iterator();
        while (cancionIterator.hasNext()) {
            Cancion songx = cancionIterator.next();
            if (key == songx.getKeyS()) {
                cancionIterator.remove();
            }
        }
    }

    public void seeSong(int index) {
        Cancion aux = listOfSongs.get(index);
        System.out.printf("%-8s|%-30s|%-30s|%-8s\n",
                "Clave", "Nombre Cancion", "Artista", "Precio");
        System.out.println("--------|------------------------------|" +
                "------------------------------|--------");
        System.out.printf("%-8d|%-30s|%-30s|%-6.2f\n",
                aux.getKeyS(), aux.getNameSong(),
                aux.getNameAuthor(), aux.getPrice());
    }
}
