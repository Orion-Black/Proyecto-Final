package Store;

import java.io.*;
import java.util.Scanner;

public class Metodos {
    static ManejoSaldo credito = new ManejoSaldo();
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
    static int aux = 0;


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
                    case 1:
                        login();
                        break;
                    case 2:
                        signIn();
                        InicioGeneral();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción invalida");
                        break;
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
                case 1:
                    requestDataSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                    break;
                case 2:
                    deleteSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                    break;
                case 3:
                    renameSong();
                    filex.writeInFile(catalogueStore.getListOfSongs());
                    showSongs();
                    break;
                case 4:
                    searchSongByKey();
                    break;
                case 5:
                    showSongs();
                    break;
                case 6:
                    webCatalogoCompleto();
                    navegador.abrirNavegadorPorDefecto("CancionCatalogo.html");
                    break;
                case 7:
                    InicioGeneral();
                    break;
                default:
                    System.out.println("Opción invalida");
                    break;
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
                case 1:
                    crearWebTrial();
                    navegador.abrirNavegadorPorDefecto("CancionesTrial.html");
                    panelUsuarios();
                    break;
                case 2:
                    comprarCancion();
                    break;
                case 3:
                    op.seeBuySongs();
                    webCancionesCompradas();
                    navegador.abrirNavegadorPorDefecto("miscancionesprueba.html");
                    break;
                case 4:
                    senseCoins();
                    break;
                case 5:
                    comprarSenseCoins();
                    break;
                case 6:
                    InicioGeneral();
                    break;
                default:
                    System.out.println("Opción invalida");
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
        crearWeb();

    }


    public void abrirWeb() throws IOException {
        openBrowser op = new openBrowser();
        op.abrirNavegadorPorDefecto("Sense1.html");
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

    public void webCatalogoCompleto() throws FileNotFoundException {
        PrintWriter catalogoWeb = new PrintWriter("CancionCatalogo.html");
        catalogoWeb.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<meta name=\"SenseStore\" content=\"Siempre hay musica para escuchar\" />\n" +
                "\t<meta content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" name=\"viewport\">\n" +
                "\n" +
                "\t<title>SenseSound-DEV-Catalogo</title>\n" +
                "\t<link rel=\"shortcut icon\" href=\"img/circleBlack.png\">\n" +
                "\t<link rel=\"stylesheet\" href=\"css/menu.css\">\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"1D1D1F\"><!--676E71/2EC77A/1D1D1F-->\n" +
                "\t<div id=\"sidemenu\" class=\"menu-collapsed\">\n" +
                "\t\t<div id=\"header\">\n" +
                "\t\t\t<div id=\"menu-btn\">\n" +
                "\t\t\t\t<div class=\"btn-hamburger\"></div>\n" +
                "\t\t\t\t<div class=\"btn-hamburger\"></div>\n" +
                "\t\t\t\t<div class=\"btn-hamburger\"></div>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t\t\n" +
                "\t\t<div id=\"menu-items\">\n" +
                "\t\t\t<div class=\"item\">\n" +
                "\t\t\t\t<a href=\"#\">\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#pop\">POP</a></span></div><br>\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#banda\">BANDA</a></span></div><br>\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#cumbias\">CUMBIAS</a></span></div><br>\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#dance\">DANCE</a></span></div><br>\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#regueton\">REGUETON</a></span></div><br>\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#rock\">ROCK</a></span></div><br>\n" +
                "\t\t\t\t\t<div class=\"title\"><span><a href=\"#urbano\">URBANO</a></span></div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "\t<div id=\"main-container\">\n" +
                "\t\t<div align=\"center\">\n" +
                "\t\t\t<img src=\"img/circleW.png\" width=\"400\" height=\"400\" alt=\"logoInicio\">\n" +
                "\t\t</div>\n" +
                "\t\t<section>\n" +
                "\t\t\t<table align=\"right\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t<img src=\"img/senseCoin.png\" width=\"70\" height=\"70\">\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t<h3 style=\"color:#FDFEFE\">SenseCoins<br><h3  align=\"center\" style=\"color:#FDFEFE\">" + getSenseCoins() + "</h3></h3>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t</section>\n" +
                "\n" +
                "\t\t<p><h2 align=\"left\" style=\"color:#FDFEFE\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Bienvenido a SenseStore</h2></p>\n" +
                "\t\t<p><h3 align=\"left\" style=\"color:#FDFEFE\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Todos los días se descubre una canción</h3></p>\n" +
                "\t\t</section>\n" +
                "\t\t<div align=\"center\">\n" +
                "\t\t\t<img src=\"img/linea.png\" width=\"1550\" height=\"30\">\n" +
                "\t\t</div>\n" +
                "\t\t<div align=\"center\">\n" +
                "\t\t\t\n" +
                "\t\t\t<h1 align=\"center\" style=\"color:#FDFEFE\"><a name=\"pop\">| POP |</a></h1>\n" +
                "\t\t\t\n" +
                "\t\t</div>\n" +
                "\n" +
                "\t\t<table class=\"default\" align=\"center\" style=\"color:#FDFEFE\">\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/pop/morat.png\" width=\"265\" height=\"265\"></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/pop/feelings.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/pop/cd9.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/pop/teAmo.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/pop/BPink.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Como te atreves a volver<br>Morat<br><i>#00020</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">In my feelings<br>DRAKE<br><i>#00021</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">No le hablen de amor<br>CD9<br><i>#00022</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Te amo<br>Piso 21 & Paulo Londra<br><i>#00023</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">How you like that<br>BlackPink<br><i>#00024</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/pop/ComoTeAtrevesAVolver.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/pop/InMyFeelings.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/pop/NoLeHablenDeAmor.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/pop/TeAmo.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/pop/HowYouLikeThat.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h1 align=\"center\"><a name=\"banda\">| Banda |</a></h1>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/banda/detengas.png\" width=\"265\" height=\"265\"></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/banda/elcolor.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/banda/aTraves.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/banda/detengas.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/banda/aerolinea1.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Por mi no te detengas<br>Banda MS<br><i>#00025</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">El color de tus ojos<br>Banda MS<br><i>#00026</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">A traves del vaso<br>Banda Los Sebastianes<br><i>#00027</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Las cosas no se hacen así<br>Banda MS<br><i>#00028</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Aerolinea carrillo<br>Tercer Elemento<br><i>#0029</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/banda/PorMiNoTeDetengas.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/banda/ElColorDeTusOjos.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/banda/ATravesDelVaso.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/banda/LasCosasNoSeHacenAsi.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/banda/AerolineaCarrillo.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h1 align=\"center\"><a name=\"cumbias\">| Cumbias |</a></h1>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/cumbias/acariñame.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/cumbias/oye.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/cumbias/17años.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/cumbias/oye.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/cumbias/misSen.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Acariñame\n" +
                "\t\t\t\t\t\t<br>Julieta Venegas & Los Angeles Azules<br><i>#00030</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Oye mujer<br>Raymix<br><i>#00031</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">17 años<br>Los Angeles Azules<br><i>#00032</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Primer Beso<br>Raymix<br><i>#00033</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Mis sentimientos<br>Los Angeles Azules <br>& Ximena Sariñana<br><i>#00034</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/cumbias/Acariñame.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/cumbias/OyeMujer.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/cumbias/17años.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/cumbias/PrimerBeso.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/cumbias/MisSentimientos.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h1 align=\"center\"><a name=\"dance\">| Dance |</a></h1>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/dance/inMy.png\" width=\"265\" height=\"265\"></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/dance/turnItUp.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/dance/bailaConmigo.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/dance/alPacino.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/dance/enzo.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">In my mind<br>Dynoro & D´ Agostino<br><i>#00035</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Turn it up<br>Armin Van Buuren<br><i>#00036</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Baila conmigo<br>Victor Cardenas feat. Kelly Ruiz<br><i>#00037</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Al pacino<br>Timmy Trumpet<br><i>#00038</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Enzo<br>DJ Snake<br><i>#00039</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/dance/InMyMind.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/dance/TurnItUp.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/dance/BailaConmigo.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/dance/AlPacino.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/dance/Enzo.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h1 align=\"center\"><a name=\"regueton\"></a>| Regueton |</a></h1>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/regueton/nadie.png\" width=\"265\" height=\"265\"></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/regueton/otroTrago.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/regueton/rebota.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/regueton/HP.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/regueton/daddy.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">NADIE<br>Sech Remix<br><i>#00040</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Otro trago Remix<br>Sech & Darell & Nicky Jam & Ozuna<br><i>#00041</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Rebota<br>Guaynaa<br><i>#00042</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">HP<br>Maluma<br><i>#00043</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Con calma<br>Daddy Yankee<br><i>#00044</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/regueton/Nadie.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/regueton/OtroTrago.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/regueton/Rebota.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/regueton/HP.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/regueton/ConCalma.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h1 align=\"center\"><a name=\"rock\"></a>| Rock |</a></h1>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/rock/hotel.png\" width=\"265\" height=\"265\"></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/rock/loveYou.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/rock/rem.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/rock/sweet.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/rock/happy.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Hotel California<br>The Eagles<br><i>#00045</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">I just called to say I love you<br>Stevie Wonder<br><i>#00046</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Losing my religion<br>R.E.M<br><i>#00047</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Sweet dreams<br>Eurythmics<br><i>#00048</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Happy together<br>The turtles<br><i>#00049</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/rock/HotelCalifornia.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/rock/OnlyCallToSayILoveYou.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/rock/LosingMyReligion.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/rock/SweetDreams.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/rock/HappyTogheter.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h1 align=\"center\"><a name=\"urbano\">| Urbano |</a></h1>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t\t<td></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/urbana/tini.png\" width=\"265\" height=\"265\"></td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/urbana/talVez.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"sound/complete/urbana/novio.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/urbana/alone.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<img src=\"sound/complete/urbana/talVez.png\" width=\"265\" height=\"265\">\n" +
                "\t\t\t\t\t<img src=\"img/lineaCircularVertical.png\" width=\"30\" height=\"265\">\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Oye<br>Taini feat. Sebastian Yatra<br><i>#00050</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Tal vez<br>Paulo Londa<br><i>#00051</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Ya no tiene novio<br>Sebastian Yatra & Mau y Ricky<br><i>#00052</i><br><img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Forever alone<br>Paulo Londra<br><i>#00053</i><br>\n" +
                "\t\t\t\t\t<img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<h4 align=\"center\">Solo pienso en ti<br>Paulo Londra ft. De la Guetto<br><i>#00054</i><br>\n" +
                "\t\t\t\t\t<img src=\"img/senseCoin.png\" width=\"30\" height=\"30\" align=\"center\">15 coins</h4>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/urbana/Oye.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/urbana/TalVez.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/urbana/YaNoTieneNovio.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/urbana/ForeverAlone.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<audio width=\"100\" controls>\n" +
                "\t\t\t\t\t\t<source src=\"sound/complete/urbana/SoloPiensoEnTi.mp3\" type=\"audio/mp3\">\n" +
                "\t\t\t\t\t</audio>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\n" +
                "\t\t<div align=\"center\">\n" +
                "\t\t\t<img src=\"img/linea.png\" width=\"1550\" height=\"30\">\n" +
                "\t\t</div>\n" +
                "\t\t\n" +
                "\t<footer>\n" +
                "\t\t<table align=\"center\">\n" +
                "\t\t\t<tbody style=\"background: rgba(22, 153, 219, .6); border: 1px solid rgba(100, 200, 0, 0.3);\" align=\"center\">\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<div align=\"center\">\n" +
                "\t\t\t\t\t\t<h4 align=\"center\" style=\"color:#FDFEFE\">\n" +
                "\t\t\t\t\t\t&nbsp;&nbsp;\"  Estas son algunas muestras por favor dirigete al programa de compra &nbsp;&nbsp;<br>ingresa su codigo y disfruta las canciones completas \"\n" +
                "\t\t\t\t\t</h4>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t\t<h5 align=\"right\" style=\"color:#FDFEFE\">Derechos reservados & Copyright  SenseSound 2020 - 2021</h5>\n" +
                "\t</footer>\n" +
                "    </div>\n" +
                "\t<script>\n" +
                "\t\tconst btn = document.querySelector('#menu-btn');\n" +
                "\t\tconst menu = document.querySelector('#sidemenu');\n" +
                "\t\tbtn.addEventListener('click', e =>{\n" +
                "\t\t\tmenu.classList.toggle(\"menu-expanded\");\n" +
                "\t\t\tmenu.classList.toggle(\"menu-collapsed\");\n" +
                "\t\t\tdocument.querySelector('body').classList.toggle('body-expanded');\n" +
                "\t\t});\n" +
                "\t</script>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
    }

    public void webCancionesCompradas() {
        String nombre = "miscancionesprueba.html";
        PaginaHTML pagina = new PaginaHTML(nombre);
        pagina.makeWeb();
    }

    public void crearWeb() throws FileNotFoundException {
        String nombre = "Sense1.html";
        PaginaHTML pag = new PaginaHTML(nombre);
    }

    public void crearWebTrial() {
        String nombre = "CancionesTrial.html";
        PaginaHTML p = new PaginaHTML(nombre);
        p.makeWebTrial();
    }

}
