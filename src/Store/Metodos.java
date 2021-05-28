package Store;

import java.io.*;
import java.util.Scanner;

public class Metodos {
    String nombreUsuario;
    String contrasena;
    double cantidad;
    double SenseCoins;
    int opcion;
    static Scanner in = new Scanner(System.in);
    static Catalogue catalogueStore = new Catalogue();
    static Archivo filex = new Archivo("canciones.txt");
    static openBrowser navegador = new openBrowser();
    static operacionesCliente op = new operacionesCliente();


    public void SenseSoundLogo() {
        System.out.println("_______________________________________");
        System.out.println("------------ Sense Sound --------------");
        System.out.println("                \uD83C\uDFA7");
    }

    public void InicioGeneral() throws IOException {

        do {
            System.out.println("Bienvenido inicia sesión o registrate.");
            System.out.println("1.- Iniciar sesión");
            System.out.println("2.- Registrarme");
            System.out.println("3.- Salir");
            System.out.print("--> ");
            try {
                opcion = in.nextInt();
                in.nextLine();
                switch (opcion) {
                    case 1 -> login();
                    case 2 -> {
                        signIn();
                        InicioGeneral();
                    }
                    case 3 -> System.exit(0);
                    default -> System.out.println("Opción invalida");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Ingreso una cadena de texto\nIngrese cualquier dato para continuar...");
                opcion = 0;
                in = new Scanner(System.in);
                System.out.print("--> ");
                in.nextLine();
            }

        } while (opcion != 3);
    }

    public void login() throws IOException {
        String name;
        String pass;
        System.out.println("Ingresa un nombre de usuario:");
        System.out.print("--> ");
        name = in.nextLine();
        System.out.println("Ingresa tu contraseña:");
        System.out.print("--> ");
        pass = in.nextLine();
        if (name.equals(getNombreUsuario()) && pass.equals(getContrasena())) {
            panelUsuarios();
        } else if (name.equals("DEV") && pass.equals("Admin")) {
            panelDev();
        } else {
            System.out.println("Ese usuario no existe o contraseña incorrecta");
        }

    }

    public void signIn() {
        String name;
        String pass;
        System.out.println("Ingresa un nombre de usuario:");
        System.out.print("--> ");
        name = in.nextLine();
        setNombreUsuario(name);
        do {
            System.out.println("Ingresa una contraseña (minimo 8 digitos):");
            System.out.print("--> ");
            pass = in.nextLine();
            if (pass.length() < 8) {
                System.out.println("Utilice al menos 8 digitos");
            } else if (pass.length() > 8) {
                System.out.println("Contraseña aceptada");
                setContrasena(pass);
            }
        } while (pass.length() < 8);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public void panelDev() throws IOException {
        int opcion;
        do {
            catalogueStore.setListOfSongs(filex.readFile());
            System.out.println("Aqui empieza la magia. Bienvenido...");
            System.out.println("-- Canciones --");
            System.out.println("1.- Subir canciones");
            System.out.println("2.- Bajar canciones");
            System.out.println("3.- Modificar canción");
            System.out.println("4.- Buscar cancion por clave");
            System.out.println("5.- Mostrar catalogo de canciones (Consola)");
            System.out.println("6.- Mostrar catalogo de canciones (WEB)");
            System.out.println("7.- Salir del Menu Desarrollador");
            System.out.print("--> ");
            opcion = in.nextInt();
            switch (opcion) {
                case 1 -> {
                    requestDataSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                }
                case 2 -> {
                    deleteSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                }
                case 3 -> {
                    renameSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                }
                case 4 -> searchSongByKey();
                case 5 -> showSongs();
                case 6 -> navegador.abrirNavegadorPorDefecto("Canciones.html");
                case 7 -> InicioGeneral();
                default -> System.out.println("Opción invalida");
            }
        } while (opcion != 7);
    }

    public void panelUsuarios() throws IOException {
        catalogueStore.setListOfSongs(filex.readFile());
        int opcion;
        do {
            SenseSoundLogo();
            System.out.println("Bienvenido " + getNombreUsuario());
            System.out.println("1.- Ver catalogo de canciones (web)");
            System.out.println("2.- Comprar cancion");
            System.out.println("3.- Escuchar canciones compradas");
            System.out.println("4.- SenseCoins");
            System.out.println("5.- Comprar SenseCoins");
            System.out.println("6.- Cerrar sesión");
            System.out.print("--> ");
            opcion = in.nextInt();
            switch (opcion) {
                case 1 -> {
                    crearWebTrial();
                    navegador.abrirNavegadorPorDefecto("CancionesTrial.html");
                    panelUsuarios();
                }
                case 2 -> comprarCancion();
                case 3 -> {
                    op.seeBuySongs();
                    webCancionesCompradas();
                    navegador.abrirNavegadorPorDefecto("miscancionesprueba.html");
                }
                case 4 -> senseCoins();
                case 5 -> comprarSenseCoins();
                case 6 -> InicioGeneral();
                default -> System.out.println("Opción invalida");
            }
        } while (opcion != 6);

    }

    public static void requestDataSong() {
        int keyx, index;
        System.out.print("Ingresa la clave de la canción: ");
        keyx = in.nextInt();
        index = catalogueStore.searchSongForKey(keyx);
        if (index != -1) {
            System.out.println("La clave de esta cancion ya existe.\n" +
                    "Reintentalo con otra clave");
            return;
        }
        String nameSongx, artistx, genderx, coverx, albumx, filex;
        double pricex;
        in.nextLine();
        System.out.println("Nombre:");
        nameSongx = in.nextLine();
        System.out.println("Artista:");
        artistx = in.nextLine();
        System.out.println("Genero:");
        genderx = in.nextLine();
        System.out.println("Ruta de la portada:");
        coverx = in.nextLine();
        System.out.println("Album:");
        albumx = in.nextLine();
        System.out.println("Ruta de la canción");
        filex = in.nextLine();
        System.out.println("Precio:");
        pricex = in.nextDouble();

        Cancion songX = new Cancion(keyx, nameSongx, artistx, genderx,
                coverx, albumx, filex, pricex);
        catalogueStore.insertSong(songX);
        System.out.println("Cancion registrada");
    }

    private static void showSongs() {
        System.out.printf("%-8s|%-30s|%-30s|%-8s\n",
                "Clave", "Nombre Cancion", "Artista", "Precio");
        System.out.println("--------|------------------------------|" +
                "------------------------------|--------");
        try {
            for (Cancion aux : catalogueStore.getListOfSongs()) {
                System.out.printf("%-8d|%-30s|%-30s|%-6.2f\n",
                        aux.getKeyS(), aux.getNameSong(), aux.getNameAuthor(), aux.getPrice());
            }
        } catch (IOError e) {
            System.out.println(eVacio());
        }

    }

    private static void renameSong() {
        int key, index;
        System.out.println("Cambiar Nombre");
        System.out.println("Dame número clave de la canción:");
        System.out.print("--->");
        key = in.nextInt();
        index = catalogueStore.searchSongForKey(key);
        if (index == -1) {
            System.out.println("La clave no existe, reintentalo");
        } else {
            in.nextLine();
            System.out.println("Datos de la canción");
            catalogueStore.seeSong(index);
            System.out.println();
            System.out.println("Dame el nuevo nombre de la canción: ");
            String nameSong = in.nextLine();
            catalogueStore.renameSong(index, nameSong);
            filex.writeInFile(catalogueStore.getListOfSongs());
            System.out.println("--Cambios guardados--");
            System.out.println();

        }
    }

    private static void deleteSong() {
        int key, index;
        System.out.println("Dame la clave de la canción a borrar: ");
        key = in.nextInt();
        index = catalogueStore.searchSongForKey(key);
        if (index == -1) {
            System.out.println("La clave no existe, reintentalo");
        } else {
            catalogueStore.deleteSong(key);
            System.out.println("La cancion y sus datos han sido eliminados");
        }
    }

    private static void searchSongByKey() {
        int key, index;
        System.out.println("Dame la clave de la canción a buscar: ");
        System.out.print("#");
        key = in.nextInt();
        index = catalogueStore.searchSongForKey(key);
        if (index == -1) {
            System.out.println("La clave no existe, reintentalo");
        } else {
            catalogueStore.seeSong(index);
        }

    }

    public static String eVacio() {
        return "Registro vacio";
    }

    public double getSenseCoins() throws FileNotFoundException { //esto seria el metodo obtenerSaldo
        File archivo = new File("saldo.txt");
        Scanner leerArchivo = new Scanner(archivo);
        while (leerArchivo.hasNext()) {
            Globales.saldo = leerArchivo.nextDouble();
        }
        return Globales.saldo;
    }

    public void setSenseCoins(int senseCoins) { // esto seria el metodo agregar o establecer saldo
        SenseCoins = senseCoins;
    }

    public void senseCoins() throws FileNotFoundException {   //esto seria el metodo obtenerSaldo
        SenseSoundLogo();
        System.out.println("Actualmente tienes " + getSenseCoins() + " SenseCoins");
        System.out.println("Siempre hay musica nueva que comprar...");


    }

    int total = 0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void comprarSenseCoins() throws FileNotFoundException { //metodo de añadir Saldo
        SenseSoundLogo();
        System.out.println("Tienda de SenseCoins");
        System.out.println("Hola " + getNombreUsuario() + "\n¿Cuantas SenseCoins deseas comprar?");
        cantidad = in.nextInt();

        try {
            PrintWriter salidaArchivo = new PrintWriter("saldo.txt");
            salidaArchivo.println(Globales.saldo+cantidad);
            salidaArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSenseCoins((int) (getSenseCoins() + cantidad));

    }

    public void cancionesCompradas() throws IOException {
        openBrowser op = new openBrowser();
        //crear lista de canciones compradas y generar web
        op.abrirNavegadorPorDefecto("SenseSound.html");
    }

    public void comprarCancion() throws FileNotFoundException {
        int numCancion, index;
        op.seeCatalogueSongs();
        System.out.println("Hola, por favor ingresa el numero de tu cancion");
        System.out.print("#");
        numCancion = in.nextInt();
        index = op.searchSong(numCancion);
        if (index == -1) {
            System.out.println("La clave de tu cancion no existe");
            return;
        }
        int indexSong = op.searchPurchasedSong(numCancion);
        if (indexSong != -1) {
            System.out.println("La cancion ya ah sido comprada");
            return;
        }
        Cancion song = catalogueStore.getListOfSongs().get(index);
        int price = (int) song.getPrice();
        if (song.getPrice() <= getSenseCoins()) {
            setSenseCoins((int) (getSenseCoins() - price));
            op.buySong(index);
            System.out.println("Cancion comprada con exito...");
        } else if (song.getPrice() > getSenseCoins()) {
            System.out.println("Saldo insuficiente, reintente");
        }


    }

    public void webCancionesCompradas() {
        String nombre = "miscancionesprueba.html";
        PaginaHTML pagina = new PaginaHTML(nombre);
        pagina.makeWeb();
    }

    public void crearWebTrial() {
        String nombre = "CancionesTrial.html";
        PaginaHTML p = new PaginaHTML(nombre);
        p.makeWebTrial();
    }

}
