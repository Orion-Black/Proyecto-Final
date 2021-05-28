package Store;

import java.io.IOError;
import java.util.Scanner;

public class trialSong {
    static Catalogue catalogueStore = new Catalogue();
    static Scanner in = new Scanner(System.in);
    static Archivo filex = new Archivo("canciones.txt");

    public static void main(String[] args) {
        catalogueStore.setListOfSongs(filex.readFile());
        showSongs();
        int option;
        do {
            menu();
            option=in.nextInt();
            in.nextLine();
            switch (option){
                case 1:
                    requestDataSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                    break;
                case 2:
                    showSongs();
                    break;
                case 3:
                    searchSong();
                    break;
                case 4:
                    deleteSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                    break;
                case 5:
                    renameSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                    break;
                case 6:
                    System.out.println("Hasta pronto...");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }while (option != 6);
    }

    public static void menu(){
        System.out.println("Operaciones sobre canciones");
        System.out.println("1.- Añadir cancion a la lista");
        System.out.println("2.- Mostrar lista de canciones");
        System.out.println("3.- Buscar una cancion X clave");
        System.out.println("4.- Borrar una canción");
        System.out.println("5.- Cambiar el nombre de una canción");
        System.out.println("6.- Salir");
        System.out.print("--> ");
    }
    public static void requestDataSong(){
        int keyx, index;
        System.out.print("Ingresa la clave de la canción: ");
        keyx = in.nextInt();
        index= catalogueStore.searchSongForKey(keyx);
        if (index != -1){
            System.out.println("La clave de esta cancion ya existe.\n" +
                    "Reintentalo con otra clave");
            return;
        }
        in.nextLine();
        String nameSongx, artistx, genderx, coverx, albumx, filex;
        double pricex;

        System.out.println("Nombre:");
        nameSongx= in.nextLine();
        System.out.println("Artista:");
        artistx= in.nextLine();
        System.out.println("Genero:");
        genderx= in.nextLine();
        System.out.println("Ruta de la portada:");
        coverx= in.nextLine();
        System.out.println("Album:");
        albumx=in.nextLine();
        System.out.println("Ruta de la canción");
        filex= in.nextLine();
        System.out.println("Precio:");
        pricex= in.nextDouble();

        Cancion songX = new Cancion(keyx, nameSongx, artistx, genderx,
                coverx, albumx, filex, pricex);
        catalogueStore.insertSong(songX);
        System.out.println("Cancion registrada");
    }

    private static void showSongs(){
        System.out.printf("%-8s|%-30s|%-30s|%-8s\n",
                "Clave", "Nombre Cancion", "Artista", "Precio");
        System.out.println("--------|------------------------------|" +
                "------------------------------|--------");
        try {
            for (Cancion aux: catalogueStore.getListOfSongs()) {
                System.out.printf("%-8d|%-30s|%-30s|%-6.2f\n",
                        aux.getKeyS(), aux.getNameSong(), aux.getNameAuthor(), aux.getPrice());
            }
        }catch (IOError e){
            System.out.println(eVacio());
        }

    }
    private static void renameSong() {
        int key, index;
        System.out.println("Cambiar Nombre");
        System.out.println("Dame la clave de la canción:");
        System.out.print("--->");key=in.nextInt();
        index=catalogueStore.searchSongForKey(key);
        if (index == -1){
            System.out.println("La clave no existe, reintentalo");
        }
        else{
            in.nextLine();
            System.out.println("Datos de la canción");
            catalogueStore.seeSong(index);
            System.out.println();
            System.out.println("Dame el nuevo nombre de la canción: ");
            String nameSong = in.nextLine();
            catalogueStore.renameSong(index, nameSong);
            filex.writeInFile(catalogueStore.getListOfSongs());
            System.out.println("Cambios guardados");
            System.out.println();

        }
    }
    private static void deleteSong(){
        int key, index;
        System.out.println("Dame la clave de la canción a borrar: ");
        key=in.nextInt();
        index= catalogueStore.searchSongForKey(key);
        if (index==-1){
            System.out.println("La clave no existe, reintentalo");
        }
        else {
            catalogueStore.deleteSong(key);
            System.out.println("La cancion y sus datos han sido eliminados");
        }
    }
    private static void searchSong(){
        int key,index;
        System.out.println("Dame la clave de la canción a buscar: ");
        key=in.nextInt();
        index= catalogueStore.searchSongForKey(key);
        if (index==-1){
            System.out.println("La clave no existe, reintentalo");
        }
        else{
            catalogueStore.seeSong(index);
        }

    }
    public static String eVacio(){
        return "Registro vacio";
    }

}