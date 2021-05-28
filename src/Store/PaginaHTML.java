package Store;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PaginaHTML {
    private final String nombrepagina;
    private final Archivo filex = new Archivo("cancionesCompradas.txt");
    private final Archivo trials = new Archivo("canciones.txt");
    private final ArrayList<Cancion> listPurchasedSongs;
    private final ArrayList<Cancion> listSongs;

    public PaginaHTML(String nombreA) {
        this.nombrepagina = nombreA;
        listPurchasedSongs = filex.readFile();
        listSongs=trials.readFile();
    }

    public void makeWeb() {
        String firstPart = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>SenseSound</title>\n" +
                "    <link rel=\"stylesheet\" href=\"css/styles.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "  <header align=\"center\">\n" +
                "    <img src=\"img/circleW.png\" width=\"130\" height=\"130\">\n" +
                "  </header>\n" +
                "    <table>\n" +
                "      \n" +
                "      <thead>\n" +
                "        <th colspan=\"5\" align=\"center\">Mis canciones</th>\n" +
                "          <tr align=\"center\">\n" +
                "            <th>Caratula</th>\n" +
                "            <th>Audio</th>\n" +
                "            <th>Nombre de la cancion</th>\n" +
                "            <th>Artista</th>\n" +
                "            <th>Genero</th>    \n" +
                "          </tr>\n" +
                "        </thead>\n" +
                "        <tbody align=\"center\">";
        String registerSong = "";
        double total=0;
        for (Cancion aux : listPurchasedSongs) {
            registerSong += "<tr>\n" +
                    "            <td>\n" +
                    "              <img src=\""+aux.getCoverPage()+"\" height=\"80\" width=\"80\">\n" +
                    "            </td>\n" +
                    "            <td>\n" +
                    "            <audio controls style=\"width: 250px;\">\n" +
                    "              <source src=\""+aux.getFile()+"\" type=\"audio/mp3\">\n" +
                    "            </audio>\n" +
                    "            </td>\n" +
                    "            <td>"+aux.getNameSong()+"</td>\n" +
                    "            <td>"+aux.getNameAuthor()+"</td>\n" +
                    "            <td>"+aux.getGenre()+"</td>\n" +
                    "          </tr>";
            total+=aux.getPrice();
        }
        String finalPart = "<tr>\n" +
                "            <td>\n" +
                "              Total: $"+total+" \n" +
                "            </td>\n" +
                "          </tr> </tbody>\n" +
                "      </table>\n" +
                "</body>\n" +
                "</html>";
        try {
            PrintWriter salidaArchivo = new PrintWriter(nombrepagina);
            salidaArchivo.println(firstPart+registerSong+finalPart);
            salidaArchivo.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void makeWebTrial() {
        try {
            PrintWriter salidaArchivo = new PrintWriter(nombrepagina);
            salidaArchivo.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>SenseSound</title>\n" +
                    "    <link rel=\"stylesheet\" href=\"css/styles.css\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <header align=\"center\">\n" +
                    "    <img src=\"img/circleW.png\" width=\"130\" height=\"130\">\n" +
                    "  </header>\n" +
                    "  <div align=\"center\">\n" +
                    "    <iframe src=\"https://open.spotify.com/embed/playlist/3TEbk1xyVFXLBBvWRbNtrg\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  <iframe src=\"https://open.spotify.com/embed/playlist/2ySsTRklkg9NHi4Y9e1q6x\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  <iframe src=\"https://open.spotify.com/embed/playlist/3lZyxMQ5XeLON30lRTsvuV\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  </div>\n" +
                    "  <div align=\"center\">\n" +
                    "    <iframe src=\"https://open.spotify.com/embed/playlist/1ZHrJWMYrpBxnjPuiY0q69\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  <iframe src=\"https://open.spotify.com/embed/playlist/79QUFViZvYJHf6dihWTcqe\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  <iframe src=\"https://open.spotify.com/embed/playlist/5bTH2sCiIwZwsIDTdkpxnF\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  </div>\n" +
                    "  <div align=\"center\">\n" +
                    "    <iframe src=\"https://open.spotify.com/embed/playlist/7LwXFiR197LcOXpCFssppB\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>\n" +
                    "  </div>\n" +
                    "  \n" +
                    "\n" +
                    "</body>\n" +
                    "</html>");
            salidaArchivo.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
